define(
		[ 'bruyere-commons', 'bruyere-settings',
				'bruyere-service-support', 'domReady' ], function(commons, settings,
				support) {
			var self = {
				game : null,
				initiatedGameLoading : false,
				animationSpeed : 1000,
				animationMode : 0,
				paused : true,
				forward : true,
				index : -1,
				timer : null,

				init : function() {
					
					commons.debug('initializing tester');
					$("#btnPlayForward").click(function() {
						self.playForward();
					});
					$("#btnPlayBackward").click(function() {
						self.playBackward();
					});
					$("#btnPause").click(function() {
						self.pause();
					});
					$("#btnStepBackward").click(function() {
						self.stepBackward();
					});
					$("#btnStepForward").click(function() {
						self.stepForward();
					});

					support.loadOwnBots();
					support.loadPublicBots();
					$("#error").hide();
					
					$( "#slider" ).slider({
	                    value: self.animationSpeed,
	                    min: 0,
	                    max: 2000,
	                    step: 10,
	                    slide: function( event, ui ) {
	                        self.animationSpeed = 2000 - ui.value;
	                        if(self.timer != null) {
	        					clearInterval(self.timer);
	        					self.timer = null;
	        					self.startGame();
	                        }
	                        console.log("animation speed set to " + self.animationSpeed);
	                    }
	                });
					
					support.initSearchBox();

				},
				initBoard : function() {
					var y = 1;
					self.index = -1;
					
					$("#game").html("");
					
					for (var i=0;i<self.game.initialBoard.length;i++) {
						var cell = self.game.initialBoard[i];
						if(cell.y != y) {
							y = cell.y;
							$("#game").append("<div style=\"clear:both;\"/>");
						}
						$("#game").append("<canvas id=\"cell" + i + "\" class=\"square y" + cell.y + " x" + cell.x + "\" width=\"26\" height=\"22\"></canvas>");
						
						if(cell.value != 0) {
							self.drawCell(cell);
						}
						
					}

				},
				playForward : function() {
					if (!self.paused && self.forward) {
						return;
					} else {
						self.paused = false;
					}
					self.forward = true;
					self.startGame();
				},
				startGame : function() {
					if (!self.game || self.game === null) {

						support._execGame(self.setGame);
					} else {

						if(!self.paused) {
							if(self.timer == null) {
								self.timer = setInterval(function() {
									self.updateBoard();
								}, self.animationSpeed);
							}
						} else {
							self.updateBoard();
						}
					}
				},
				playBackward : function() {
					if (!self.paused && !self.forward) {
						return;
					} else {
						self.paused = false;
					}
					self.forward = false;
					self.startGame();
				},
				pause : function() {
					if (self.paused) {
						return;
					}

					self.paused = true;
					clearInterval(self.timer);
					self.timer = null;
				},
				stepBackward : function() {
					if (!self.paused) {
						self.pause();
					}
					self.forward = false;
					self.startGame();
				},
				stepForward : function() {
					if (!self.paused) {
						self.pause();
					}
					self.forward = true;
					self.startGame();
				},
				updateBoard : function() {
					if(support.selectionChanged) {
						if(!self.paused) {
							self.pause();
						}
						self.game = null;
						return;
					}
					if(self.forward) {
						self.index++;
					} else {
						self.index--;
					}
					
					if(self.index>=self.game.moves.length) {
						self.index = self.game.moves.length-1;
						self.pause();
						return;
					} else if(self.index < -1) {
						self.index = -1;
						self.pause();
						return;
					}
					
					if(self.index == -1) {
						for (var i=0;i<self.game.initialBoard.length;i++) {
							var cell = self.game.initialBoard[i];
							self.drawCell(cell);
						}

						return;
					}
					
					var move = self.game.moves[self.index];
					
					var winner =self.game.winner;
					if(winner ==1){
						$(".scoreboard").html("bot 1 win").addClass("playerscore player1score");
					}else{
						$(".scoreboard").html("bot 2 win").addClass("playerscore player2score");;
					}
					
					
					for (var i=0;i<move.board.length;i++) {
						var cell = move.board[i];
						self.drawCell(cell, move);
					}
				},
				drawCell : function(cell, move) {
					var c = $('.y'+cell.y+'.x'+cell.x);
					
					var canvas = c.get()[0];
					
					var ctx = canvas.getContext("2d");
					
					ctx.clearRect ( 0 , 0 , canvas.width, canvas.height);
					
					if(cell.value == 1) {
						ctx.fillStyle = "#00F";
					} else if (cell.value == 0){
						return;
					} else {
						ctx.fillStyle = "#F00";
					}
					
					
					if(move && cell.winning_piece) {
						if(cell.winning_piece == true){
							
							ctx.strokeStyle ="#FFF";
							ctx.lineWidth = 4;
						}
					}
					
					ctx.beginPath();
					ctx.arc(13,11,10,0,2*Math.PI);
					ctx.stroke();
					ctx.fill();
				},
				setGame : function(game) {
					if (game.error) {
						$("#error").show();
						var errorInfo = commons.formatServerError(game.error,10);
						$("#error").html(errorInfo);
					} 
					self.game = game;
					self.initBoard();
					
					self.startGame();
				}
			};

			// init myself
			self.init();
			return self;
		});
$(".scoreboard").html("who will win?");

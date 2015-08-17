define(
	['bruyere-commons', 'bruyere-settings','bruyere-bot', 'bruyere-service-support',  'domReady' ],
	function(commons, settings,Bot, support) {
		var self = {
			game : null,
			animationSpeed : 500,
			// 0 - manual step, -1 backward animated play, 1 forward animated play
			animationMode : 0, 
			displayedMoves : [],
			hiddenMoves : [],
			
			init : function() {
				commons.debug('initializing tester');
				$("#btnPlayForward").click(function() {self.playForward();});
				$("#btnPlayBackward").click(function() {self.playBackward();});
				$("#btnPause").click(function() {self.pause();});
				$("#btnStepBackward").click(function() {self.stepBackward();});
				$("#btnStepForward").click(function() {self.stepForward();});
				
				$( "#slider" ).slider({
                    value:500,
                    min: 0,
                    max: 1000,
                    step: 10,
                    slide: function( event, ui ) {
                        self.animationSpeed = 1000 - ui.value;
                        console.log("animation speed set to " + self.animationSpeed);
                    }
                });

				support.loadOwnBots();
				support.loadPublicBots();
				
				$("#error").hide();
				
				// draw cells
				self.drawBoard();
				
				support.initSearchBox();
				support.setSelectionCallback(function() {
					self._reset();
					self._checkState();
					self.drawBoard();
				});
				
				self._checkState();
			},
			drawBoard : function() {
				var size = 100;
				for (var i=1;i<=size;i++) {
					$("#game").append("<div id=\"cell" + i + "\" class=\"square\">" + i + "</div>");
				}
			},
			// animate forward
			playForward : function() {
				self.animationMode = 1;
				if (!self.game) {
					self._execGame();
				}
				else {
					self._next();
				}
			},
			// animate back
			playBackward : function() {
				self.animationMode = -1;
				if (self.paused) {
					self.paused = false;
				}
				self._previous();
			},
			pause : function() {
				self.animationMode = 0;
				self._updateScore();
			},
			// step back
			stepBackward : function() {
				self.animationMode = 0;
				self._previous();
			},
			// step forward
			stepForward : function() {
				self.animationMode = 0;
				if (!self.game) {
					self._execGame();
				}
				else {
					self._next();
				}
			},
			_next : function() {
				if (!self.game) return;			
				if (self.hiddenMoves.length==0) return;				
				var move = _.first(self.hiddenMoves);
				self.hiddenMoves = _.without(self.hiddenMoves,move);
				self.displayedMoves.push(move);
				
				var i = move["player1.played"];
				$("#cell"+i).addClass("player1Played");
				i = move["player2.played"];
				$("#cell"+i).addClass("player2Played");
				var ii = move["player1.forced"];
				for (var j=0;j<ii.length;j++) {
					$("#cell"+ii[j]).addClass("player1Forced");
				}
				ii = move["player2.forced"];
				for (var j=0;j<ii.length;j++) {
					$("#cell"+ii[j]).addClass("player2Forced");
				}
				
				self._updateScore();
				
				if (self.hiddenMoves.length==0) {
					self.animationMode=0;
				}							
				if (self.animationMode==1) {
					setTimeout( function() {self._next()}, self.animationSpeed );
				}
			},
			_previous : function() {
				if (!self.game) return;			
				if (self.displayedMoves.length==0) return;			
				var move = _.last(self.displayedMoves);
				self.displayedMoves = _.without(self.displayedMoves,move);
				self.hiddenMoves.splice(0,0,move);
					
				var i = move["player1.played"];
				$("#cell"+i).removeClass("player1Played");
				i = move["player2.played"];
				$("#cell"+i).removeClass("player2Played");
				var ii = move["player1.forced"];
				for (var j=0;j<ii.length;j++) {
					$("#cell"+ii[j]).removeClass("player1Forced");
				}
				ii = move["player2.forced"];
				for (var j=0;j<ii.length;j++) {
					$("#cell"+ii[j]).removeClass("player2Forced");
				}
				
				self.replayCursor = self.replayCursor-1;
				self._updateScore();
				
				if (self.displayedMoves.length==0) {
					self.animationMode=0;
				}
				
				if (self.animationMode==-1) {
					setTimeout( function() {self._previous()}, self.animationSpeed );
				}
			},
			_updateScore : function() {
				if (self.game && self.game.error) {
					// if an error occurs, set winner without score
					if (self.game.error.winner==1) {
						$("#player1score").text("WON");
						$("#player2score").text("LOST");	
					}
					else {
						$("#player1score").text("LOST");
						$("#player2score").text("WON");	
					}
				}
				else {
					var score1 = 0;
					var score2 = 0;
					if (self.game) {
						for (var i=0;i<self.displayedMoves.length;i++) {
							var move = self.displayedMoves[i];
							score1 = score1 + move["player1.played"];
							score2 = score2 + move["player2.played"];
							var ii = move["player1.forced"];
							for (var j=0;j<ii.length;j++) {
								score1 = score1 + ii[j];
							}
							ii = move["player2.forced"];
							for (var j=0;j<ii.length;j++) {
								score2 = score2 + ii[j];
							}
						}
					}
					$("#player1score").html(score1);
					$("#player2score").html(score2);
				}
				self._checkState();
			},
			_reset : function() {
				self.game = null;
				self.hiddenMoves = null;
				self.displayedMoves = null;
				self.replayCursor = 0;
				
				$(".square").removeClass("player1Played");
				$(".square").removeClass("player2Played");
				$(".square").removeClass("player1Forced");
				$(".square").removeClass("player2Forced");
				
				$("#error").html("");
				$("#error").hide();
				
				self._updateScore();
			},
			_execGame : function() {
				if ($('.selected-player2').length === 0) {
					bootbox.alert("Select two bots to start the game");
					return;
				}
				
				var url = settings.root + "creategame_b2b";
				var bot1Id = $('.selected-player1').data("id");
				var bot2Id = $('.selected-player2').data("id");
				var data = "" + bot1Id + "\n" + bot2Id + "\n";
				var jqxhr = $.ajax({
					url : url,
					type : "POST",
					data : data
				}).done(function(src) {				
					// get results
					var url2 = jqxhr.getResponseHeader("Location");
					commons.debug("done creating game, results will be available at " + url2);
					$("#gameloadingdialog").dialog({title: "Executing and loading game"});
					$("#gameloadingprogressbar").progressbar({value: false });
					setTimeout(function(){self._fetchGame(url2)},2000);
				}).fail(function(jqXHR, textStatus, error) {
					var errorLoc = jqXHR.getResponseHeader("Location-Error");
					commons.handleError(textStatus,"cannot post game");
					commons.notifyUser("Server Error",jqXHR.responseText);
				});
				
			},
			_fetchGame : function(url) {
				var game = $.getJSON(url)
					.done(function(data) {

						$("#gameloadingdialog").dialog("close");
						self.game = data;
						self.hiddenMoves = self.game.moves;
						self.displayedMoves = [];
						self._next();

						if (data.error) {
							$("#error").show();
							var errorInfo = commons.formatServerError(data.error,10);
							$("#error").html(errorInfo);
						}
					})
					.fail(function(jqXHR, textStatus, error) {
						commons.handleError(textStatus,"game execution error");
						commons.notifyUser("Execution Error","A server error occured, please notify the system administrator, and try again later");
					});
			},
			_checkState : function() {
				// disable / enable buttons
				var needsGame = !self.game  && $('.selected-player2').length>0;
				var canGoForward = !!self.game && self.hiddenMoves.length>0;
				var canGoBackward = !!self.game && self.displayedMoves.length>0;
				$("#btnPlayForward").prop('disabled',! (needsGame || canGoForward));
				$("#btnPlayForward").prop('disabled',! (needsGame || canGoForward));
				$("#btnPlayBackward").prop('disabled',! (canGoBackward));
				$("#btnPause").prop('disabled',self.animationMode === 0);
				$("#btnStepBackward").prop('disabled',! (canGoBackward));
				$("#btnStepForward").prop('disabled',! (needsGame || canGoForward) );
			},
		};
		self.init();
		return self;
	});
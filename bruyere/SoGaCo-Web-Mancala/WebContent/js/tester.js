define(
	['./bruyere-commons', './bruyere-settings','./bruyere-bot', 
		'bruyere-service-support', 'domReady' ],
	function(commons, settings,Bot, support) {
		var self = {
			game : null,
			animationSpeed : 500,
			// 0 - manual step, -1 backward animated play, 1 forward animated play
			animationMode : 0, 
			displayedMoves : [],
			hiddenMoves : [],
			activePlayer : 1,
			
			init : function() {
				commons.debug('initializing tester');
				$("#btnPlayForward").click(function() {self.playForward();});
				$("#btnPlayBackward").click(function() {self.playBackward();});
				$("#btnPause").click(function() {self.pause();});
				$("#btnStepBackward").click(function() {self.stepBackward();});
				$("#btnStepForward").click(function() {self.stepForward();});
				$("#error").hide();
				
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
				
				// draw cells
				
				// some empty space to better centre board
				$("#game").append("<div style=\"height:100px;\"></div>");
				
				// the next pit is mancala 1
				$("#game").append("<div class=\"mancala-left-mancala\" style=\"clear: left\"><div id=\"pit1-6\" class=\"mancala-left-mancala-inner player1Pit\">0</div></div>");
				
				$("#game").append("<div id=\"game_middle\" style=\"float:left\">");
				
				$("#game_middle").append("<div class=\"mancala-top-pit\"><div id=\"pit2-0\" class=\"mancala-top-pit-inner player2Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-top-pit\"><div id=\"pit2-1\" class=\"mancala-top-pit-inner player2Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-top-pit\"><div id=\"pit2-2\" class=\"mancala-top-pit-inner player2Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-top-pit\"><div id=\"pit2-3\" class=\"mancala-top-pit-inner player2Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-top-pit\"><div id=\"pit2-4\" class=\"mancala-top-pit-inner player2Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-top-pit\"><div id=\"pit2-5\" class=\"mancala-top-pit-inner player2Pit\">4</div></div>");
				
				$("#game_middle").append("<div class=\"mancala-bottom-pit\" style=\"clear: left\"><div id=\"pit1-5\" class=\"mancala-bottom-pit-inner player1Field\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-bottom-pit\"><div id=\"pit1-4\" class=\"mancala-bottom-pit-inner player1Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-bottom-pit\"><div id=\"pit1-3\" class=\"mancala-bottom-pit-inner player1Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-bottom-pit\"><div id=\"pit1-2\" class=\"mancala-bottom-pit-inner player1Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-bottom-pit\"><div id=\"pit1-1\" class=\"mancala-bottom-pit-inner player1Pit\">4</div></div>");
				$("#game_middle").append("<div class=\"mancala-bottom-pit\"><div id=\"pit1-0\" class=\"mancala-bottom-pit-inner player1Pit\">4</div></div>");

				// the next pit is mancala 2
				$("#game").append("<div class=\"mancala-right-mancala\"><div id=\"pit2-6\" class=\"mancala-right-mancala-inner player2Pit\">0</div></div>");
				
				// could add extra box to display "hand"
		
				$("#game").append("<table style=\"clear: left;margin-top:150px;margin-left:100px;\"><tr><td>Stones played:</td><td id=\"stonesplayed\"></td></tr></table>");
				
				self._checkState();
				
				support.initSearchBox();
				support.setSelectionCallback(function() {
					self._reset();
					self._checkState();
				});
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
				self._displayMove(move);
				self.replayCursor = self.replayCursor+1;
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
				self._displayMove(move);
				self.replayCursor = self.replayCursor-1;
				self._updateScore();
				
				if (self.displayedMoves.length==0) {
					self.animationMode=0;
				}
				
				if (self.animationMode==-1) {
					setTimeout( function() {self._previous()}, self.animationSpeed );
				}
			},
			_displayMove : function(move) {
				
				// clean highlights
				$("div").removeClass("player1PitPlayed");
				$("div").removeClass("player1PitChanged");
				$("div").removeClass("player2PitPlayed");
				$("div").removeClass("player2PitChanged");
				
				$("#stonesplayed").text(move.stonesPlayed);
				
				// display new board
				// display stones
				for (i = 0; i < move.player1state.length; i++) {
					var stones = move.player1state[i];
					var pit = "pit1"+"-"+i;
					$("#"+pit).text(stones);
				}
				for (i = 0; i < move.player2state.length; i++) {
					var stones = move.player2state[i];
					var pit = "pit2"+"-"+i;
					$("#"+pit).text(stones);
				}
				// highlight changes
				for (i = 0; i < move.player1changed.length; i++) {
					var pit = "pit1"+"-"+move.player1changed[i];
					$("#"+pit).addClass("player1PitChanged");
				}
				for (i = 0; i < move.player2changed.length; i++) {
					var pit = "pit2"+"-"+move.player2changed[i];
					$("#"+pit).addClass("player2PitChanged");
				}
				if (move.playerStarted==1) {
					var pit = "pit1"+"-"+move.startPit;
					$("#"+pit).removeClass("player1PitChanged");
					$("#"+pit).addClass("player1PitPlayed");
				}
				else if (move.playerStarted==2) {
					var pit = "pit2"+"-"+move.startPit;
					$("#"+pit).removeClass("player2PitChanged");
					$("#"+pit).addClass("player2PitPlayed");
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
					// copy score from mancalas
					$("#player1score").text($("#pit1-6").text());
					$("#player2score").text($("#pit2-6").text());
				}
				self._checkState();
			},
			_reset : function() {
				self.game = null;
				self.hiddenMoves = null;
				self.displayedMoves = null;
				self.replayCursor = 0;
				
				// clean highlights
				$("div").removeClass("player1PitChanged");
				$("div").removeClass("player2PitChanged");
				$("div").removeClass("player1PitPlayed");
				$("div").removeClass("player2PitPlayed");
				
				$("#error").html("");
				$("#error").hide();
				
				// reset pit values
				for (i = 0; i < 6; i++) {
					var pit = "pit1"+"-"+i;
					$("#"+pit).text(4);
				}
				for (i = 0; i < 6; i++) {
					var pit = "pit2"+"-"+i;
					$("#"+pit).text(4);
				}
				// reset mancalas
				$("#pit1-6").text(0);
				$("#pit2-6").text(0);
				
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
						// skip over first "empty" move
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
				var needsGame = !self.game && $('.selected-player2').length>0;
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
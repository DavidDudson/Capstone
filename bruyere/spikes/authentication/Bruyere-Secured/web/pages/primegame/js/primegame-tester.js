define(
	['./bruyere-commons', './bruyere-settings','./bruyere-bot' ],
	function(commons, settings,Bot) {
		var self = {
			bots : [],
			game : null,
			selection : [],
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
	              
				self._loadOwnBots();
				self._loadPublicBots();
				
				// draw cells
				var size = 100;
				for (var i=1;i<=size;i++) {
					$("#game").append("<div id=\"cell" + i + "\" class=\"square\">" + i + "</div>");
				}
				
				self._checkState();
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
				self._checkState();
			},
			_loadOwnBots : function() {
				self._loadBots(settings.user,"#ownBotsList");
			},
			_loadPublicBots : function() {
				self._loadBots(settings.public_user,"#publicBotsList");
			},
			_loadBots : function(user,domElementId) {
				var url = settings.root + "userbots/" + user;
				$(domElementId).empty();
				$(domElementId).html("<ul></ul>");
				$.getJSON(url).done(
					function(data) {
						commons.debug(data);
						var botMetadataArr = data.collection.items;
						
						// sort by name
						botMetadataArr.sort(function(a,b){
							var s1 = a.name || '';
							var s2 = b.name || '';
							return s1.localeCompare(s2);
						});

						// display names in menu bar
						for ( var i = 0; i < botMetadataArr.length; i++) {
							var botMetadata = botMetadataArr[i];
							self._addBot(domElementId,botMetadata,botMetadata.name);
						}
					}).fail(function(jqxhr, textStatus, error) {
						commons.handleError(textStatus, error);
					});
			},
			_addBot : function(domElementId,metadata, name) {
				var bot = new Bot(metadata,null);
				var i = self.bots.length;
				self.bots.push(bot);
				var ul = $(domElementId+" ul");
				ul.append("<li id=\"bot"+i+"\" data-index=\"" + i + "\" >" + name + "</li>");

				$(domElementId+" li").off("click");
				$(domElementId+" li").click(function() {
					self._select($(this).attr("data-index"));
				});
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
				
				self._updateScore();
			},
			_select : function(selection) {
				self._reset();
				
				commons.debug('selecting bot ' + selection);
				var bot = self.bots[selection];
				self.selection = _.without(self.selection,selection);
				while (self.selection.length>1) {
					self.selection = _.rest(self.selection);
				}
				self.selection.push(selection);
				commons.debug('selection: ' + self.selection);
				
			    $("#ownBotsList li").removeClass("selected-player1");
			    $("#publicBotsList li").removeClass("selected-player1");
			    $("#ownBotsList li").removeClass("selected-player2");
			    $("#publicBotsList li").removeClass("selected-player2");
			    
			    if (self.selection.length>0) $("#bot"+self.selection[0]).addClass("selected-player1");
			    if (self.selection.length>1) $("#bot"+self.selection[1]).addClass("selected-player2");
			    
			    self._checkState();
			},
			_execGame : function() {
				if (self.selection.length<2) {
					bootbox.alert("Select two bots to start the game");
				} 
				if (self.selection.length==2) {
					var url = settings.root + "creategame_b2b";
					var bot1Id = self.bots[self.selection[0]].getId();
					var bot2Id = self.bots[self.selection[1]].getId();
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
						commons.error("cannot post game");
					});
				}
			},
			_fetchGame : function(url) {
				var game = $.getJSON(url)
					.done(function(data) {
						$("#gameloadingdialog").dialog("close");
						self.game = data;
						self.hiddenMoves = self.game.moves;
						self.displayedMoves = [];
						self._next();
					})
					.fail(function(jqXHR, textStatus, error) {
						commons.debug("game execution error");
					});
			},
			_checkState : function() {
				// disable / enable buttons
				var needsGame = !self.game && self.selection.length==2;
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
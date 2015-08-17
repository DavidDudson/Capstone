define([ './bruyere-commons', './bruyere-settings'],
		function(commons, settings) {
			var self = {
					selectionIndex : 1,
					selectionChanged : false,
					selectionCallback : null,
					
					initSearchBox : function() {
						var box = $("#searchBox");
						box.click(function() {
							if($(this).data("default") == true) {
								$(this).data("default", $(this).val());
								$(this).val("");
							}
						});
						box.focusout(function() {
							if($(this).val() == "") {
								$(this).val($(this).data("default"));
								$(this).data("default", true);
							}
						});
						box.keypress(function(e) {
						    if(e.which == 13) {
						    	self.search();
						    }
						});
						$("#btnSearch").click(function() {
							self.search();
						});
					},
					search : function() {
						var query = $("#searchBox").val();
						$.get("searchBot", {q : query}).done(function(data) {
							$("#sharedBotsList ul").empty();
							
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
								self._addBot("#sharedBotsList",botMetadata,botMetadata.name, true);
							}
						});
					},
					loadOwnBots : function() {
						self._loadBots("__current_user","#ownBotsList");
					},
					loadPublicBots : function() {
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
								if(user !== settings.public_user) {
									botMetadataArr.sort(function(a,b){
										var s1 = a.name || '';
										var s2 = b.name || '';
										return s1.localeCompare(s2);
									});
								}

								// display names in menu bar
								for ( var i = 0; i < botMetadataArr.length; i++) {
									var botMetadata = botMetadataArr[i];
									self._addBot(domElementId,botMetadata,botMetadata.name, false);
								}
							}).fail(function(jqxhr, textStatus, error) {
								commons.handleError(textStatus, error);
							});
					},
					_addBot : function(domElementId,metadata, name, displayUserName) {
						var ul = $(domElementId+" ul");
						if(displayUserName) {
							ul.append("<li style=\"text-overflow:ellipsis;overflow: hidden;white-space:nowrap;\" data-id=\"" + metadata.id + "\" >" + name + " (" + metadata.owner_name + ")</li>");
						} else {
							ul.append("<li data-id=\"" + metadata.id + "\" >" + name + "</li>");
						}
							
						$(domElementId+" li").off("click");
						$(domElementId+" li").click(function() {
							self._select($(this));
						});
					},
					_select : function(selection) {						
						commons.debug('selecting bot ' + selection);
						
						self.selectionChanged = true;
						$("#game").html("");
						
						var currentPlayerClass = "selected-player" + self.selectionIndex;
						self.selectionIndex = self.selectionIndex == 1 ? 2 : 1;
						var otherPlayerClass = "selected-player" + self.selectionIndex;
						
						var currentPlayer = $('.' + currentPlayerClass);
						var otherPlayer = $('.' + otherPlayerClass);
						
						if(currentPlayer)
							currentPlayer.removeClass(currentPlayerClass);
						if(otherPlayer) 
							otherPlayer.removeClass(otherPlayerClass);
						
						if(currentPlayer && selection[0] == currentPlayer[0]) {
							selection.addClass(otherPlayerClass);
							otherPlayer.addClass(currentPlayerClass);
						} else if(otherPlayer && selection[0] == otherPlayer[0]) {							
							selection.addClass(currentPlayerClass);
							currentPlayer.addClass(otherPlayerClass);							
						} else {
							selection.addClass(currentPlayerClass);
							if(otherPlayer)
								otherPlayer.addClass(otherPlayerClass);
						}
						
						
						if(self.selectionCallback != null) {
							self.selectionCallback();
						}
					},
					_execGame : function(callback) {
						if ($('.selected-player2').length === 0 || $('.selected-player1').length === 0) {
							bootbox.alert("Select two bots to start the game");
							return;
						}
						
						self.selectionChanged = false;
						
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
							setTimeout(function(){self._fetchGame(url2, callback)},2000);
						}).fail(function(jqXHR, textStatus, error) {
							var errorLoc = jqXHR.getResponseHeader("Location-Error");
							//commons.error("cannot post game");
							commons.notifyUser("Server Error",jqXHR.responseText);
						});
						
					},
					_fetchGame : function(url, callback) {
						$.getJSON(url)
							.done(function(data) {
								$("#gameloadingdialog").dialog("close");
								callback(data);
							})
							.fail(function(jqXHR, textStatus, error) {
								commons.debug("game execution error");
							});
					},
					setSelectionCallback : function(callback) {
						self.selectionCallback = callback;
					},
					preselectSharedPlayer : function(player) {
						$.get("bots-metadata/" + player).done(function(botMetadata) {
							self._addBot("#sharedBotsList",botMetadata,botMetadata.name);
							$("#sharedBotsList li").addClass("selected-player2");
						});
					}
			};
			
			
			// init myself
			return self;
		});
define(
		['./bruyere-commons', './bruyere-settings','./bruyere-bot' ],
		function(commons, settings, Bot) {
			var self = {
				ace : null,
				bots : [],
				selection :-1,
				languages : {},

				init : function() {
					this.ace = ace.edit("editor");
					this.ace.setTheme("ace/theme/eclipse");
					this.ace.getSession().setMode("ace/mode/java");
					this.ace.getSession().on("change", this._textChanged);
					this._loadPrograms();
					
					// add event listeners to buttons
					$("#btnSave").click(function() {self.save();});
					$("#btnNew").click(function() {self.create();});
					$("#btnDelete").click(function() {self.kill();});
					$("#btnUndo").click(function() {self.undo();});
					$("#btnRedo").click(function() {self.redo();});
					$("#btnProperties").click(function() {self.details();});
					
					self._checkState();
					var url = settings.root + "/getLanguages";
					$.get(url, function( data ) {
						self.languages = data;
						
						var checked = "checked";
						for(var language in self.languages){
							$("#languageSelection").append("<input type='radio' name='language' value='"+language+"' "+checked+"> "+language.charAt(0).toUpperCase() + language.slice(1)+"<br>");
							checked = "";
						}
						$("#loadingLanguagesWarning").hide();
					});
				},
				create : function() {
					// reset
					self.selection = -1;
					var dialogContent = "<div id='createDialogContent'>"+
										"Name for Bot: <input id='createBotName' name='botName' type='text' value='MyBot'></input>"+
										"<form id='languageSelection'><div id='languageTitle'>Language:</div>";
					var checked = "checked";
					if(Object.keys(self.languages).length == 0){
						dialogContent+= "<span id='loadingLanguagesWarning'>Loading Languages, Please Wait.</span>";
					}
					for(var language in self.languages){
						dialogContent+= "<input type='radio' name='language' value='"+language+"' "+checked+"> "+language.charAt(0).toUpperCase() + language.slice(1)+"<br>";
						checked = "";
					}
					dialogContent += "</form></div>";
					bootbox.confirm(dialogContent, function(result){
						if(result==true){
							var botName = $("#createBotName").val();
							var botLanguage = $('input:radio[name=language]:checked').val();
							
							var url = settings.root + "template/" + botLanguage +"/"+ botName;
							var request = $.ajax({
								url : url,
								type : "GET"
							}).done(function(src) {
								$("#programList li").removeClass("selected");
								self._addBot({}, botName, botLanguage, src);
								self._editBotSrc(self.bots.length-1);
							}).fail(function(jqXHR, textStatus, error) {
								commons.handleError(textStatus, error);
							});
						}
					});
				},
				details : function() {
					var bot = self.bots[self.selection];
					var valueConverter = function(k,v) {
						return v;
					};
					var keyConverter = function(k) {
						if (k=='lastmodified') {
							return 'last modified';
						}
						else if (k=='qname') {
							return 'full name';
						}
						else if (k=='hasUnsavedChanges') {
							return 'has unsaved changes';
						}
						else return k;
					};
					if (bot) {
						commons.debug('showing bot details');
						// work with clone to add some derived properties
						var metadata = _.clone(bot.getMetadata());
						
						metadata.persistent = bot.isPersistent();
						metadata.hasUnsavedChanges = bot.hasUnsavedChanges();
						
						var keys = _.keys(metadata);
						keys.sort();
						var table = "<h3>Bot Properties</h3><table class=\"details\">";
						//table = table+ "<tr><th>property</th><th>value</th></tr>";
						_.each(keys,function(key) {
							table = table+"<tr class=\"details\"><td class=\"details-key\">";
							table = table+keyConverter(key)+':';
							table = table+"</td><td class=\"details-value\">";
							table = table+valueConverter(key,metadata[key]);
							table = table+"</td></tr>";
						});
						table = table+"</table>"
						bootbox.alert(table);
					}
				},
				kill : function() {
					var bot = self.bots[self.selection];
					// nothing to do if bot id is null
					if (bot.isPersistent()) {
						var url = bot.getDeleteURL();
						commons.debug("deleting bot from " + url);
						$.ajax({
							url : url,
							type : "DELETE"
						}).done(function(src) {
							self.bots = _.without(self.bots,bot);
							self._removeBotFromUI(self.selection);
							$("#output").html("Bot deleted");
							self._checkState();
						}).fail(function(jqXHR, textStatus, error) {
							var errorLoc = jqXHR.getResponseHeader("Location-Error");
							commons.debug("bot cannot be deleted");
							$("#output").html("Bot cannot be deleted");
							self._checkState();
						});
					}
					else {
						commons.debug("deleting unsaved bot " + bot.getId());
						self.bots = _.without(self.bots,bot);
						self._removeBotFromUI(self.selection);
						$("#output").html("Bot deleted");
					}
				},
				getBuildURL: function() {
					var bot = self.bots[self.selection];
					return bot.getBuildURL();
				},
				save : function() {
					var bot = self.bots[self.selection];
					var data = 	"user: " + settings.user + "\n" + 
							 	"botname: "+ bot.getName()+"\n" +
								self.ace.getValue();
					var url = bot.getBuildURL();
					commons.debug("saving bot at " + url);
					var jqxhr = $.ajax({
						url : url,
						type : "POST",
						data : data
					}).done(function(src) {				
						// get server metadata
						var url2 = jqxhr.getResponseHeader("Location-Metadata");
						commons.debug("done saving, fetching/updating bot metadata from " + url2);
						
						$.getJSON(url2).done(
								function(metadata) {
									var name1 = bot.getMetadata().name;
									bot.setMetadata(metadata);
									// update display name
									var name2 = bot.getMetadata().name;
									if (name1!=name2) {
										commons.debug("update name label for bot");
										$("#bot"+self.selection).html(name2);
									}
									bot.saved();
									self._checkState();
								});
						
						$("#output").html("Bot successfully compiled and saved");
					}).fail(function(jqXHR, textStatus, error) {
						var errorLoc = jqXHR.getResponseHeader("Location-Error");
						commons.debug("build error is available at " + errorLoc);
						self._displayBuildProblems(errorLoc);
						self._checkState();
					});
				},
				undo : function() {
					self.ace.getSession().getUndoManager().undo();
				},
				redo : function() {
					self.ace.getSession().getUndoManager().redo();
				},
				// private functions start with underscore
				_textChanged : function() {
					$("#output").empty();
					// clear markers
					self.ace.getSession().setAnnotations([]);
					// commons.debug("text changed");
					var bot = self.bots[self.selection];
					if (bot) {
						var unchanged = self.ace.getValue() == self.bots[self.selection].src;					
						self.bots[self.selection].changed(!unchanged);
						self._checkState();
					}
				},
				_checkState : function() {
					// disable / enable buttons
					var bot = self.bots[self.selection];
					var needsSave = false;
					if (bot && bot.hasUnsavedChanges()) {
						needsSave = true;
					} 
					$("#btnSave").prop('disabled', !needsSave);
					$("#btnDelete").prop('disabled', !bot);
					$("#btnProperties").prop('disabled', !bot);
					
					var undoManager = self.ace.getSession().getUndoManager();
					$("#btnUndo").prop('disabled', !undoManager.hasUndo());
					$("#btnRedo").prop('disabled', !undoManager.hasRedo());
				},
				_displayBuildProblems : function(errorLoc) {
					$.getJSON(errorLoc).done(
							function(data) {
								$("#output").empty();
								commons.debug(data);
								var issues = data.collection.items;
								for ( var i = 0; i < issues.length; i++) {
									var issue = issues[i];
									var html = "<div class=\"issue-" + issue.kind + "\">" + issue.kind;
									if (issue.linenumber&& issue.linenumber > 0) {
										self._markLine(issue.linenumber);
										html = html + " (line " + issue.linenumber + ")";
									}
									html = html + ": " + issue.message + "</div>";
									$("#output").append(html);
								}
							}).fail(function(jqxhr, textStatus, error) {

					});
				},
				_markLine : function(lineNumber) {				
					self.ace.getSession().setAnnotations([{
						row: lineNumber-1,
						column: 0,
						text: "error",
						type: "error"
					}]);
					var range = new Range(lineNumber, 0, lineNumber + 1, 10);
					commons.debug(range);
				},
				_removeBotFromUI : function(index) { 
					// commons.debug('remove bot ' + self.selection + ' from UI ');
					self.ace.setValue("");
					
					// rebuild menu
					$("#programList").empty();
					$("#programList").html("<ul></ul>");
					
					// easiest solution: reinitialize all bots
					var bots2 = self.bots;
					self.bots =[];
					self.index=-1;
					for (var i=0;i<bots2.length;i++) {
						var bot = bots2[i];
						self._addBot(bot.getMetadata(), bot.getName(), bot.getSrc());
					}
					
				},
				_loadPrograms : function() {
					var url = settings.root + "userbots/" + settings.user;
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
								$("#programList").empty();
								$("#programList").html("<ul></ul>");
								for ( var i = 0; i < botMetadataArr.length; i++) {
									var botMetadata = botMetadataArr[i];
									self._addBot(botMetadata,botMetadata.name);
								}
							}).fail(function(jqxhr, textStatus, error) {
						commons.handleError(textStatus, error);
					});
				},
				_addBot : function(metadata, name, language, src) {
					var bot = new Bot(metadata,src);
					if (!metadata["name"]) metadata["name"]=name;
					if (!metadata["language"]) metadata["language"]=language;
					var i = self.bots.length;
					self.bots.push(bot);
					var ul = $("#programList ul");
					ul.append("<li id=\"bot"+i+"\" data-index=\"" + i + "\" >" + name + "</li>");

					$("#programList li").off("click");
					$("#programList li").click(function() {
						self._editBotSrc($(this).attr("data-index"));
					});
				},
				_changeLanguage : function(language){
					if(language == "python27"){
						self.ace.getSession().setMode("ace/mode/python");
					}
					else if(language == "java"){
						self.ace.getSession().setMode("ace/mode/java");
					}
				},
				_editBotSrc : function(i) {
					self.selection = i;
					var bot = self.bots[i];
					self._changeLanguage(bot.getLanguage());
					
					//commons.debug("editing bot " + bot.getId());
					var src = bot.getSrc();
					if (src) {
						commons.debug("editing cached source for bot at "+ i);
					    self.ace.setValue(src);
					    $("#programList li").removeClass("selected");
						$("#bot"+i).addClass("selected");
						self._resetUndoManager();
						self._checkState();
					} else if (bot.isPersistent()) {
						var url = bot.getSrcURL();
						commons.debug("fetching bot source code from " + url);
						var request = $.ajax({
							url : url,
							type : "GET"
						}).done(function(src) {
							// cache
							bot.setSrc(src);
							self.ace.setValue(src);
						    $("#programList li").removeClass("selected");
						    $("#bot"+i).addClass("selected");
						    self._resetUndoManager();
						    self._checkState();
						}).fail(function(jqXHR, textStatus, error) {
							commons.handleError(textStatus, error);
						});
					} else {
						commons.warn("No bot selected");
						self._checkState();
					}
				},
				_resetUndoManager : function() {
					// reset undo manager, this only works in the scope of one document
					var undo_manager = self.ace.getSession().getUndoManager();
				    undo_manager.reset();
				    // this is really necessary !!
					self.ace.getSession().setUndoManager(undo_manager);
				}
				
			};
			self.init();
			return self;
		})
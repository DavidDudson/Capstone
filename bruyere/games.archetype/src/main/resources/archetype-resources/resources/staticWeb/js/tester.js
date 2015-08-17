define(
	['./bruyere-commons', './bruyere-settings','./bruyere-bot', 'domReady' ],
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
				
				// $("#btnPlayForward").click(function() {self.playForward();});
				// $("#btnPlayBackward").click(function() {self.playBackward();});
				// $("#btnPause").click(function() {self.pause();});
				// $("#btnStepBackward").click(function() {self.stepBackward();});
				// $("#btnStepForward").click(function() {self.stepForward();});
				
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
	              
				/* self._loadOwnBots();
				self._loadPublicBots();
				
				// draw cells
				var size = 100;
				for (var i=1;i<=size;i++) {
					$("#game").append("<div id=\"cell" + i + "\" class=\"square\">" + i + "</div>");
				}
				
				self._checkState(); */
			},
		};
		self.init();
		return self;
	});
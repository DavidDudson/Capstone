define(function () {
	var commons = {
			DEBUG : true,
			handleError : function(textStatus, error) {
				var err = textStatus + ", " + error;
				console.log("ERROR: " + err);
			},
			warn : function(message) {
				console.log("WARN: " + message);
			},
			debug : function(message) {
				if (this.DEBUG) {
					console.log("DEBUG: " + message);
				}
			}
		};
	return commons;
});

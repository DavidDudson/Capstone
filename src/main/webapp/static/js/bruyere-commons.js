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
			},
			notifyUser : function(title,message) {
				$("#errordialog-content").html(message);
				$("#errordialog").dialog({title: title,text: message,modal: true,width:350,height:300});
			},
			formatServerError : function(error,maxStackTraceSize) {
				var botEncounteringError = (error.winner==1)?2:1;
				var errorInfo = "<b>A server error occured during execution of bot " + botEncounteringError + "</b><br/>" +
					"error type: " + error.type +"<br/>";
					if (error.message) errorInfo = errorInfo + "message: " + error.message +"<br/>";
					errorInfo = errorInfo+"</hr>" + "stacktrace:<br/>";
					var stackTraceSize = Math.min(error.stacktrace.length, maxStackTraceSize);
					for (var i=0;i<stackTraceSize;i++) {
						errorInfo = errorInfo + error.stacktrace[i] + "<br/>";
					}
				return errorInfo;
			}
		};
	return commons;
});

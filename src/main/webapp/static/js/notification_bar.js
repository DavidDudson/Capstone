angular.module("app")
    .factory("NotificationBar", NotificationBarService);

function NotificationBarService() {

    return function (defaultText) {
        var notificationBar = {
            error: undefined,
            //position in queue
            position: 1,
            //Whether or not a build is active
            active: '',
            //eg success error etc.
            type: null,
            //The text to show on the bar
            text: defaultText,

            //Rests the build to default,
            reset: function () {
                notificationBar.position = 1;
                notificationBar.type = null;
                notificationBar.text = defaultText;
                notificationBar.active = '';
            },
            //Display A Warning
            showWarning: function(message){
                notificationBar.reset();
                notificationBar.position = 1;
                notificationBar.type = 'warning';
                notificationBar.text = message;
                notificationBar.active = '';
            },
            showSuccess: function(message){
                notificationBar.reset();
                notificationBar.position = 1;
                notificationBar.type = 'success';
                notificationBar.text = message;
                notificationBar.active = '';
            },
            showError: function(message){
                notificationBar.reset();
                notificationBar.position = 1;
                notificationBar.type = 'danger';
                notificationBar.text = message;
                notificationBar.active = '';
            },
            showProgress: function(message){
                notificationBar.reset();
                notificationBar.position = 1;
                notificationBar.type = '';
                notificationBar.text = message;
                notificationBar.active = 'active';
            }
        };
        return notificationBar;
    }
}
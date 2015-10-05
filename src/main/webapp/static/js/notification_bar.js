angular.module("app")
    .factory("NotificationBar", NotificationBarService);

function NotificationBarService() {

    return function () {
        var notificationBar = {
            //How many bots in queue
            complete: true,
            //position in queue
            position: 100,
            //Whether or not a build is active
            active: false,
            //eg success error etc.
            type: null,
            //The text to show on the bar
            text: 'Click Save or Test to get started',

            //Rests the build to default,
            reset: function () {
                notificationBar.position = 100;
                notificationBar.type = null;
                notificationBar.text = 'Click Save or Test to get started';
            }
        };
        return notificationBar;
    }
}
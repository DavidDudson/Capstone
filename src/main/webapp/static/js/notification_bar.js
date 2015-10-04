angular.module("app")
    .factory("NotificationBar", NotificationBarService);

function NotificationBarService($http) {

    return function () {
        var notificationBar = {
            //How many bots in queue
            total: 100,
            //position in queue
            position: 100,
            //eg success error etc.
            type: null,
            //The text to show on the bar
            text: '',

            //Rests the build to default,
            reset: function () {
                notificationBar.total = 100;
                notificationBar.position = 100;
                notificationBar.type = null;
                notificationBar.text = '';
            }
        };
        return notificationBar;
    }
}
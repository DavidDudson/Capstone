angular
    .module("app")
    .controller("modalCtrl", function ($scope, $modal) {
        //The modal itself
        $scope.instance = null;
        //The currently selected bot to use as a template
        $scope.selectedBot = null;
        // When the modal ok button is pressed create a new bot and close modal
        $scope.ok = function (name, bot) {
            $scope.instance.close({name: name, src: bot.src, xml: bot.xml, new: true});
        };
        //When the modal cancel button close the model
        $scope.cancel = function () {
            $scope.instance.dismiss();
        };
    });


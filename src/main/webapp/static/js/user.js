angular.module("app")
    .factory("User", UserService);

//Makes a user, Requires the BotService
function UserService(Bots) {
    return function (username, profilePicture) {
        var user = {
            name: username,
            profilePictureUrl: profilePicture,
            bots : Bots("__current_user"),
        };
        return user;
    }
}
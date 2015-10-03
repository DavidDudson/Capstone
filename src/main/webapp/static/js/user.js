angular.module("app")
    .factory("User", UserService);

//Makes a user, Requires the BotService
function UserService(Bots) {
    return function (username, profilePicture) {
        return {
            name: username,
            profilePictureUrl: profilePicture,
            bots: Bots("User")
        }
    }
}
angular.module("app")
    .factory("BotSelector", BotSelectorService);

function BotSelectorService() {

    return function (botLimit) {

        var selector = {
            limit: botLimit,
            bots: [],
            //Treats the bot list as a queue with a size of limit.
            select: function (bot) {
                selector.bots.push(bot);
                if (selector.bots.size === selector.limit) {
                    selector.bots.shift()
                }
            },
            reset: function(){
                selector.bots = [];
            }
        };
        return selector
    }
}
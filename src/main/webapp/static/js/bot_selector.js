angular.module("app")
    .factory("BotSelector", BotSelectorService);

function BotSelectorService() {

    return function (botLimit) {

        var selector = {
            limit: botLimit,
            bots: undefined,
            getBots: function(){
                return selector.bots
            },
            //Treats the bot list as a queue with a size of limit.
            select: function (bot) {
                if (!selector.bots) {
                    selector.bots = [bot];
                    selector.bots[0].colour = 'red';
                } else if (selector.limit === 1) {
                    selector.bots[0].colour = '';
                    selector.bots = [bot];
                    selector.bots[0].colour = 'red';
                } else {
                    selector.bots.push(bot);
                    if (selector.bots.length > selector.limit) {
                        var oldBot = selector.bots.shift();
                        oldBot.colour = '';
                    }
                    selector.bots[0].colour = 'red';
                    selector.bots[1].colour = 'blue';
                }

            }
        };
        return selector
    }
}
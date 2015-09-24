angular
    .module("app")
    //Blockly toolbox
    .directive("toolbox", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/toolbox.xml',
            replace: true
        }
    })
    //Initial blockly state
    .directive("blocklyInitial", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/blocklyInitial.xml',
            replace: true
        }
    })
    //Basically the Editor "class", it has functions you can call on it etc.
    .controller("editorCtrl", function ($modal, $http, $rootScope) {

        //Initialize blockly once the dom has finished loading,
        //aka after the toolbox has been injected
        addEventListener('load', function () {
            $rootScope.editor.initialize()
        }, false);


        //The reason i have done it this way is so in the
        //html you type editor.something, rather than just something.
        //This makes it clearer what the intention behind it is. eg. editor.save()
        $rootScope.editor = {
            //Initialize the blockly workspace
            initialize: function () {
                var workspace = Blockly.inject('blocklyDiv', {
                    toolbox: document.getElementById('toolbox'),
                    rtl: false,
                    comments: true,
                    collapse: true,
                    scrollbars: true,
                    grid: {
                        spacing: 25,
                        length: 3,
                        colour: '#ccc',
                        snap: true
                    }
                });
                //TODO, only call this line once a bot has been selected and use the bot src rather than this.
                //Also pull this data from the builtInBot source code, that way it isn't hardcoded here.
                Blockly.Xml.domToWorkspace(workspace, document.getElementById('initialBlocklyState'));
                workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];
            },
            //Save the current bot
            save: function () {
                //The data to use as the request body
                var data = {
                    name: user.bots.selected.name,
                    language: 'JAVA',
                    src: Blockly.Java.workspaceToCode(workspace, ["notests"])
                };
                //The actual request
                $http.post('bots', data)
                    .success(function () {
                        console.log("Build success")
                    })
                    .error(function () {
                        console.error("Build failure")
                    });
            },
            share: function () {
                console.log("This would share the bot")
            },
            //The modal for selecting create new bot options
            modal: {
                //The modal itself
                instance: null,
                //The currently selected bot to use as a template
                selectedBot: null,
                //When the modal ok button is pressed create a new bot and close modal
                ok: function (name, bot) {
                    $rootScope.editor.modal.instance.dismiss();
                    $rootScope.user.bots.list.push({name: name, src: bot.src})
                },
                //When the modal cancel button close the model
                cancel: function () {
                    $rootScope.editor.modal.instance.dismiss();
                },
                //Generate and display the modal
                create: function () {
                    $rootScope.editor.modal.instance = $modal.open({
                        animation: true,
                        templateUrl: './static/html/newBotModal.html',
                        resolve: {
                            bots: function () {
                                return $rootScope.builtInBots.list;
                            }
                        }
                    });
                }
            }
        }
    });

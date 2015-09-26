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
            workspace : null,
            workspaceXml : '<xml><xml>',
            blocklyConfig : {
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
            },
            selectedBot: null,
            allBots : function(){return $rootScope.builtInBots.list.concat($rootScope.user.bots.list)},
            //Initialize the blockly workspace
            initialize: function () {
                $rootScope.editor.workspace = Blockly.inject('blocklyDiv', $rootScope.editor.blocklyConfig);
                //TODO, only call this line once a bot has been selected and use the bot src rather than this.
                //Also pull this data from the builtInBot source code, that way it isn't hardcoded here.
                Blockly.Xml.domToWorkspace($rootScope.editor.workspace, document.getElementById('initialBlocklyState'));
                $rootScope.editor.workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];

            },
            build: {
                //How many bots in queue
                total: 100,
                //position in queue
                position: 100,
                //Whether a build is in progress
                active: '',
                //eg success error etc.
                type: null,
                //The text to show on the bar
                text: 'Press save to build your bot',

                //Updates the progress bar
                update: function (position, pass) {
                    $rootScope.editor.build.progress = position;
                    if (position != $rootScope.editor.build.total) {
                        $rootScope.editor.build.active = 'active';
                        $rootScope.editor.build.type = null;
                        $rootScope.editor.build.text =
                            'In queue... ' + $rootScope.editor.build.position + '/' + $rootScope.editor.build.total;
                    } else if (pass) {
                        $rootScope.editor.build.active = '';
                        $rootScope.editor.build.type = 'success';
                        $rootScope.editor.build.text = 'Build Success'
                    } else {
                        $rootScope.editor.build.active = '';
                        $rootScope.editor.build.type = 'error';
                        $rootScope.editor.build.text = 'Build Failure'
                    }
                }
            },
            //The modal for selecting create new bot options
            modal: {
                //The modal itself
                instance: null,
                //The currently selected bot to use as a template
                selectedBot: {name:'FirstSquareBot'},
                //When the modal ok button is pressed create a new bot and close modal
                ok: function (name, bot) {
                    console.log(name,bot);
                    $rootScope.editor.modal.instance.dismiss();
                    $rootScope.user.bots.list.push({name: name, src: bot.src, xml: bot.xml})
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

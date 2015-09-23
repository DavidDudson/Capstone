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
    .controller("editorCtrl", function ($http,$rootScope) {

        //The reason i have done it this way is so in the
        //html you type editor.something, rather than just something.
        //This makes it clearer what the intention behind it is. eg. editor.save()
        $rootScope.editor = {
            blocklyConfig: {
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
            //Initialize the blockly workspace
            initialize: function () {
                var workspace = Blockly.inject('blocklyDiv', $scope.editor.blocklyConfig);
                //TODO, only call this line once a bot has been selected and use the bot src rather than this.
                //Also pull this data from the builtInBot source code, that way it isn't hardcoded here.
                Blockly.Xml.domToWorkspace(workspace, document.getElementById('initialBlocklyState'));
                workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];
            },
            //Save the current bot
            save: function () {
                var data = {
                    name: user.bots.selected.name,
                    language: 'JAVA',
                    src: Blockly.Java.workspaceToCode(workspace, ["notests"])
                };

                $http.post('bots', data)
                    .success(function () {
                        console.log("Build success")
                    })
                    .error(function () {
                        console.error("Build failure")
                    });
            },
            //Create a new bot
            create: function (name) {
                if (name != "") {
                    $rootScope.user.bots.add(name);
                }
            }

        }
    });

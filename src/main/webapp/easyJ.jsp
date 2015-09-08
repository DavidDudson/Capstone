<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
    pageContext.setAttribute("screenName",
            session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture",
            session.getAttribute("userPicture"));
    nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services) application
            .getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
    String gameName = services.getGameSupport().getName();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Blockly Playground</title>
    <script type="text/javascript" src="static/js/EasyJ/blockly_uncompressed.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/logic.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/loops.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/math.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/text.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/lists.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/variables.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/procedures.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/java/customBlocks.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/messages.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/logic.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/loops.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/math.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/text.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/lists.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/variables.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/procedures.js"></script>
    <script type="text/javascript" src="static/js/EasyJ/blocks/customBlocks.js"></script>
    <!-- Load the Google Drive SDK Realtime libraries. -->
    <script src="https://apis.google.com/js/api.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

    <script data-main="static/js/main-editor" src="static/js/require.js" charset="utf-8"></script>
    <script src="static/js/ace.js" charset="utf-8"></script>
    <script src="static/js/jquery-1.7.2.min.js"></script>
    <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/bootbox.min.js"></script>
    <script src="static/js/underscore-min.js" charset="utf-8"></script>
    <script src="static/js/gameState.js"></script>
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/grid.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script>
        'use strict';
        // Depending on the URL argument, render as LTR or RTL.
        var rtl = (document.location.search == '?rtl');
        var workspace = null;

        function enableRealtimeSpecificUi() {
            var realtimeDiv = document.getElementById('realtime');
            realtimeDiv.display = 'block';
        }

        function start() {
            var toolbox = document.getElementById('toolbox');
            workspace = Blockly.inject('blocklyDiv',
                    {
                        rtl: rtl,
                        media: '/media/',
                        toolbox: toolbox,
                        comments: true,
                        disable: true,
                        collapse: true,
                        grid: {
                            spacing: 25,
                            length: 3,
                            colour: '#ccc',
                            snap: true
                        },
                        readOnly: false,
                        realtime: false,
                        realtimeOptions: {
                            clientId: 'YOUR CLIENT ID GOES HERE',
                            chatbox: {elementId: 'chatbox'},
                            collabElementId: 'collaborators'
                        },
                        scrollbars: true
                    });
            Blockly.Xml.domToWorkspace(workspace,
                    document.getElementById('mitch-startBlocks'));
            if (Blockly.Realtime.isEnabled()) {
                enableRealtimeSpecificUi();
            }
        }

        function toCode() {
            var output = document.getElementById('importExport');
            output.value = Blockly['Java'].workspaceToCode(workspace);
        }

        function compileAndSave() {
            alert(Blockly['Java'].workspaceToCode(workspace));

            var data = 	{};
            data.name = 'MyCoolAsBot';
            data.language = 'JAVA';
            data.src = Blockly['Java'].workspaceToCode(workspace);
            data = JSON.stringify(data);
            var url = 'http://localhost:8080/Capstone/bots';

            var dialog = $('#progressModal');
            $('#progressModal .error').hide();
            $('#progressModal .modal-title').html("Schedule build request ...");
            $('#progressModal .progress-bar').css('width', '0%');

            dialog.modal('show');

            $.ajax({
                dataType: "json",
                url : url,
                type : "POST",
                data : data
            }).done(function(src) {
                // get server metadata
                self._visualizeBuildStatus(src);
                self._updateBuildStatus(src.buildStatusURL);
            }).fail(function(jqXHR, error) {
                var errorLoc = jqXHR.getResponseHeader("Location-Error");
                console.log("build error is available at " + errorLoc);
//                self._displayBuildProblems(errorLoc);
//                self._checkState();
            });
        }

        function _updateBuildStatus(uri){
            $.getJSON(uri).done(function(src) {
                self._visualizeBuildStatus(src);
                if(src.done) {
                    $('#progressModal .progress-bar').css('width', '100%');
                    var bot = self.bots[self.selection];
                    if(src.error) {
                        $('#progressModal .modal-title').html("Load build errors...");
                        self._displayBuildProblems(src.error);
                    } else {
                        bot.setMetadata(src.metadata);
                        bot.setSrc(src);
                        bot.saved();
                        self._checkState();
                        $("#output").html("Bot successfully compiled and saved");
                        $('#progressModal .modal-title').html("Bot successfully compiled and saved");
                    }
                    setTimeout(function() {
                        $("#progressModal").modal("hide");
                    }, 500);
                } else {
                    console.log(src.buildStatusURL);
                    setTimeout(function() {
                        self._updateBuildStatus(src.buildStatusURL);
                        self.editor.focus();
                    }, 2000);
                }
            }).fail(function(error) {
                $('#progressModal .modal-title').html("Unexpected error occured. Try again.");
            });
        }

        function _visualizeBuildStatus(data) {
            if(data.currentPosition != -1) {
                var pos = (data.currentPosition-1);
                if(pos < 0 ) pos = 0;

                if(pos > 0) {
                    $('#progressModal .modal-title').html("Position in queue : " + pos);
                } else {
                    $('#progressModal .modal-title').html("Building ...")
                }

                var percentage = 1 - pos/data.queueSize;
                percentage = percentage * 66;

                $('#progressModal .progress-bar').css('width', percentage + '%');
            } else {
                $('#progressModal .modal-title').html("Still in queue ...");
            }
        }

    </script>
    <style>
        .inactiveLink {
            pointer-events: none;
            cursor: default;
        }
    </style>
</head>
<body onload="start()">
<div class="container_12">
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">StarBattle</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="test.jsp">Test </a></li>
        <li><a href="#">about </a></li>
        <li><a href="#">community </a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a class="inactiveLink">${screenName}</a></li>
        <li><a d="profilePicture2" href="login.jsp">Logout </a></li>
        <c:if test="${profilePicture != null}">
        <li>
            <img
                id="profilePictureURL" src="${profilePicture}"
                class="img-responsive img-rounded center-block"
                style="width: 40px; margin: 5px;" alt="Profile Picture">
        </li>
        </c:if>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
    <div id="blocklyDiv" style="height: 480px; width: 66%; float:left"></div>
    <div style="width: 30%; height: 480px; float:right">
        <textarea id="importExport" style="width: 100%; height: inherit"></textarea>
        <input type="button" onclick="toCode()" style="width:50%" value="Java">
        <button id="btnSave" type="button" onclick="compileAndSave()">Save + Compile </button>
    </div>
</div>
<xml id="toolbox" style="display: none">
    <category name="Logic">
        <block type="controls_if"></block>
        <block type="logic_compare"></block>
        <block type="logic_operation"></block>
        <block type="logic_negate"></block>
        <block type="logic_boolean"></block>
        <block type="logic_null"></block>
        <block type="logic_ternary"></block>
    </category>
    <category name="Loops">
        <block type="controls_repeat_ext">
            <value name="TIMES">
                <block type="math_number">
                    <field name="NUM">10</field>
                </block>
            </value>
        </block>
        <block type="controls_whileUntil"></block>
        <block type="controls_for">
            <value name="FROM">
                <block type="math_number">
                    <field name="NUM">1</field>
                </block>
            </value>
            <value name="TO">
                <block type="math_number">
                    <field name="NUM">10</field>
                </block>
            </value>
            <value name="BY">
                <block type="math_number">
                    <field name="NUM">1</field>
                </block>
            </value>
        </block>
        <block type="controls_forEach"></block>
        <block type="controls_flow_statements"></block>
    </category>
    <category name="Math">
        <block type="math_number"></block>
        <block type="math_arithmetic"></block>
        <block type="math_single"></block>
        <block type="math_trig"></block>
        <block type="math_constant"></block>
        <block type="math_number_property"></block>
        <block type="math_change">
            <value name="DELTA">
                <block type="math_number">
                    <field name="NUM">1</field>
                </block>
            </value>
        </block>
        <block type="math_round"></block>
        <block type="math_on_list"></block>
        <block type="math_modulo"></block>
        <block type="math_constrain">
            <value name="LOW">
                <block type="math_number">
                    <field name="NUM">1</field>
                </block>
            </value>
            <value name="HIGH">
                <block type="math_number">
                    <field name="NUM">100</field>
                </block>
            </value>
        </block>
        <block type="math_random_int">
            <value name="FROM">
                <block type="math_number">
                    <field name="NUM">1</field>
                </block>
            </value>
            <value name="TO">
                <block type="math_number">
                    <field name="NUM">100</field>
                </block>
            </value>
        </block>
        <block type="math_random_float"></block>
    </category>
    <category name="Text">
        <block type="text"></block>
        <block type="text_join"></block>
        <block type="text_append">
            <value name="TEXT">
                <block type="text"></block>
            </value>
        </block>
        <block type="text_length"></block>
        <block type="text_isEmpty"></block>
        <block type="text_indexOf">
            <value name="VALUE">
                <block type="variables_get">
                    <field name="VAR">text</field>
                </block>
            </value>
        </block>
        <block type="text_charAt">
            <value name="VALUE">
                <block type="variables_get">
                    <field name="VAR">text</field>
                </block>
            </value>
        </block>
        <block type="text_getSubstring">
            <value name="STRING">
                <block type="variables_get">
                    <field name="VAR">text</field>
                </block>
            </value>
        </block>
        <block type="text_changeCase"></block>
        <block type="text_trim"></block>
        <block type="text_print"></block>
        <block type="text_prompt_ext">
            <value name="TEXT">
                <block type="text"></block>
            </value>
        </block>
    </category>
    <category name="Lists">
        <block type="lists_create_empty"></block>
        <block type="lists_create_with"></block>
        <block type="lists_repeat">
            <value name="NUM">
                <block type="math_number">
                    <field name="NUM">5</field>
                </block>
            </value>
        </block>
        <block type="lists_length"></block>
        <block type="lists_isEmpty"></block>
        <block type="lists_indexOf">
            <value name="VALUE">
                <block type="variables_get">
                    <field name="VAR">list</field>
                </block>
            </value>
        </block>
        <block type="lists_getIndex">
            <value name="VALUE">
                <block type="variables_get">
                    <field name="VAR">list</field>
                </block>
            </value>
        </block>
        <block type="lists_setIndex">
            <value name="LIST">
                <block type="variables_get">
                    <field name="VAR">list</field>
                </block>
            </value>
        </block>
        <block type="lists_getSublist">
            <value name="LIST">
                <block type="variables_get">
                    <field name="VAR">list</field>
                </block>
            </value>
        </block>
        <block type="lists_split">
            <value name="DELIM">
                <block type="text">
                    <field name="TEXT">,</field>
                </block>
            </value>
        </block>
    </category>
    <sep></sep>
    <category name="Variables" custom="VARIABLE"></category>
    <category name="Functions" custom="PROCEDURE"></category>
    <sep></sep>
    <category name="StarBattles">
    </category>
    <sep></sep>
    <category name="Testing">
        <block type="get_gamestate"></block>
        <block type="variable_define"></block>
        <block type="get_next_valid_cell"></block>
    </category>
</xml>
<xml id="startBlocks" style="display: none">
    <block type="variables_set">
        <field name="VAR">game array</field>
        <value name="VALUE">
            <block type="variables_get">
                <field name="VAR">GameState</field>
            </block>
        </value>
    </block>
</xml>
<xml id="mitch-startBlocks" style="display:none">
    <block type="procedures_defreturn" id="1" x="63" y="63" deletable="false">
        <mutation></mutation>
        <field name="NAME">getNextMove</field>
        <value name="RETURN">
            <block type="get_next_valid_cell" id="2"></block>
        </value>
    </block>
    </div>
</xml>
<h2>Response:</h2>
<p id="errordialog"></p>
<p id="errordialog-content"></p>

</body>
</html>

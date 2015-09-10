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
            workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];
            if (Blockly.Realtime.isEnabled()) {
                enableRealtimeSpecificUi();
            }
        }

        function toCode() {
            var output = document.getElementById('importExport');
            output.value = Blockly.Java.workspaceToCode(workspace);
        }

        function compileAndSave() {

            var data = 	{};
            data.name = 'MyCoolAsBot';
            data.language = 'JAVA';
            data.src = document.getElementById('importExport').value;
            data = JSON.stringify(data);
            var url = 'http://localhost:8080/Capstone/bots';

            $.ajax({
                dataType: "json",
                url : url,
                type : "POST",
                data : data
            }).done(function(src) {
                // get server metadata
                console.log(src.buildStatusUrl)
                _visualizeBuildStatus(src);
                _updateBuildStatus(src.buildStatusURL);
            }).fail(function(jqXHR, error) {
                console.log(src.buildStatusUrl)
                var errorLoc = jqXHR.getResponseHeader("Location-Error");
                console.log("build error is available at " + errorLoc);
                _fetchGame('http://localhost:8080' + errorLoc)
            });
        }

        function _updateBuildStatus(uri){
            $.getJSON(uri).done(function(src) {
                _visualizeBuildStatus(src);
                if(src.done) {
                    var bot = {"name":"MyCoolAsBot"};
                    if(src.error) {
                        $('#progressModal .modal-title').html("Load build errors...");
                        alert(src.error);
                    } else {
                        bot.setMetadata(src.metadata);
                        bot.setSrc(src);
                        bot.saved();
                        $("#output").html("Bot successfully compiled and saved");
                        $('#progressModal .modal-title').html("Bot successfully compiled and saved");
                    }
                    setTimeout(function() {
                        $("#progressModal").modal("hide");
                    }, 500);
                } else {
                    console.log(src.buildStatusURL);
                    setTimeout(function() {
                        _updateBuildStatus(src.buildStatusURL);
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
        
        function toXml() {
            var output = document.getElementById('importExport');
            var xml = Blockly.Xml.workspaceToDom(workspace);
            output.value = Blockly.Xml.domToPrettyText(xml);
        }

        var commons = {
            DEBUG : true,
            handleError : function(textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("ERROR: " + err);
            },
            warn : function(message) {
                console.log("WARN: " + message);
            },
            debug : function(message) {
                if (this.DEBUG) {
                    console.log("DEBUG: " + message);
                }
            },
            notifyUser : function(title,message) {
                $("#errordialog-content").html(message);
//                $("#errordialog").dialog({title: title,text: message,modal: true,width:350,height:300});
            },
            formatServerError : function(error,maxStackTraceSize) {
                var botEncounteringError = (error.winner==1)?2:1;
                var errorInfo = "<b>A server error occured during execution of bot " + botEncounteringError + "</b><br/>" +
                        "error type: " + error.type +"<br/>";
                if (error.message) errorInfo = errorInfo + "message: " + error.message +"<br/>";
                errorInfo = errorInfo+"</hr>" + "stacktrace:<br/>";
                var stackTraceSize = Math.min(error.stacktrace.length, maxStackTraceSize);
                for (var i=0;i<stackTraceSize;i++) {
                    errorInfo = errorInfo + error.stacktrace[i] + "<br/>";
                }
                return errorInfo;
            }
        };


        function makeBotGame(){
            var url = "creategame_b2b";
            var bot1Id = "FirstSquareBot";
            var bot2Id = "LastSquareBot";
            var data = "" + bot1Id + "\n" + bot2Id + "\n";
            var jqxhr = $.ajax({
                url : url,
                type : "POST",
                data : data
            }).done(function(src) {
                // get results
                var url2 = jqxhr.getResponseHeader("Location");
                commons.debug("done creating game, results will be available at " + url2);

                _fetchGame(url2);
            }).fail(function(jqXHR, textStatus, error) {
                var errorLoc = jqXHR.getResponseHeader("Location-Error");
                commons.handleError(textStatus,"cannot post game");
                commons.notifyUser("Server Error",jqXHR.responseText);
                console.log(jqXHR);
                console.log(errorLoc);
                console.log(error);
            });
        }
        function _fetchGame(url) {
            var game = $.getJSON(url)
                    .done(function (data) {
                        $("#gameloadingdialog").dialog("close");
                        $("#errordialog-content").html(data);
                    })
                    .fail(function (error) {
                        commons.debug("game execution error");
                    });
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
    </category>
    <sep></sep>
    <category name="Variables" custom="VARIABLE"></category>
    <category name="Functions" custom="PROCEDURE"></category>
    <sep></sep>
    <category name="StarBattles">
        <block type="get_first_valid_coordinate"></block>
        <block type="get_last_valid_coordinate"></block>
        <block type="get_all_valid_moves"></block>
    </category>
    <sep></sep>
    <category name="Testing">
        <block type="get_neighbour_valid_coordinates"></block>
        <block type="get_gamestate"></block>
        <block type="can_attack_coordinate"></block>
        <block type="if_coordinate_hit_aim_direction"></block>
        <block type="get_coordinate_at_pos"></block>
        <block type="check_state_of_coordinate"></block>
    </category>
</xml>
<xml id="mitch-startBlocks" style="display:none">
    <block type="procedures_defreturn" id="1" x="63" y="63" deletable="false" editable="false">
        <mutation></mutation>
        <field name="NAME">nextMove</field>
        <value name="RETURN">
            <block type="get_first_valid_coordinate" id="2"></block>
        </value>
    </block>
</xml>
<button onclick="makeBotGame()">Demo a Game Request</button>
<h2>Response:</h2>
<p id="response">
<p id="errordialog"></p>
<p id="errordialog-content"></p>

</body>
</html>

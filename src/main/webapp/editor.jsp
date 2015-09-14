<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="nz.ac.massey.cs.ig.core.services.Services" %>
<%
    Services services = (Services) application.getAttribute(Services.NAME);

    pageContext.setAttribute("isDebug",services.getConfiguration().isDebug());
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
    pageContext.setAttribute("gameName", services.getGameSupport().getName());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${gameName} Editor</title>
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

    <script data-main="static/js/main-editor" src="static/js/require.js" charset="utf-8"></script>
    <script src="static/js/jquery-1.7.2.min.js"></script>
    <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/gameState.js"></script>
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/grid.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script>
        'use strict';
        var workspace = null;

        function start() {
            var workspace = Blockly.inject('blocklyDiv',
                    {
                        toolbox: document.getElementById('toolbox'),
                        rtl: false,
                        comments: true,
                        collapse: true,
                        scrollbars: true,
                        grid: {
                            spacing: 25,
                            length:3,
                            colour: '#ccc',
                            snap:true
                        }
                    });

            Blockly.Xml.domToWorkspace(workspace, document.getElementById('initialBlocklyState'));

            workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];
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
                console.log(src.buildStatusUrl);
                _visualizeBuildStatus(src);
                _updateBuildStatus(src.buildStatusURL);
            }).fail(function(jqXHR, error) {
                console.log(src.buildStatusUrl);
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
                    $('#progressModal .modal-title').html("Building ...");
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
                $("#errordialog").dialog({title: title,text: message,modal: true,width:350,height:300});
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
        };

        function _fetchGame(url) {
            var game = $.getJSON(url)
                    .done(function (data) {
                        $("#gameloadingdialog").dialog("close");
                        $("#errordialog-content").html(data);
                    })
                    .fail(function (error) {
                        commons.debug("game execution error");
                    });
            document.getElementById("response").value=JSON.stringify(game);
            alert(game);
        }

    </script>
</head>
<body onload="start()">
    <div class="container_12">
        <div id="header">
            <div id="nav_container">

                <div class="container_12" style="padding:0;">

                    <div id="nav_menu" class="left">
                        <div id="logo" class="left">
                            <a href="index.html"> Battle Spaceship </a>
                        </div>
                        <nav class="menu">
                            <a class="toggle-nav" href="#">&#9776;</a>
                            <ul class="list_inline active">
                                <li> <a href="editor.jsp"> Editor </a></li>
                                <li> <a href=""> Survey </a> </li>
                                <li> <div class="menu-on"> <a href=""> My Bots </a> </div> </li>
                            </ul>

                        </nav>
                    </div>


                    <div id="user" class="right">
                        <ul class="list_inline">
                            <li> <a d="profilePicture2" class="username" href="">${screenName}</a> </li>
                            <li> <a class="logout" href="index.jsp"> Logout </a> </li>
                            <c:if test="${profilePicture != null}">
                                <li class="profilePictureContent" id="profilePicture3"><img
                                        id="profilePictureURL" src="${profilePicture}"
                                        class="img-responsive img-rounded center-block"
                                        style="width: 40px; margin: 5px;" alt="Profile Picture"></li>
                            </c:if>
                        </ul>
                    </div>

                </div>

                <div class="clear"> </div>

            </div>
        </div>
        <br/>
        <br/>
        <div>
            <div style="width:20%; float:left; height: 600px">
                <div class="sidebar_box_inner">

                    <div class="sidebar_head">
                        My Bots
                    </div>

                    <div class="sidebar_content">
                        <ul class="list_block">
                            <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                            <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                            <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                            <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                            <li class="bot more"> <a href=""> more... </a> </li>
                        </ul>
                    </div>


                </div>
            </div>
        </div>
        <div id="blocklyDiv" style="background-color:rgba(0,0,0,0.4); height: 600px; width: 80%; float:right"></div>
        <div>
            <br/>
            <button id="btnSave" style="width: 50%; float:left;" onclick="compileAndSave()"> Save </button>
            <c:if test="${isDebug}">
                <input type="button" onclick="toCode()" style="width: 50%; float:right;" value="To Java">
                <br/><br/>
                <textarea id="importExport" style="width: 100%; height: 200px"></textarea>
            </c:if>
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
    <xml id="initialBlocklyState" style="display:none">
        <block type="procedures_defreturn" id="1" x="63" y="63" deletable="false" editable="false">
            <mutation></mutation>
            <field name="NAME">nextMove</field>
            <value name="RETURN">
                <block type="get_first_valid_coordinate" id="2"></block>
            </value>
        </block>
    </xml>

</body>
</html>

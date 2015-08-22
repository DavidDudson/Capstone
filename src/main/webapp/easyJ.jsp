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
    <script src="static/js/jquery-1.7.2.min.js"></script>
    <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script src="static/js/gameState.js"></script>
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/grid.css">
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
    </script>
</head>
<body onload="start()">
<div class="container_12">
    <div id="header">
        <div id="nav_container">
            <div id="nav_menu" class="left">
                <div id="logo" class="left">
                    <a href="index.html"> Battleship </a>
                </div>
                <nav class="menu">
                    <a class="toggle-nav" href="#">&#9776;</a>
                    <ul class="list_inline active">
                        <li><a href="easyJ.html"> Editor </a></li>
                        <li><a href=""> Help </a></li>
                        <li><a href=""> Community </a></li>
                        <li><a href=""> Survey </a></li>
                        <li><a href=""> About </a></li>
                        <li>
                            <div class="menu-on"><a href=""> My Bots </a></div>
                        </li>
                        <li>
                            <div class="menu-on"><a href=""> Built-in Bots </a></div>
                        </li>
                        <li>
                            <div class="menu-on"><a href=""> Shared Bots </a></div>
                        </li>
                        <li> <a href="easyJ.html"> Editor </a> </li>
                        <li> <a href=""> Help </a> </li>
                        <li> <a href=""> Community </a> </li>
                        <li> <a href=""> Survey </a> </li>
                        <li> <a href=""> About </a> </li>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="profilePictureContent" id="profilePicture1"><p
                                    class="navbar-text">${screenName}</p></li>
                            <li><a id="profilePicture2" href="logout">Logout</a></li>

                            <c:if test="${profilePicture != null}">
                                <li class="profilePictureContent" id="profilePicture3"><img
                                        id="profilePictureURL" src="${profilePicture}"
                                        class="img-responsive img-rounded center-block"
                                        style="width: 40px; margin: 5px;" alt="Profile Picture"></li>
                            </c:if>
                        </ul>
                        <li> <div class="menu-on"> <a href=""> My Bots </a> </div> </li>
                        <li> <div class="menu-on">  <a href=""> Built-in Bots </a> </div> </li>
                        <li> <div class="menu-on">  <a href=""> Shared Bots </a> </div> </li>
                    </ul>

                </nav>
            </div>
            <div id="collaborators"></div>
        </div>
    </div>
    <div id="blocklyDiv" style="height: 480px; width: 66%; float:left"></div>
    <div style="width: 30%; height: 480px; float:right">
        <textarea id="importExport" style="width: 100%; height: inherit"></textarea>
        </br>
        <input type="button" onclick="toCode()" style="width:100%" value="Java">
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
</body>
</html>

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
    <script type="text/javascript" src="static/js/Blockly/blockly_uncompressed.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/logic.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/loops.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/math.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/text.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/lists.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/variables.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/procedures.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/customBlocks.js"></script>
    <script type="text/javascript" src="static/js/Blockly/messages.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/logic.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/loops.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/math.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/text.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/lists.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/variables.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/procedures.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/customBlocks.js"></script>

    <script src="static/js/jquery-1.10.2.min.js"></script>
    <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="static/css/editor.css">
    <link rel="stylesheet" type="text/css" href="static/css/grid.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script src="static/js/editor.js"></script>
    <script src="static/js/test.js"></script>
</head>
<body onload="setupWorkspace(); getUserBots(addUserBotsToUI)">

<!--Header-->
<div id="header">
    <div id="nav_container">
        <div class="container_12" style="padding:0;">
            <div id="nav_menu" class="left">
                <nav class="menu">
                    <a class="toggle-nav" href="#">&#9776;</a>
                    <ul class="list_inline active">
                        <li><a href="index.jsp"> ${gameName} </a></li>
                        <li><a href="about.jsp"> About </a></li>
                        <li><a href="test.jsp"> Test </a></li>
                        <li><a href="http://tinyurl.com/ptbweh9"> Survey </a></li>
                    </ul>

                </nav>
            </div>
            <div id="user" class="right">
                <ul class="list_inline">
                    <li> <a d="profilePicture2" class="username" href="">${screenName}</a> </li>
                    <li class="logout" href="index.jsp"> Logout </li>
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

<div class="container_10">
    <div id="content">
        <div id="sidebar_left" class="sidebar left">
            <div id="my_bots" class="sidebar_box">
                <div class="sidebar_box_inner">

                    <div class="sidebar_head">
                        My Bots
                    </div>

                    <div class="sidebar_content">
                        <ul id="userBots" class="list_block">
                        </ul>
                    </div>

                </div>
            </div>
        </div>

        <div id="main_content">
            <div class="main-cont-menu">

                <ul>
                    <li>
                        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal"> New </button>
                    </li>
                    <li>
                        <button id="del" class="btn btn-info btn-lg" onclick="deleteCurrentBot()" disabled="disabled"> Delete </button>
                    </li>
                    <li>
                        <button id="save" class="btn btn-info btn-lg" onclick="saveBot()" disabled="disabled"> Save </button>
                    </li>
                </ul>
            </div>
            <div class="main-blockly">
                <div id="blocklyDiv" style="height:450px"></div>
            </div>
        </div>

        <div id="test_grid_box">
                <ul class="test_grid">
                    <%for (int i = 0; i < 10; i++) {%>
                    <%for (int j = 0; j < 10; j++) {%>
                    <li id="a<%=i * 10 + j%>"></li>
                        <%}%>
                    </br>
                    <%}%>

                </ul>

        </div>
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Modal Header</h4>
                    </div>
                    <p>name for bot:</p>
                    <input id="botName">

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="createNewBot()"> Submit </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

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
        <block type="get_neighbour_valid_coordinates"></block>
        <block type="get_gamestate"></block>
        <block type="can_attack_coordinate"></block>
        <block type="if_last_move_hit_aim_direction"></block>
        <block type="get_coordinate_at_pos"></block>
        <block type="check_state_of_coordinate"></block>
        <block type="last_move_sunk"></block>
        <block type="the_last_move"></block>
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
</html>

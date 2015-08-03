<%-- 
    Document   : admin
    Created on : 7/01/2015, 9:44:54 AM
    Author     : Marcel Kroll
--%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SaGaCo Project</title>
        <script src="../js/ace.js"></script>
        <script src="../js/jquery-1.10.2.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/bootbox.min.js"></script>
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href='../css/bruyere.css' rel='stylesheet' type='text/css' />
        <link href="../css/jquery-ui.min.css"  rel="stylesheet">
        <script src="../js/jquery-ui.min.js"></script>
        <script src="../js/authMethod.js"></script>
        <script src="../js/hello.min.js"></script>
        
            
        <script type="text/javascript">
            
            //variables for the client app to use
            var requestURL = window.location.protocol + "//" + window.location.host + "/" + window.location.pathname+"/";
            var userHasBeenSelected = 0;
            
            //prototype to check if a string is empty or either contains all white space characters
            String.prototype.isEmpty = function() {
                return (this.length === 0 || !this.trim());
            };
            
            String.prototype.containsUsers = function () {
                return this.replace(/^\s*/, "").replace(/\s*$/, "").replace(/^\n*/, "").replace(/\n*$/, "");
            }
            
            //on the load of the admin page we parse for the current white list data
            //also set up the dialog popup boxes
            window.onload = function(event){
                
                    showLogoutPane();
                    
                    //prevent the dialog box from opening automatically
                    $("#dialogNote").dialog({autoOpen:false, dialogClass:'noteTitle'});
                    $("#dialogWarn").dialog({autoOpen:false, dialogClass:'warnTitle'});
                    $("#dialogError").dialog({autoOpen:false, dialogClass:'errorTitle'});
                    $("#dialogConfirm").dialog({autoOpen:false, dialogClass:'errorTitle'});
                
                    //prevent a send request redirecting the page
                    event.preventDefault();
                    
                    parseWhiteList();
                    
                   
                    
            };
            
            //this function handles when the client knows it needs a new version of the whitelist
            //so it parses for a new copy of it
            function parseWhiteList(){
                $.post(requestURL,{"requestType":"getWhiteList","requestValue":""}, function(msg){     
                        var obj = JSON.parse(msg);
                        var whiteListArray = obj.whiteListString.split(",");
                        var arrayLength = whiteListArray.length - 1;
                        console.log(msg)
                        $("#userList").find('option').remove().end();
                        
                        if (arrayLength == 0){
                            $("#userList").append(new Option("No users whitelisted","No users whitelisted"));
                        }
                        
                        else{
                            for(var i=0; i<arrayLength; i++){
                                $("#userList").append(new Option(whiteListArray[i],whiteListArray[i])); 
                        }}                
               });
            }
            
            //this function handles for when a user attempts to add a user to the white
            function addUser(){
                
                var userName = $("#newUserInput").val();   
          
                if (userName.isEmpty()){
                    $("#dialogError").html("You must enter a valid user name into the provided field");
                    $("#dialogError").dialog("open");
                }
                
                else{
                    $.post(requestURL,{"requestType":"addUser","requestValue":userName}, function (msg){
                            var obj = JSON.parse(msg);
                            var result = obj.addUser
                            
                            if (result == "userAdded"){
                                $("#dialogNote").html(userName + " has been added to the whiteList");
                                $("#dialogNote").dialog("open");
                                parseWhiteList();
                            }
                            else if (result == "userExists"){
                                $("#dialogWarn").html(userName + " already exists within the whitelist");
                                $("#dialogWarn").dialog("open");
                            }
                            else if (result == "error"){
                                $("#dialogError").html("An unknown error has occured on the server");
                                $("#dialogError").dialog("open");
                            }
                            
                     });
                }
                
                $("#newUserInput").val("");
                
            }
            
            //this function handles for when a user attempts to remove a user from the white list
            function removeUser(){
                var userToRemove = $("#userList").val();
                
                if (userToRemove == null){
                    $("#dialogWarn").html("Please select a user to remove");
                    $("#dialogWarn").dialog("open");
                }
                else{
                
                $.post(requestURL,{"requestType":"removeUser","requestValue":userToRemove}, function (msg){
                    var obj = JSON.parse(msg);
                    var result = obj.removeUser;
                    
                    if (result == "userRemoved"){
                        $("#dialogNote").html(userToRemove + " has been removed from the whiteList");
                        $("#dialogNote").dialog("open");
                        parseWhiteList();
                    }
                    else if (result == "userNoLongerExists"){
                        $("#dialogWarn").html(userToRemove + " was already removed from the whiteList");
                        $("#dialogWarn").dialog("open");
                    }
                    else if (result == "error"){
                        $("#dialogError").html("An unknown error has occured on the server");
                        $("#dialogError").dialog("open");
                    }
                
                });
            }
            }
            
            function userSelected(){
                var selectedUser = $("#userList").val();

                if (selectedUser == "No users whitelisted"){}
                else{
                //set the user name as the one we are currently investigating
                $("#userToEdit").val(selectedUser);
                
                userHasBeenSelected = 1;
                
                //parse for what role is assigned to the current user
                parseForUserRole(selectedUser);
                
                 }
                
                
            }
            
            function parseForUserRole(userName){
                //this function sets the current role box with the role that is associated with the user
                console.log("parsing role for user: " + userName);
                
                $.post(requestURL,{"requestType":"getUserRole","requestValue":userName}, function (msg){
                            var obj = JSON.parse(msg);
                            var result = obj.getUserRole;                           
                            
                            if (result == "student"){
                                $("#userToEditCurrentRole").val(result);
                            }
                            else if (result == "admin"){
                                $("#userToEditCurrentRole").val(result);
                            }
                            else{
                                $("#dialogError").html("An unknown error has occured");
                                $("#dialogError").dialog("open");
                            }
                        });

            }
            
            function submitUserChange(){
                //this function is for if we want to commit a change of rights for a user
                console.log("submit user change");
                console.log($("#userToEditRole").val());
                
                var userName = $("#userToEdit").val();
                var newRole = $("#userToEditRole").val();
                var currentRole = $("#userToEditCurrentRole").val();
                
                if (userHasBeenSelected){
                    if (newRole == currentRole){
                        $("#dialogWarn").html("You must select a role that is different to the currently assigned role");
                        $("#dialogWarn").dialog("open");
                    }
                    
                    else{
                        console.log("submit user change")
                        console.log($("#userToEditRole").val())
                        
                        
                         $.post(requestURL,{"requestType":"setUserRole","requestValue":userName + "," + currentRole + "," + newRole}, function (msg){
                            var obj = JSON.parse(msg);
                            var result = obj.setUserRole;
                            
                            console.log("user role change")
                            console.log(result);
                            
                            if(result == "roleSet"){
                                $("#dialogNote").html(userName + " has been assigned the role " + newRole);
                                $("#dialogNote").dialog("open");
                                parseForUserRole(userName);
                            }
                           
                            else{
                                $("#dialogError").html("An unknown error has occured");
                                $("#dialogError").dialog("open");
                            }
                            
                            
                        });
                    } 
                }
                else{
                    $("#dialogWarn").html("You must select a user first");
                    $("#dialogWarn").dialog("open");
                }
                
            }
            
            function addMultiple(){
                var multipleInput = $("#multiInput").val();
   
                if (multipleInput.containsUsers()){
                    console.log("at least one user has been provided, executing")
                    
                    $.post(requestURL,{"requestType":"addMultiple","requestValue":multipleInput}, function (msg){
                        var obj = JSON.parse(msg);
                        var result = obj.addMultiple;
                        var resultMetaData = obj.addMultipleMeta;
                            
                        
                        if (result == "success"){
                            if (resultMetaData == 1){
                                var textReponseVariable = "user was"
                            }
                            else{
                                var textReponseVariable = "users where"
                            }
                            $("#dialogNote").html("Success " + resultMetaData + " " + textReponseVariable + " added to the white list");
                            $("#dialogNote").dialog("open");
                            parseWhiteList();
                        }
                        
                        else if (result == "allExist"){
                            $("#dialogWarn").html("All provided users where already found on the white list");
                            $("#dialogWarn").dialog("open");
                        }
                        
                        else if (result == "alreadyInWhiteList"){
                            var resultList = resultMetaData.split(";");
                            if (resultList[0] == 1){
                                var preTextReponseVariable = "user was"
                            }
                            else{
                                var preTextReponseVariable = "users where"
                            }
                            
                            if (resultList[1] == 1){
                                var postTextResponseVariable = "user was"
                            }
                            else{
                                var postTextResponseVariable = "users where"
                            }
                            var failedUsers = resultList[2].slice(0,-2);
                            
                            $("#dialogWarn").html("Partial Success " + resultList[0] + " " + preTextReponseVariable + " added to the whitelist, however the following " + resultList[1] + " " + postTextResponseVariable + " already in the white list: " + failedUsers);
                            $("#dialogWarn").dialog("open");
                            parseWhiteList();
                        }
                        
                        else if (result == "error"){
                            $("#dialogError").html("An unknown error has occured on the server");
                            $("#dialogError").dialog("open");
                        }
                    });
                }
                
                else{
                    $("#dialogWarn").html("You provide at least one user to add to the whitelist");
                    $("#dialogWarn").dialog("open");
                }
                           
            }
            
            function removeAllUsers(){
                console.log("Warning about to remove all users from whiteList");
                
                $("#dialogConfirm").html("Warning you are about to delete all entries from the white list! Are you sure you want to do this?");
                $("#dialogConfirm").dialog({resizable: false, modal: true,
                    buttons: {"Delete all items": function() {
                        $( this ).dialog( "close" );
                        $.post(requestURL,{"requestType":"clearWhiteList","requestValue":""}, function (msg){
                            var obj = JSON.parse(msg);
                            var result = obj.clearWhiteList;
                            
                            if (result == "cleared"){
                                $("#dialogWarn").html("The white list has been cleared of all users");
                                $("#dialogWarn").dialog("open");
                                parseWhiteList();
                            }
                            else {
                                $("#dialogError").html("An unknown error has occured on the server");
                                $("#dialogError").dialog("open");
                            }
                        });
          
                },
                    Cancel: function() {
                        $( this ).dialog( "close" );
          
                    }
                  }
                })
        
                $("#dialogConfirm").dialog("open");
            }
            
        </script>
    </head>

    <body>
        <%
            pageContext.setAttribute("screenName", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userName"));
            pageContext.setAttribute("profilePicture", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userPicture"));
        %>
        
        <!-- these divs contain the various popup boxes that the application will use-->
        <div id="dialogNote" title="Notification"></div>
        <div id="dialogWarn" title="Warning"></div>
        <div id="dialogError" title="Error"></div>
        <div id="dialogConfirm" title="Warning Confirm Deletion"></div>
        <!-- end of dialog declarations-->
          
        
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="brand">Bruyere Administrative Control Panel</a>
                        <div class="profilePictureContent" id="profilePicture3"><img id="profilePictureURL" src="${profilePicture}" alt="Profile Picture" width="40" height="40"></div>
                        <div class="profilePictureContent" id="profilePicture2"><a href="../../logout">Logout</a></div>
                        <div class="profilePictureContent" id="profilePicture1">${screenName}</div>          
                </div>
            </div>
        </div>    
        
        <br>
        
        <section>
            <div class="container">
                <div class="adminHeader">Bruyere Administrative Panel</div> 
            </div>
            
            <div class="usersBox">
                <div class="usersTitle">Authorized Users</div>
                <div class="usersList">
                    <select id="userList" onchange="userSelected()" size="23">
                        <option value="Loading...">Loading...</option>        
                    </select>
                    
                    <p>
                        <button class="btn btn-primary" type="button" onclick="removeUser()"><i class="icon-trash icon-white"></i>&nbsp;&nbsp;Remove Selected</button>
                        <button class="btn btn-primary" type="button" onclick="parseWhiteList()"><i class="icon-refresh icon-white"></i></button>
                    </p>
                    
                    <p><button class="btn btn-primary" type="button" onclick="removeAllUsers()"><i class="icon-trash icon-white"></i>&nbsp;&nbsp;Remove All Users</button></p>
                    <input type="text" id="newUserInput" placeholder="WhiteList New User">
                    <button class="btn btn-primary" type="button" onclick="addUser()"><i class="icon-file icon-white"></i>&nbsp;&nbsp;Add User</button>
                </div>  
            
            </div>            
            
            
            <div class="container">
                
                <div class="adminContainer">
                    
                    <div class="multiInputBox">
                        <div class="multiInputTitle">WhiteList Multiple Users</div>
                        
                        <div class="multiInputForm">      
                            <textarea id="multiInput" placeholder="#Example of adding multiple Users#
John Smith 
12345678 
joeblogs@hotmail.com
"></textarea><br>
                            <button class="btn btn-primary" type="button" onclick="addMultiple()"><i class="icon-file icon-white"></i>&nbsp;&nbsp;Add All Users</button>
                        </div>
                    </div>
                    
                    
                
                </div>
                
                <div class="editContainer">
                    <div class="editUserBox">
                        
                        <div class="editUserTitle">Edit User</div>
                    
                        <div class="editUserForm">
                            
                            <label>User To Edit:</label><input disabled id="userToEdit" type="text">
                            <label>Currently Assigned Role:</label><input disabled id="userToEditCurrentRole" type="text">
                            
                                <label>Role To Assign:</label> 
                                <select id="userToEditRole">
                                    <option value="student">student</option>
                                    <option value="admin">admin</option>
                                </select>
                            
                            <br><br>    
                            <label><button class="btn btn-primary" type="button" onclick="submitUserChange()"><i class="icon-pencil icon-white"></i>&nbsp;&nbsp;Invoke Changes</button></label>
                            
                            
                        </div>
                        
                    
                    </div>
                </div>
                
                  
            </div>
       
        
        </section>
    
    </body>
</html>
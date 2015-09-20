<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="nz.ac.massey.cs.ig.core.services.Services" %>
<%
    Services services = (Services) application.getAttribute(Services.NAME);

    pageContext.setAttribute("isDebug",services.getConfiguration().isDebug());
    pageContext.setAttribute("gameName", services.getGameSupport().getName());
%>

<!DOCTYPE html>
<html>
    <head>

        <title>${gameName} Home</title>

        <link rel="stylesheet" type="text/css" href="static/css/style.css">
        <link rel="stylesheet" type="text/css" href="static/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="static/css/bootstrap-social.css">
        <link rel="stylesheet" type="text/css" href="static/css/font-awesome.css" rel="stylesheet" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script>
            var getUrl = window.location;
            var baseUrl = getUrl.protocol + "//" + getUrl.host + "/"
                    + getUrl.pathname.split('/')[1];
            $(".callbackUrlElement").val(baseUrl);
            $(".baseURLElement").val(baseUrl);
        </script>
    </head>

    <body>

        <div style="clear: both"></div>
        <br />

        <div class="main container">
            <div class="row">

                <div class="jumbotron">
                    <h1>${gameName}</h1>
                </div>
            </div>
            <div >
                    <div id="ldapLogin" class="text-center">
                <div id="socialLogin" class="">
                    <div class="paddingBlock"></div>
                    <!-- Login buttons for social authentication -->
                    <div class="googlePlusButton" style="float:right">
                        <form action="SocialLogin" method="POST" style="width: 200px;"
                              class="center-block">
                            <input type="hidden" name="loginType" value="google" /> <input
                                type="hidden" class="callbackUrlElement" name="callbackurl"
                                value="http://localhost:8080/Capstone" /> <input type="hidden" class="baseURLElement"
                                name="baseURL" value="" />
                            <p>
                                <button type="submit"
                                        class="btn btn-block btn-social btn-google-plus">
                                    <i class="fa fa-google-plus"></i>Sign in with Google
                                </button>
                            </p>
                        </form>
                    </div>

                    <div class="facebookButton" style="float:left">
                        <form action="SocialLogin" method="POST" style="width: 200px;"
                              class="center-block">
                            <input type="hidden" name="loginType" value="facebook" /> <input
                                type="hidden" class="callbackUrlElement" name="callbackurl"
                                value="http://localhost:8080/Capstone" /> <input type="hidden" class="baseURLElement"
                                name="baseURL" value="" />
                            <p>
                                <button type="submit"
                                        class="btn btn-block btn-social btn-facebook">
                                    <i class="fa fa-facebook"></i>Sign in with Facebook
                                </button>
                            </p>
                        </form>
                    </div>
                </div>

                <c:if test="${isDebug}">

                        <% if(request.getAttribute("authMessage") != null) { %>
                        <div class="alert alert-danger" role="alert">${authMessage}</div>
                        <% } %>
                        <!-- This form is for handling LDAP authentication requests-->
                        <form name="loginform" action="login" method="POST" class="center-block loginForm">
                            <input type="hidden" class="baseURLElement" name="baseURL" value="" />
                            <input type="hidden" class="form-control" name="username" required="required" placeholder="Username" value="Debug" />
                            <input type="hidden" name="password" class="form-control" required="required" placeholder="Password" value="DebugPassword"/><br />
                            <input class="form-control" type="submit" value="Login"><br>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
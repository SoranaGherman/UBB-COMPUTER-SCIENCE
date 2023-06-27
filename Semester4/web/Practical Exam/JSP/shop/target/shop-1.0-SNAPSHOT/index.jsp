<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Web - Assignment 9</title>
    <link rel="stylesheet" href="styles/login.css">
</head>
<body>
<form id="login-form" action="${pageContext.request.contextPath}/login-controller" method="post">
    Enter username : <input type="text" name="username"> <BR>
    <input type="submit" value="LogIn"/>
</form>

<%
    String error = ((String) request.getSession().getAttribute("error"));
    if (error == null) {
        error = "";
    }
%>

<div><%=error%></div>
</body>
</html>



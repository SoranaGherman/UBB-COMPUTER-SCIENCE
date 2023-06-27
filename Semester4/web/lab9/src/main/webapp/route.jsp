<%--
  Created by IntelliJ IDEA.
  User: soranaaa
  Date: 16.05.2023
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The route</title>

</head>
<body>
    <% if (request.getAttribute("routeNames") != null) { %>
    <h2>Route Names</h2>
    <ul>
        <% for (String routeName : (java.util.ArrayList<String>) request.getAttribute("routeNames")) { %>
        <li><%= routeName %></li>
        <% } %>
    </ul>
    <% } %>


    <form action="${pageContext.request.contextPath}/logout-controller" method="get">
        <input type="submit" value="LogOut">
    </form>

</body>
</html>

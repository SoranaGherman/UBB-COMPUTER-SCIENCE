<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Success Page</title>
  <link rel="stylesheet" type="text/css" href="styles/main.css">

</head>
<body>

<h2>List of Players</h2>
<form action="${pageContext.request.contextPath}/player-controller" method="get">
  <table>
    <thead>
    <tr>
      <th>Player Name</th>
      <th>Player Position</th>
    </tr>
    </thead>
    <tbody>
    <% for (com.example.sport.model.Player player: (java.util.ArrayList<com.example.sport.model.Player>) request.getAttribute("players")) { %>
    <tr>
      <td><%= player.getName()%></td>
      <td><%= player.getPosition()%></td>
    </tr>
    <% } %>
    </tbody>
  </table>
</form>

<form action="${pageContext.request.contextPath}/player-controller" method="get">
  <label for="search_name">Search player name:</label>
  <input type="text" id="search_name" name="search_name">
  <input type="submit" value="SEARCH PLAYER"/>
</form>

<h2>List of Relatives</h2>
<form action="${pageContext.request.contextPath}/teamMembers-controller" method="get">
  <table>
    <thead>
    <tr>
      <th>Player Name</th>
      <th>Player Position</th>
    </tr>
    </thead>
    <tbody>
    <% for (com.example.sport.model.Player player: (java.util.ArrayList<com.example.sport.model.Player>) request.getAttribute("relatives")) { %>
    <tr>
      <td><%= player.getName()%></td>
      <td><%= player.getPosition()%></td>
    </tr>
    <% } %>
    </tbody>
  </table>
</form>

<form action="${pageContext.request.contextPath}/teamMembers-controller" method="get">
  <label for="degree">Enter degree:</label>
  <input type="number" id="degree" name="degree">
  <input type="submit" value="SHOW RELATIVES"/>
</form>

</body>
</html>


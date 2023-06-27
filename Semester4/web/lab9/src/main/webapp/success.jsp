<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Success Page</title>
    <link rel="stylesheet" type="text/css" href="styles/city.css">
<%--    <script src="scripts/neighbourhoodButton.js"></script>--%>
</head>
<body>
<h1 style="color: deeppink; font-weight: bold; align-content: center; font-size: 40px">
    Hello, <%= ((com.example.demo1.domain.User) session.getAttribute("user")).getUsername() %>!
</h1>

<h2>List of Cities</h2>
<form action="${pageContext.request.contextPath}/connection-controller" method="get">
    <table>
        <thead>
        <tr>
            <th>Select city</th>
            <th>Name</th>
        </tr>
        </thead>
        <tbody>
        <% for (com.example.demo1.domain.City city : (java.util.ArrayList<com.example.demo1.domain.City>) request.getAttribute("cities")) { %>
        <tr>
            <td><input type="radio" class="radioButton" name="selectedCityId" value="<%= city.getCityId() %>"></td>
            <td><%= city.getName() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <br>

    <label>Selected City: <%= request.getAttribute("currentCity") %></label>

    <br><br>

    <input type="submit" id="showNeighborhoodButton" value="Show neighborhood" />

</form>

<form action="${pageContext.request.contextPath}/connection-controller" method="get">
    <input type="hidden" name="action" value="showRouteIds">
    <input type="submit" value="Show Route">
</form>

<form action="${pageContext.request.contextPath}/connection-controller" method="get">
<input type="hidden" name="action" value="undo">
<input type="submit" name="undo" value="undo"/>
</form>


<script>
    const radioButtons = document.querySelectorAll('input[type="radio"][name="selectedCityId"]');
    const submitButton = document.getElementById('showNeighborhoodButton');

    radioButtons.forEach(function(radioButton) {
        radioButton.addEventListener('change', function() {
            const checkedRadioButton = document.querySelector('input[type="radio"][name="selectedCityId"]:checked');
            if (checkedRadioButton) {
                submitButton.style.display = 'block'; // Show the button
            } else {
                submitButton.style.display = 'none'; // Hide the button
            }
        });
    });
</script>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: soranaaa
  Date: 19.06.2023
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--
  Created by IntelliJ IDEA.
  User: soranaaa
  Date: 19.06.2023
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>List of Results</h2>
<form action="${pageContext.request.contextPath}/result-controller" method="get">
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Result</th>
        </tr>
        </thead>
        <tbody>
        <% for (com.example.quiz.model.Result result: (java.util.ArrayList<com.example.quiz.model.Result>) request.getAttribute("results")) { %>
        <tr>
            <td><%= result.getUsername()%></td>
            <td><%= result.getResult()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</form>

</body>
</html>


</body>
</html>

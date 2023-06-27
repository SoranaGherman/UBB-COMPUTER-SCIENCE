<%--
  Created by IntelliJ IDEA.
  User: soranaaa
  Date: 19.06.2023
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Page</title>
    <link rel="stylesheet" type="text/css" href="styles/main.css">
    <script src="scripts/quiz_create.js"></script>

</head>

<body>
<h2>List of Questions</h2>

<form action="${pageContext.request.contextPath}/quiz-controller" method="get">
    <table>
        <thead>
        <tr>
            <th>QuestionText</th>
        </tr>
        </thead>
        <tbody>
        <% for (com.example.quiz.model.Question question: (java.util.ArrayList<com.example.quiz.model.Question>) request.getAttribute("questions")) { %>
        <tr>
            <td><%= question.getText()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</form>

<form action="${pageContext.request.contextPath}/result-controller" method="post">
    Enter results : <input type="text" name="results"> <BR>
    <input type="submit" value="SHOW RESULTS"/>
</form>

</body>
</html>

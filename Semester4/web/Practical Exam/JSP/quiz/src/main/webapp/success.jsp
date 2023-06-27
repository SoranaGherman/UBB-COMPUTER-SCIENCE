<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Success Page</title>
    <link rel="stylesheet" type="text/css" href="styles/main.css">
    <script src="scripts/quiz_create.js"></script>

</head>
<body>
<h1 style="color: deeppink; font-weight: bold; align-content: center; font-size: 40px">
    Hello, <%= ((com.example.quiz.model.User) session.getAttribute("user")).getUsername() %>!
</h1>

<h2>List of Questions</h2>
<form action="${pageContext.request.contextPath}/connection-controller" method="get">
    <table>
        <thead>
        <tr>
            <th>QuestionText</th>
            <th>Question Correct Answer</th>
            <th>Question Wrong Answer</th>
        </tr>
        </thead>
        <tbody>
        <% for (com.example.quiz.model.Question question: (java.util.ArrayList<com.example.quiz.model.Question>) request.getAttribute("questions")) { %>
        <tr>
            <td><%= question.getText()%></td>
            <td><%= question.getCorrectAnswer()%></td>
            <td><%= question.getWrongAnswer()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</form>

<form action="${pageContext.request.contextPath}/question-controller" method="get">
<label for="quizId">Enter quiz id:</label>
<input type="number" id="quizId" name="quizId">
<input type="submit" value="Do quiz"/>
</form>


<form action="quiz-controller" method="post">
    <label for="title">Enter title:</label>
    <input type="text" id="title" name="title">
    <label for="numInputs">Enter the number of inputs:</label>
    <input type="number" id="numInputs" name="numInputs">
    <button type="button" onclick="generateInputFields()">Generate Inputs</button>
    <div id="inputContainer"></div>
    <button type="submit">ADD NEW QUIZ</button>
</form>

</body>
</html>

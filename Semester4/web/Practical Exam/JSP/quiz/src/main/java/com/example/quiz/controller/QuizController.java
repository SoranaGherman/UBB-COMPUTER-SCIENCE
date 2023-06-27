package com.example.quiz.controller;

import com.example.quiz.db.DbManager;
import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "quiz-controller", value="/quiz-controller")
public class QuizController extends HttpServlet {

    private int id = 0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check if the user is logged in by verifying the session
        HttpSession session = req.getSession(false); // Pass `false` to prevent the creation of a new session

        if (session != null && session.getAttribute("user") != null) {
            // User is logged in

            // Create an instance of DbManager to interact with the database
            DbManager dbManager = new DbManager();
            RequestDispatcher rd = null;

            // Retrieve the list of cities from the database
            ArrayList<Question> questions = dbManager.getAllQuestions();

            // Pass the list of cities as a request attribute
            req.setAttribute("questions", questions);

            // Forward the request to the JSP page for rendering
            rd = req.getRequestDispatcher("/success.jsp");

            rd.forward(req, resp);

        }
        else{
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("title");
        String result="";
        int numOfInputs = Integer.parseInt(request.getParameter("numInputs"));
        System.out.println(result);

        for(int i=0; i < numOfInputs; i++){
            String question_name = "inputField" + i;
            String question_value = request.getParameter(question_name);
            result = result + " " + question_value;
        }

        RequestDispatcher rd = null;
        DbManager dbmanager = new DbManager();
        Quiz quiz = new Quiz(id, title, result);
        this.id++;
        dbmanager.createQuiz(quiz);

        ArrayList<Question> questions = dbmanager.getAllQuestions();


        // Pass the list of cities as a request attribute
        request.setAttribute("questions", questions);

        // Forward the request to the JSP page for rendering
        rd = request.getRequestDispatcher("/success.jsp");

        rd.forward(request, response);

    }
}

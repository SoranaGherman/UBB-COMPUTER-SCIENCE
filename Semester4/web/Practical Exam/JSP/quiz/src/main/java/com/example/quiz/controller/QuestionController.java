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

@WebServlet(name = "question-controller", value="/question-controller")
public class QuestionController extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check if the user is logged in by verifying the session
        HttpSession session = req.getSession(false); // Pass `false` to prevent the creation of a new session

        if (session != null && session.getAttribute("user") != null) {
            // User is logged in

            // Create an instance of DbManager to interact with the database
            DbManager dbManager = new DbManager();
            RequestDispatcher rd = null;

            int quizId = Integer.parseInt(req.getParameter("quizId"));

            session.setAttribute("quizId", quizId);

            // Retrieve the list of cities from the database
            String questions_ids= dbManager.getQuiz(quizId);

            System.out.println(quizId);

            System.out.println(questions_ids);


            ArrayList<Question> questions = dbManager.getQuestionsOfQuiz(questions_ids);
            System.out.println(questions);

            // Pass the list of cities as a request attribute
            req.setAttribute("questions", questions);

            // Forward the request to the JSP page for rendering
            rd = req.getRequestDispatcher("/quiz.jsp");

            rd.forward(req, resp);

        }
        else{
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

}

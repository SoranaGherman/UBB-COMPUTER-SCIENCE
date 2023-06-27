package com.example.quiz.controller;

import com.example.quiz.db.DbManager;
import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.model.Result;
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

@WebServlet(name = "result-controller", value="/result-controller")

public class ResultController extends HttpServlet {

    private int id = 0;
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String result = request.getParameter("results");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String name = user.getUsername();
        RequestDispatcher rd = null;

        DbManager dbmanager = new DbManager();


        int quizId  = (int) session.getAttribute("quizId");
        String ids = dbmanager.getQuiz(quizId);

        int score = dbmanager.calculateScore(result, ids);

        dbmanager.insertResult(new Result(id, name, score));

        ArrayList<Result> results = dbmanager.getAllResults();

        request.setAttribute("results", results);

        rd = request.getRequestDispatcher("/result.jsp");

        rd.forward(request, response);
    }

}

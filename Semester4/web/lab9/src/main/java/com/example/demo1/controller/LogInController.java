package com.example.demo1.controller;

import com.example.demo1.db.DbManager;
import com.example.demo1.domain.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "login-controller", value="/login-controller")
public class LogInController extends HttpServlet {
    public LogInController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        RequestDispatcher rd = null;

        DbManager dbmanager = new DbManager();
        User user = dbmanager.authenticate(username, password);

        ArrayList<Integer> rid = new ArrayList<Integer>();
        System.out.println(rid);
        request.setAttribute("route_ids", rid);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect to the CitiesController
            response.sendRedirect(request.getContextPath() + "/cities-controller");
        } else {
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
}
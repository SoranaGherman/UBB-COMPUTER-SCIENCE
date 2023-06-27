package com.example.shop.controller;

import jakarta.servlet.http.HttpServlet;

import com.example.shop.model.*;

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

        String name = request.getParameter("username");
        RequestDispatcher rd = null;

        ArrayList<Product> products = new ArrayList<>();
        request.setAttribute("products", products);

        ArrayList<Order> orders = new ArrayList<>();

        if (name != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", name);
            session.setAttribute("orders", orders);

            rd = request.getRequestDispatcher("/shop.jsp");
            rd.forward(request, response);
        } else {
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
}

package com.example.shop.controller;
import com.example.shop.db.DbManager;
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


@WebServlet(name = "cancel-controller", value="/cancel-controller")

public class CancelController extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");

        ArrayList<Order> finalize = new ArrayList<>();
        session.setAttribute("orders", finalize);

        ArrayList<Product> products = new ArrayList<>();
        request.setAttribute("products", products);

        RequestDispatcher rd = null;

        if (name != null) {
            rd = request.getRequestDispatcher("/shop.jsp");
            rd.forward(request, response);
        } else {
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
}


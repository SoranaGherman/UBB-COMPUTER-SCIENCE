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


@WebServlet(name = "order-controller", value="/order-controller")
public class OrderController extends HttpServlet{
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");

        RequestDispatcher rd = null;

        if (name != null) {
            rd = request.getRequestDispatcher("/order.jsp");
            rd.forward(request, response);
        } else {
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check if the user is logged in by verifying the session
        HttpSession session = req.getSession(false); // Pass `false` to prevent the creation of a new session

        if (session != null && session.getAttribute("username") != null) {
            // User is logged in

            // Create an instance of DbManager to interact with the database
            DbManager dbManager = new DbManager();
            RequestDispatcher rd = null;

            String name = (String) session.getAttribute("username");
            int productId = Integer.parseInt(req.getParameter("product_id"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            Order order = new Order(-1, name, productId, quantity);

            // Retrieve the list of cities from the database
            ArrayList<Order> orders = (ArrayList<Order>) session.getAttribute("orders");
            orders.add(order);

            session.setAttribute("orders", orders);

            ArrayList<Product> products = new ArrayList<>();
            req.setAttribute("products", products);


            // Forward the request to the JSP page for rendering
            rd = req.getRequestDispatcher("/shop.jsp");

            rd.forward(req, resp);

        }
        else{
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}

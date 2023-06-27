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


@WebServlet(name = "product-controller", value="/product-controller")
public class ProductController extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = null;

        DbManager dbmanager = new DbManager();

        String name = request.getParameter("product_name");
        String description = request.getParameter("product_description");

        Product p = new Product(-1, name, description);
        dbmanager.addProduct(p);

        rd = request.getRequestDispatcher("/shop.jsp");
        rd.forward(request, response);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check if the user is logged in by verifying the session
        HttpSession session = req.getSession(false); // Pass `false` to prevent the creation of a new session

        if (session != null && session.getAttribute("username") != null) {
            // User is logged in

            // Create an instance of DbManager to interact with the database
            DbManager dbManager = new DbManager();
            RequestDispatcher rd = null;

            String name = req.getParameter("search_product_name");

            // Retrieve the list of cities from the database
            ArrayList<Product> products = dbManager.getAllProductsByName(name);

            // Pass the list of cities as a request attribute
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

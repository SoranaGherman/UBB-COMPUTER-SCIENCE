package com.example.demo1.controller;

import com.example.demo1.db.DbManager;
import com.example.demo1.domain.City;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "cities-controller", value="/cities-controller")
public class CitiesController extends HttpServlet {

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
            ArrayList<City> cities = dbManager.getAllCities();


            // Pass the list of cities as a request attribute
            req.setAttribute("cities", cities);
            req.setAttribute("currentCity", "Select a city to start your route!");

            // Forward the request to the JSP page for rendering
            rd = req.getRequestDispatcher("/success.jsp");

            rd.forward(req, resp);

        }
        else{
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

}

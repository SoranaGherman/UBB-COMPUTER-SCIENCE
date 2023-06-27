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

@WebServlet(name = "connection-controller", value="/connection-controller")
public class ConnectionController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check if the user is logged in by verifying the session
        HttpSession session = req.getSession(false); // Pass `false` to prevent the creation of a new session

        if (session != null && session.getAttribute("user") != null) {
            // User is logged in

            // Retrieve route_ids ArrayList from the session or create a new one if it doesn't exist
            ArrayList<Integer> route_ids = (ArrayList<Integer>) session.getAttribute("route_ids");
            if (route_ids == null) {
                route_ids = new ArrayList<>();
                session.setAttribute("route_ids", route_ids);
            }


            String selectedCityId = req.getParameter("selectedCityId");
            String action = req.getParameter("action");

            DbManager dbManager = new DbManager();
            RequestDispatcher rd = null;


            if (selectedCityId != null) {
                // "Show neighborhood" button was clicked
                int selectedId = Integer.parseInt(selectedCityId);
                route_ids.add(selectedId);

                ArrayList<City> cities = dbManager.getNeighbourhood(selectedId);
                String currentCity = dbManager.getCityName(selectedId);

                req.setAttribute("currentCity", currentCity);
                req.setAttribute("cities", cities);
                req.setAttribute("route_ids", route_ids);

                rd = req.getRequestDispatcher("/success.jsp");

            } else if ("showRouteIds".equals(action)) {
                // "Show route ids" button was clicked
                ArrayList<String> routeNames = new ArrayList<>();
                for (int id : route_ids) {
                    routeNames.add(dbManager.getCityName(id));
                }
                req.setAttribute("routeNames", routeNames);
                rd = req.getRequestDispatcher("/route.jsp");
            } else if ("undo".equals(action)) {

                if (route_ids.isEmpty()) {
                    ArrayList<City> cities = dbManager.getAllCities();
                    req.setAttribute("cities", cities);
                    req.setAttribute("currentCity", "Select a city to start your route!");
                    req.setAttribute("route_ids", route_ids);
                } else if (route_ids.size() == 1) {
                    ArrayList<City> cities = dbManager.getAllCities();
                    req.setAttribute("cities", cities);
                    req.setAttribute("currentCity", "Select a city to start your route!");
                    route_ids.remove(route_ids.size() - 1);
                    req.setAttribute("route_ids", route_ids);
                } else {
                    int previousCityId = route_ids.get(route_ids.size() - 2);
                    System.out.println(previousCityId);
                    ArrayList<City> cities = dbManager.getNeighbourhood(previousCityId);
                    String currentCity = dbManager.getCityName(previousCityId);
                    route_ids.remove(route_ids.size() - 1);

                    req.setAttribute("currentCity", currentCity);
                    req.setAttribute("cities", cities);
                    req.setAttribute("route_ids", route_ids);
                }

                // Forward back to the success page
                rd = req.getRequestDispatcher("/success.jsp");
            }

            if (rd != null)
                rd.forward(req, resp);
        }
        else{
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

}

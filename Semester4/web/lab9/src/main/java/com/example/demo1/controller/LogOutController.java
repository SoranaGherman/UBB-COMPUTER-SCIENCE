package com.example.demo1.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "logout-controller", value="/logout-controller")
public class LogOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getSession().invalidate();

        ArrayList<Integer> rid = new ArrayList<Integer>();
        req.setAttribute("route_ids", rid);

        RequestDispatcher rd = null;
        rd = req.getRequestDispatcher("/index.jsp");
        rd.forward(req, resp);
    }
}

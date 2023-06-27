package com.assignment9.controller;

import com.assignment9.db.DbManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@WebServlet(name = "controller", value="/controller")
public class Controller extends HttpServlet {
    HttpSession currentSession;
    int userId;

    // handle http GET request
    public void doGet(HttpServletRequest req, HttpServletResponse servletResponse) throws IOException {
        currentSession = req.getSession();
        userId = (Integer) currentSession.getAttribute("userID");

        servletResponse.setContentType("text/html");
        DbManager db = new DbManager();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse servletResponse) throws IOException {
        currentSession = req.getSession();
        userId = (int) currentSession.getAttribute("userID");

        servletResponse.setContentType("text/html");
        DbManager db = new DbManager();
    }
}
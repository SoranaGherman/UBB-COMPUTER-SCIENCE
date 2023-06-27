package com.example.sport.controller;

import com.example.sport.db.DbManager;
import com.example.sport.model.*;

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

        String name = request.getParameter("player_name");
        RequestDispatcher rd = null;

        DbManager dbmanager = new DbManager();
        boolean ok = dbmanager.playerExists(name);

        if (ok) {
            HttpSession session = request.getSession();
            session.setAttribute("player_name", name);

            ArrayList<Player> players = new ArrayList<Player>();
            request.setAttribute("players", players);

            ArrayList<Player> relatives = new ArrayList<>();
            request.setAttribute("relatives", relatives);

//            response.sendRedirect(request.getContextPath() + "/player-controller");
            rd = request.getRequestDispatcher("/player.jsp");
            rd.forward(request, response);
        } else {
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
}
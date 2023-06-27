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


@WebServlet(name = "teamMembers-controller", value="/teamMembers-controller")
public class teamMembersController extends  HttpServlet{

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check if the user is logged in by verifying the session
        HttpSession session = req.getSession(false); // Pass `false` to prevent the creation of a new session

        if (session != null && session.getAttribute("player_name") != null) {
            // User is logged in

            // Create an instance of DbManager to interact with the database
            DbManager dbManager = new DbManager();
            RequestDispatcher rd = null;

            String search_name = req.getParameter("search_name");

            ArrayList<Player> players;
            players = dbManager.getPlayersByName(search_name);

            req.setAttribute("players", players);

            int degree = Integer.parseInt(req.getParameter("degree"));
            ArrayList<Player> relatives = new ArrayList<>();

            String current_player = (String) session.getAttribute("player_name");

            if (degree == 1){
                relatives = dbManager.degree1(current_player);
            }
            else if(degree == 2){
                relatives = dbManager.degree2(current_player);
            }
            else if(degree == 3){
                ArrayList<Player> degree1 = dbManager.degree2(current_player);
                for(Player p: degree1){
                    relatives.addAll(dbManager.degree1(p.getName()));
                }
            }

            req.setAttribute("relatives", relatives);

            rd = req.getRequestDispatcher("/player.jsp");
            rd.forward(req, resp);
        }
    }
}

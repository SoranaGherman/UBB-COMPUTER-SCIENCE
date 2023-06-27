package com.example.sport.db;

import java.sql.*;
import java.util.ArrayList;

import com.example.sport.model.*;

public class DbManager {
    private Statement stmt;
    private Connection con;

    private PreparedStatement statement;

    public DbManager() {
        connect();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sport", "root", "");
            stmt = con.createStatement();
            System.out.println("success");
        } catch (Exception ex) {
            System.out.println("connection error:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean playerExists(String name) {
        ResultSet rs;
        boolean ok = false;

        try {
            String query = "SELECT * FROM Player WHERE name='" + name + "' ";
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                ok = true;
            }
            rs.close();
            return ok;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public ArrayList<Player> getPlayersByName(String name){
        ArrayList<Player> players = new ArrayList<Player>();
        ResultSet rs;

        try {
            String query = "select * from player where name like " + "'%" + name + "%'";

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                players.add(new Player(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    public int playerId(String name) {
        ResultSet rs;
        int id=-1;

        try {
            String query = "SELECT id FROM Player WHERE name='" + name + "' ";
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String playerName(int id) {
        ResultSet rs;
        String name="";

        try {
            String query = "SELECT name FROM Player WHERE id=" + id;
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                name = rs.getString("name");
            }
            rs.close();
            return name;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public ArrayList<Player> getNeighbours(String ids){

        ArrayList<Player> players = new ArrayList<Player>();
        ResultSet rs;

        try {
            String query2 = "select * from Player where id IN (" +  ids + ")";
            rs = stmt.executeQuery(query2);

            while (rs.next()) {
                players.add(new Player(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position")
                ));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;

    }


    public ArrayList<Player> degree1(String name){
        ArrayList<Player> players = new ArrayList<Player>();
        int id = playerId(name);
        String ids="";

        ResultSet rs;

        try {
            String query = "select idPlayer2 from TeamMembers where idPlayer1=" +  id;

            rs = stmt.executeQuery(query);

            while (rs.next()) {
              ids += rs.getInt("idPlayer2") + ",";
            }

            ids = (ids.substring(0, ids.length() - 1));

            players = getNeighbours(ids);
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public ArrayList<Player> degree2(String name){
        ArrayList<Player> players = new ArrayList<Player>();
        int id = playerId(name);
        ArrayList<Integer> ids = new ArrayList<>();

        ResultSet rs;

        try {
            String query = "select idPlayer2 from TeamMembers where idPlayer1=" +  id;
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                ids.add(rs.getInt("idPlayer2"));
            }

            for (Integer integer : ids) {
                String playerName = playerName(integer);
                ArrayList<Player> playerNeighbours;
                playerNeighbours = degree1(playerName);

                players.addAll(playerNeighbours);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

}
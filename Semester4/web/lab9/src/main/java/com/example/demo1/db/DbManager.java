package com.example.demo1.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo1.domain.City;
import com.example.demo1.domain.User;

public class DbManager {
    private Statement stmt;

    public DbManager() {
        connect();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/transportation", "root", "");
            stmt = con.createStatement();
            System.out.println("success");
        } catch(Exception ex) {
            System.out.println("connection error:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        ResultSet rs;
        User u = null;
        System.out.println(username+" "+password);
        try {
            rs = stmt.executeQuery("SELECT * FROM login WHERE username='"+username+"' AND password='"+password+"'");
            if (rs.next()) {
                u = new User(rs.getString("username"), rs.getString("password"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public ArrayList<City> getAllCities() {
        ArrayList<City> cities = new ArrayList<City>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from cities");
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("idcities"),
                        rs.getString("name")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public ArrayList<City> getNeighbourhood(int idC1) {
        ArrayList<City> neighbours = new ArrayList<City>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM cities WHERE idcities IN (SELECT idC2 FROM connection WHERE idC1="+idC1+")");
            while (rs.next()) {
                neighbours.add(new City(
                        rs.getInt("idcities"),
                        rs.getString("name")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return neighbours;
    }

    public String getCityName(int idcity) {
        String cityName="";
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM cities WHERE idcities="+idcity);
            while (rs.next()) {
                cityName = rs.getString("name");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityName;
    }

}

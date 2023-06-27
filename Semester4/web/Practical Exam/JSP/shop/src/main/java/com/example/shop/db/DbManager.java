package com.example.shop.db;

import com.example.shop.model.*;

import java.sql.*;
import java.util.ArrayList;

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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "");
            stmt = con.createStatement();
            System.out.println("success");
        } catch (Exception ex) {
            System.out.println("connection error:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }

        String insertSqlQuery = "INSERT INTO Product (name, description) VALUES(?, ?);";

        try {
            statement = con.prepareStatement(insertSqlQuery);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getAllProductsByName(String name) {
        ArrayList<Product> products = new ArrayList<Product>();
        ResultSet rs;
        try {
            String sql = "select * from Product where name like '"  +  name + "%'";
            System.out.println(sql);

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void insertAll(ArrayList<Order> orders){

        for(int i=0; i<orders.size(); ++i){
            String insertSqlQuery = "INSERT INTO `Order` (user, productId, quantity) VALUES(?, ?, ?);";
            Order order = orders.get(i);

            try {
                statement = con.prepareStatement(insertSqlQuery);
                statement.setString(1, order.getUser());
                statement.setInt(2, order.getProductId());
                statement.setInt(3, order.getQuantity());

                System.out.println(order.getUser());
                System.out.println();

                statement.executeUpdate();

                System.out.println("ok");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

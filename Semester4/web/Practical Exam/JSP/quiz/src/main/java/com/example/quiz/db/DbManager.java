package com.example.quiz.db;

import java.sql.*;

import com.example.quiz.model.Result;
import com.example.quiz.model.User;
import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
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
        try {
            rs = stmt.executeQuery("SELECT * FROM user WHERE username='"+username+"' AND password='"+password+"'");
            if (rs.next()) {
                u = new User(rs.getString("username"), rs.getString("password"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from question");
            while (rs.next()) {
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getString("correctAnswer"),
                        rs.getString("wrongAnswer")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public ArrayList<Question> getQuestionsOfQuiz(String questions_ids){
        ArrayList<Question> questions = new ArrayList<Question>();
        ResultSet rs;
        try {

            String ids = questions_ids.replace(' ', ',');

            rs = stmt.executeQuery("select * from question WHERE id IN (" + ids + ")");

            while (rs.next()) {
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getString("correctAnswer"),
                        rs.getString("wrongAnswer")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;

    }

    public void createQuiz(Quiz quiz) {
        if (quiz == null) {
            return;
        }

        String insertSqlQuery = "INSERT INTO quiz (id, title, listOfQuestions) VALUES(?, ?, ?);";

        try {
            statement = con.prepareStatement(insertSqlQuery);
            statement.setInt(1, quiz.getId());
            statement.setString(2, quiz.getTitle());
            statement.setString(3, quiz.getListOfQuestions());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getQuiz(int quziId){
        ResultSet rs;
        User u = null;
        try {
            rs = stmt.executeQuery("SELECT listOfQuestions FROM quiz WHERE id="+ quziId);
            if (rs.next()) {
                String res = rs.getString("listOfQuestions");
                return res;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void insertResult(Result result){
        if (result == null) {
            return;
        }

        String insertSqlQuery = "INSERT INTO result (id, username, result) VALUES(?, ?, ?);";

        try {
            statement = con.prepareStatement(insertSqlQuery);
            statement.setInt(1, result.getId());
            statement.setString(2, result.getUsername());
            statement.setInt(3, result.getResult());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int calculateScore(String result, String quizId) {
        int score = 0;
        ResultSet rs;
        String[] answers = result.split(" ");
        String new_res = "'" + String.join("','", answers) + "'";

        String ids = quizId.replace(' ', ',');

        try {
            String sqlQuery = "SELECT COUNT(*) FROM question WHERE id IN (" + ids + ")" + " AND correctAnswer IN (" + new_res + ")";
            System.out.println(sqlQuery);
            rs = stmt.executeQuery(sqlQuery);
            if (rs.next()) {
                score = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(score);
        return score;
    }


    public ArrayList<Result> getAllResults() {
        ArrayList<Result> results = new ArrayList<Result>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from result");
            while (rs.next()) {
                results.add(new Result(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("result")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


}


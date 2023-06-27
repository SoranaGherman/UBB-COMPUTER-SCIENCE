package com.example.quiz.model;

public class Result {
    private int id;
    private String username;
    private int result;

    public Result(int id, String username, int result) {
        this.id = id;
        this.username = username;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

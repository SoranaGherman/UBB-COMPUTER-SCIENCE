package com.example.quiz.model;

public class Quiz {
    private int id;
    private String title;
    private String listOfQuestions;

    public Quiz(int id, String title, String listOfQuestions) {
        this.id = id;
        this.title = title;
        this.listOfQuestions = listOfQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(String listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }
}

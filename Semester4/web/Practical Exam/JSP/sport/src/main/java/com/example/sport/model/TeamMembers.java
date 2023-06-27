package com.example.sport.model;

public class TeamMembers {
    private int id;
    private int idPlayer1;
    private int getIdPlayer2;
    private int idTeam;

    public TeamMembers(int id, int idPlayer1, int getIdPlayer2, int idTeam) {
        this.id = id;
        this.idPlayer1 = idPlayer1;
        this.getIdPlayer2 = getIdPlayer2;
        this.idTeam = idTeam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlayer1() {
        return idPlayer1;
    }

    public void setIdPlayer1(int idPlayer1) {
        this.idPlayer1 = idPlayer1;
    }

    public int getGetIdPlayer2() {
        return getIdPlayer2;
    }

    public void setGetIdPlayer2(int getIdPlayer2) {
        this.getIdPlayer2 = getIdPlayer2;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }
}

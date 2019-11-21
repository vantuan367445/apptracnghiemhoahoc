package com.example.tuanvatvo.demo2.model;

import java.io.Serializable;

public class ModelItemHistory implements Serializable {
    private int id;
    private String nameDe;
    private int   soCauDung;
    private String day;
    private String time;
    private String timeLamBai;
    private String made;
    private int idUsser;
    private  int socauhoi;

    public ModelItemHistory(int id, String nameDe, int soCauDung, String day, String time, String timeLamBai, String made, int idUsser, int socauhoi) {
        this.id = id;
        this.nameDe = nameDe;
        this.soCauDung = soCauDung;
        this.day = day;
        this.time = time;
        this.timeLamBai = timeLamBai;
        this.made = made;
        this.idUsser = idUsser;
        this.socauhoi = socauhoi;
    }

    public ModelItemHistory() {
    }

    public int getSocauhoi() {
        return socauhoi;
    }

    public void setSocauhoi(int socauhoi) {
        this.socauhoi = socauhoi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDe() {
        return nameDe;
    }

    public void setNameDe(String nameDe) {
        this.nameDe = nameDe;
    }

    public int getSoCauDung() {
        return soCauDung;
    }

    public void setSoCauDung(int soCauDung) {
        this.soCauDung = soCauDung;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeLamBai() {
        return timeLamBai;
    }

    public void setTimeLamBai(String timeLamBai) {
        this.timeLamBai = timeLamBai;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    public int getIdUsser() {
        return idUsser;
    }

    public void setIdUsser(int idUsser) {
        this.idUsser = idUsser;
    }
}

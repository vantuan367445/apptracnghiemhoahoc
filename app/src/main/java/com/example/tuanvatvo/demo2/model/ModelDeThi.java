package com.example.tuanvatvo.demo2.model;

import java.io.Serializable;

public class ModelDeThi implements Serializable {
    private int id;
    private String namedethi;
    private int lop;
    private String made;
    private int socauhoi;

    public ModelDeThi(int id, String namedethi, int lop, String made, int socauhoi) {
        this.id = id;
        this.namedethi = namedethi;
        this.lop = lop;
        this.made = made;
        this.socauhoi = socauhoi;
    }

    public ModelDeThi() {
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

    public String getNamedethi() {
        return namedethi;
    }

    public void setNamedethi(String namedethi) {
        this.namedethi = namedethi;
    }

    public int getLop() {
        return lop;
    }

    public void setLop(int lop) {
        this.lop = lop;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }
}

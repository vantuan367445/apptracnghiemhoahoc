package com.example.tuanvatvo.demo2.model;

import java.io.Serializable;

public class ModelChemistry implements Serializable {
    private  int idSubject;
    private int lop;
    private String name;
    private String linkImage;
    private boolean selected;
    private String describe;

    public ModelChemistry() {
    }

    public int getLop() {
        return lop;
    }

    public void setLop(int lop) {
        this.lop = lop;
    }

    public ModelChemistry(int lop,String name, String linkImage, boolean selected, String describe, int idSubject) {
        this.name = name;
        this.linkImage = linkImage;
        this.selected = selected;
        this.describe = describe;
        this.idSubject = idSubject;
        this.lop       =lop;


    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}

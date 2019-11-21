package com.example.tuanvatvo.demo2.model;

import java.io.Serializable;

public class ModelCauHoi_Json  implements Serializable{
    private int id;
    private String namecauhoi;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private int idlop;
    private String idmade;
    private int isImage;
    private String linkImage;
    private int dapan;
    private int yourChoice;

    public ModelCauHoi_Json(int id, String namecauhoi, String choiceA, String choiceB, String choiceC, String choiceD, int idlop, String idmade, int isImage, String linkImage, int dapan, int yourChoice) {
        this.id = id;
        this.namecauhoi = namecauhoi;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.idlop = idlop;
        this.idmade = idmade;
        this.isImage = isImage;
        this.linkImage = linkImage;
        this.dapan = dapan;
        this.yourChoice = yourChoice;
    }



    public ModelCauHoi_Json() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamecauhoi() {
        return namecauhoi;
    }

    public void setNamecauhoi(String namecauhoi) {
        this.namecauhoi = namecauhoi;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public int getIdlop() {
        return idlop;
    }

    public void setIdlop(int idlop) {
        this.idlop = idlop;
    }

    public String getIdmade() {
        return idmade;
    }

    public void setIdmade(String idmade) {
        this.idmade = idmade;
    }

    public int getisImage() {
        return isImage;
    }

    public void setImage(int image) {
        isImage = image;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public int getDapan() {
        return dapan;
    }

    public void setDapan(int dapan) {
        this.dapan = dapan;
    }

    public int getYourChoice() {
        return yourChoice;
    }

    public void setYourChoice(int yourChoice) {
        this.yourChoice = yourChoice;
    }
}

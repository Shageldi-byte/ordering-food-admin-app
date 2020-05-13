package com.titu.project;

import com.google.firebase.database.Exclude;

public class tagam_class {

    String id;
    String ady;
    String bahasy;
    String suraty;
    String barada;
    String resepti;
    String gornushi;
    //String documentId;

    public void tagam_class(){

    }
    public tagam_class(String id,String ady, String bahasy, String suraty, String barada, String resepti, String gornushi) {
        this.id=id;
        this.ady = ady;
        this.bahasy = bahasy;
        this.suraty = suraty;
        this.barada = barada;
        this.resepti = resepti;
        this.gornushi = gornushi;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdy() {
        return ady;
    }

    public void setAdy(String ady) {
        this.ady = ady;
    }

    public String getBahasy() {
        return bahasy;
    }

    public void setBahasy(String bahasy) {
        this.bahasy = bahasy;
    }

    public String getSuraty() {
        return suraty;
    }

    public void setSuraty(String suraty) {
        this.suraty = suraty;
    }

    public String getBarada() {
        return barada;
    }

    public void setBarada(String barada) {
        this.barada = barada;
    }

    public String getResepti() {
        return resepti;
    }

    public void setResepti(String resepti) {
        this.resepti = resepti;
    }

    public String getGornushi() {
        return gornushi;
    }

    public void setGornushi(String gornushi) {
        this.gornushi = gornushi;
    }
}

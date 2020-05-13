package com.titu.project;

import com.google.firebase.database.Exclude;

public class kategoriyalar {
    String documentId;
    String kategoriya;
    String image_url;

    public kategoriyalar(){

    }

    public kategoriyalar(String kategoriya, String image_url,String documentId) {
        this.kategoriya = kategoriya;
        this.image_url = image_url;
        this.documentId=documentId;
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getKategoriya() {
        return kategoriya;
    }

    public void setKategoriya(String kategoriya) {
        this.kategoriya = kategoriya;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

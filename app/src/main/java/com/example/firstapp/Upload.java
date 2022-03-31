package com.example.firstapp;

public class Upload {


    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url) {

        this.url= url;
    }



    public String getUrl() {
        return url;
    }
}


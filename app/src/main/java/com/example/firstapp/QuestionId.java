package com.example.firstapp;

import com.google.firebase.firestore.Exclude;

import io.reactivex.annotations.NonNull;

//Extender class
public class QuestionId {

    @Exclude
    public String BlogPostId;

    public <T extends QuestionId> T withId(@NonNull final String id){
        this.BlogPostId = id;
        return (T) this;
    }

}
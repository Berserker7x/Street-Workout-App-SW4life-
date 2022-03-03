package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SlideActivity extends AppCompatActivity {
     public  static ViewPager viewPager;
    SlideViewPage adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_slide);
        viewPager = findViewById(R.id.viewpager);
        adapter = new SlideViewPage(this);
        viewPager.setAdapter(adapter);


    }
}


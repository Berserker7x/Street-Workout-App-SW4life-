package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {

  TabLayout tabLayout;
  ViewPager viewPager;
  FloatingActionButton fb,google,twiter;
  float v=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tab_fragment);






    }
}
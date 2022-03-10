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
        setContentView(R.layout.activity_login);
        tabLayout=findViewById(R.id.tab_layout);
        fb=findViewById(R.id.fab_facebook);
        google=findViewById(R.id.fab_google);
        twiter=findViewById(R.id.tab_layout);
        tabLayout=findViewById(R.id.fab_twt);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adapter=new LoginAdapter(getSupportFragmentManager(),this.tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnAdapterChangeListener((ViewPager.OnAdapterChangeListener) new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        fb.setTranslationY(300);
        google.setTranslationY(300);
        twiter.setTranslationY(300);
        tabLayout.setTranslationY(300);
        fb.setAlpha(v);
        google.setAlpha(v);
        twiter.setAlpha(v);
        tabLayout.setAlpha(v);
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twiter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        twiter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();





    }
}
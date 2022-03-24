package com.example.firstapp;

import  androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;


import android.view.View;
import android.widget.Button;


public class ask_question extends AppCompatActivity {
Button askfragmentbtn,qstfragmentbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask_question);
    askfragmentbtn=findViewById(R.id.askfrag);
        qstfragmentbtn=findViewById(R.id.hubfrag);
    askfragmentbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            replaceFragment(new qst_hub());
        }
    });
        qstfragmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ask_fragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,fragment);
        fragmentTransaction.commit();

    }


}
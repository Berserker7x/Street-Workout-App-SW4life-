package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;


import java.util.ArrayList;
import java.util.List;



public class Sign extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuptab);
        final Spinner subject_spinner=findViewById(R.id.spintowin);
        final List<String> spnArray=new ArrayList();
        spnArray.add("Set & Reps");
        spnArray.add("Static");
        spnArray.add("Dynamic");
        spnArray.add("full Sw Athlete");
        ArrayAdapter<String> adapter=new ArrayAdapter (this, android.R.layout.simple_spinner_item,spnArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        subject_spinner.setAdapter(adapter);






    }
}
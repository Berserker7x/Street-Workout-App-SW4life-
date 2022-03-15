package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;



public class Sign extends AppCompatActivity {
    String[] textArray = { "Set & Reps", "Static", "Dynamic", "complete Sw athlete" };
    Integer[] imageArray = { R.drawable.serndreps, R.drawable.staticos,
            R.drawable.dynamic, R.drawable.sw };







    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuptab);

         TextView text = (TextView) findViewById(R.id.spinnerTextView);
        ImageView imageView =(ImageView)findViewById(R.id.spinnerImages);
         Spinner spinner = (Spinner) findViewById(R.id.mySpinner);
       SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_value_layout, textArray, imageArray);
        spinner.setAdapter(adapter);







    }
}

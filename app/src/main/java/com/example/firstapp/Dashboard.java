package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivity;

public class Dashboard extends AppCompatActivity {
    ListView l1;
    TextView name;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        final TextView name = (TextView) findViewById(R.id.usernameid);
        final LinearLayout logoutbtn = findViewById(R.id.logout);
        Intent intent = getIntent();
        final String userSession = intent.getStringExtra("name");
        name.setText("Welcome " + userSession);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session;//global variable
                session = new Session(Dashboard.this);
                Intent i = new Intent(Dashboard.this, Login.class);
                //in oncreate
//and now        we set sharedpreference then use this like
                session.logout();
                startActivity(i);




            }
        });


    }}



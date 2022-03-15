package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Login extends AppCompatActivity {
    TextView username;
    TextView password;
    Button login;
    Button sign;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tab_fragment);

        username =findViewById(R.id.username);
        password=findViewById(R.id.pass);
        login=findViewById(R.id.loginbtn);

        sign=findViewById(R.id.Signupbtn);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Login.this,Sign.class);

                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent2);

            }
        });






    }
}
package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://sw4lifeapp-default-rtdb.firebaseio.com/");
   EditText username;
   EditText password;
    Button login;
    Button sign;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tab_fragment);

        username =findViewById(R.id.email);
        password=findViewById(R.id.pass);
        login=findViewById(R.id.loginbtn);
        sign=findViewById(R.id.Signupbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userlogin=username.getText().toString();
                final String passlogin=password.getText().toString();
                if(userlogin.isEmpty() || passlogin.isEmpty()){
                    Log.d("myTag", "logiiiiiiiiiiiiiiiiing ");
                    Toast.makeText(Login.this,"Please enter your username or password",Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            //check if user exist
                            if(snapshot.hasChild(userlogin)){
                                //yes exist
                                //password form firebase and test
                                final String getPassword=snapshot.child(userlogin).child("password").getValue(String.class);
                                if (getPassword.equals(passlogin)){
                                    Toast.makeText(Login.this,"Welcome",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this,"wrong password ",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this,"wrong password",Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled( DatabaseError error) {

                        }
                    });

                }
            }
        });








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
package com.example.firstapp;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;





public class Sign extends AppCompatActivity {


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

    private   EditText email;
    private  EditText password;
    private    EditText username;
    private Button signup;
    private FirebaseAuth mAuth;





    String[] textArray = { "Set & Reps", "Static", "Dynamic", "complete Sw athlete" };
    Integer[] imageArray = { R.drawable.serndreps, R.drawable.staticos, R.drawable.dynamic, R.drawable.sw };







    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuptab);

        TextView text = (TextView) findViewById(R.id.spinnerTextView);
        ImageView imageView =(ImageView)findViewById(R.id.spinnerImages);
        Spinner spinner = (Spinner) findViewById(R.id.mySpinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_value_layout, textArray, imageArray);
        spinner.setAdapter(adapter);
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        username=findViewById(R.id.username);
        signup=findViewById(R.id.button2);

        String  categorie = spinner.getSelectedItem().toString();
        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override public void onClick(View v) {
                                          final String emailvalue=email.getText().toString();
                                          final    String passwordvalue=password.getText().toString();
                                          final String usermamevalue=username.getText().toString();
                                          String  categorie = spinner.getSelectedItem().toString();
                                          Integer score=0;

                                          if(emailvalue.isEmpty() || passwordvalue.isEmpty() || usermamevalue.isEmpty() ){
                                              Toast.makeText(Sign.this,"Please fill all fields",Toast.LENGTH_SHORT).show();

                                          }
                                          else{

                                              databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange( DataSnapshot snapshot) {
                                                      //check if username used
                                                      if(snapshot.hasChild(usermamevalue)){
                                                          Toast.makeText(Sign.this,"Username is already Registred chose an other one ",Toast.LENGTH_SHORT).show();

                                                      }
                                                      else {
                                                          //sending data to firebase
                                                          databaseReference.child("users").child(usermamevalue).child("username").setValue(usermamevalue);
                                                          databaseReference.child("users").child(usermamevalue).child("email").setValue(emailvalue);
                                                          databaseReference.child("users").child(usermamevalue).child("password").setValue(passwordvalue);
                                                          databaseReference.child("users").child(usermamevalue).child("categorie").setValue(categorie);
                                                          databaseReference.child("users").child(usermamevalue).child("score").setValue(score);
                                                          Toast.makeText(Sign.this,"User registred successfully",Toast.LENGTH_SHORT).show();

                                                          Intent intent3=new Intent(Sign.this,Login.class);

                                                          intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                                          startActivity(intent3);


                                                      }
                                                  }

                                                  @Override
                                                  public void onCancelled(  DatabaseError error) {

                                                  }
                                              });



                                          }





                                      }
                                  }

        );









    }
}
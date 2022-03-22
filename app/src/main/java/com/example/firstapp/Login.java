package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
    private FirebaseAuth mAuth;
    FloatingActionButton Google;
    private  static  final int RC_SIGN_IN=100;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tab_fragment);

        username =findViewById(R.id.email);
        password=findViewById(R.id.pass);
        login=findViewById(R.id.loginbtn);
        sign=findViewById(R.id.Signupbtn);
        Google=findViewById(R.id.fab_google);



        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        //configuration google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(Login.this, gso);
                Intent intent3=mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent3,RC_SIGN_IN);





            }



        });





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
                                    startActivity(new Intent(Login.this,Dashboard.class));
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
                                        Intent intent2 = new Intent(Login.this, Sign.class);

                                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent2);

                                    }


                                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==RC_SIGN_IN){
         Task<GoogleSignInAccount> accountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
         try{
             //google Sign auth with firebase
             GoogleSignInAccount account=accountTask.getResult(ApiException.class);
             firebaseAuthWithGoogleAccount(account);

         }catch (Exception e){

         }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
    AuthCredential credential=GoogleAuthProvider.getCredential(account.getIdToken(),null);
    mAuth.signInWithCredential(credential)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //login success
                    //get loged in user
                    FirebaseUser firebaseUser=mAuth.getCurrentUser();
                    String uid=mAuth.getUid();
                    String email=firebaseUser.getEmail();
                    //check if new or exist
                    if(authResult.getAdditionalUserInfo().isNewUser()){
                        //user is new account
                        Toast.makeText(Login.this,"user creeated",Toast.LENGTH_SHORT).show();


                    }else {
                        //exist
                        Toast.makeText(Login.this," Existing user ",Toast.LENGTH_SHORT).show();
                    }
                    //start profile activity
                     startActivity(new Intent(Login.this,Dashboard.class));
                     finish();



                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    //login failed


                }
            });

    }
}

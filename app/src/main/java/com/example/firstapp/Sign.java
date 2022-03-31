package com.example.firstapp;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


public class Sign extends AppCompatActivity {


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    private   EditText email;
    private  EditText password;
    private    EditText username;
    private Button signup;
    private FirebaseAuth mAuth;
    ImageView upload ;






    String[] textArray = { "Set & Reps", "Static", "Dynamic", "complete Sw athlete" };
    Integer[] imageArray = { R.drawable.serndreps, R.drawable.staticos, R.drawable.dynamic, R.drawable.sw };

    String picturePath = "";





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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        upload=findViewById(R.id.addcamera);

        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        username=findViewById(R.id.username);
        signup=findViewById(R.id.button2);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }

            private void SelectImage() {
                // Defining Implicit Intent to mobile gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE_REQUEST);
            }
        });



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
                                                          uploadImage(usermamevalue);



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

                                      private void uploadImage(String username) {
                                          if(filePath != null)
                                          {
                                              final ProgressDialog progressDialog = new ProgressDialog(Sign.this);
                                              progressDialog.setTitle("Uploading...");
                                              //progressDialog.show();
                                              StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());;


                                              ref.putFile(filePath)
                                                      .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                          @Override
                                                          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                              progressDialog.dismiss();
                                                              Toast.makeText(Sign.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                                               ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                   @Override
                                                                   public void onSuccess(Uri uri) {
                                                                       HashMap<String,String> hashMap=new HashMap<>();
                                                                       hashMap.put("imageurl",String.valueOf(uri));



                                                                       databaseReference.child("users").child(username).child("image").setValue(hashMap);
                                                                   }
                                                               });
                                                          }
                                                      })
                                                      .addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception e) {
                                                              progressDialog.dismiss();
                                                              Toast.makeText(Sign.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      })
                                                      .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                          @Override
                                                          public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                              double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                                                      .getTotalByteCount());
                                                              progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                                          }
                                                      });
                                          }
                                      }
















    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
               // imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}
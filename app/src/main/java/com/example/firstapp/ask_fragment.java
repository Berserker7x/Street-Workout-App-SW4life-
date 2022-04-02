package com.example.firstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import id.zelory.compressor.Compressor;


public class ask_fragment extends Fragment {
    View view;
    private final int PICK_IMAGE_REQUEST = 22;
    private static final int MAX_LENGTH = 100;
    private Button submitBtn;
    private EditText newPostText;
    private String user_id;
    private FirebaseAuth mauth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private ImageView newPostImg;
    private Uri main_uri = null;
    private ProgressBar progressBar;
    private String postText;
    private Bitmap compressedImageBitmap;


    public ask_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_fragment, container, false);
        //Firebase Variables
        mauth = FirebaseAuth.getInstance();
        user_id = mauth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        newPostImg = view.findViewById(R.id.newPostImage);
        newPostText = view.findViewById(R.id.newPostDesc);
        submitBtn = view.findViewById(R.id.newPostBtn);
        progressBar = view.findViewById(R.id.newPostProgress);


        storageReference = FirebaseStorage.getInstance().getReference();
        newPostImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }

            private void selectImage() {
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


        // add.setOnClickListener(v -> Log.d("hh","clicked"));
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postText = newPostText.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if(main_uri != null && !TextUtils.isEmpty(postText)){
//                    final String timestamp = FieldValue.serverTimestamp().toString();

                    progressBar.setVisibility(View.VISIBLE);
                    final String randomName = UUID.randomUUID().toString();


                    //Create storage path reference
                    StorageReference filePath = storageReference.child("post_images").child(randomName + ".jpg");
                    //url of image to be put in the above path
                    //This uploads image to Post_images folder in Storage..To determine heirchachy u need to use map to put in collections
                    filePath.putFile(main_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){

                                //downloadURI is url of image uploaded that link of image only
                                final String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();

                                //upload thumbnail compressed
                                File imageFile = new File(main_uri.getPath());

                                try {
                                    compressedImageBitmap = new Compressor(getActivity().getApplication())
                                            .setMaxHeight(100)
                                            .setMaxWidth(100)
                                            .setQuality(1)
                                            .compressToBitmap(imageFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                //Uploadinf bitmap to firebase
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                //compressedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] thumbBitmap = baos.toByteArray();

                                UploadTask thumbImage = storageReference.child("/post_images/thumbs").child(randomName+".jpg").putBytes(thumbBitmap);
                                thumbImage.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        if(task.isSuccessful()){

                                            String thumbUri = task.getResult().getStorage().getDownloadUrl().toString();

                                            Map<String,Object> postMap = new HashMap<>();
                                            postMap.put("image_url",downloadUrl);
                                            postMap.put("desc",postText);
                                            postMap.put("user_id",user_id);
                                            postMap.put("image_thumb",thumbUri);
                                            postMap.put("timeStamp", FieldValue.serverTimestamp());

                                            //Finally add everything to collection
                                            //we dont add .document as it must be created and named randomly by firebase automatically
                                            firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        Log.d("post ","added");
                                                      Toast.makeText(getActivity(), "Successfully Posted", Toast.LENGTH_LONG).show();
                                                        Intent main = new Intent(getActivity().getApplication(),Dashboard.class);
                                                        startActivity(main);

                                                    } else {
                                                        Toast.makeText(getActivity(), "Error" + task.getException().toString(), Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                    }
                                                }
                                            });


                                        }
                                        else {
                                          //  Toast.makeText(NewPost.this, "Error" + task.getException().toString(), Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity().getApplication(), "No Image or Description", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

        });

        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == -1
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            main_uri = data.getData();

        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }




}
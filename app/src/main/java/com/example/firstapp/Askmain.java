package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class Askmain extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askmain);
       final Button firstFragmentBtn=findViewById(R.id.fragbtn1);
      final  Button  secondFragmentBtn=findViewById(R.id.fragbtn2);


        firstFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                                                public void onClick(View v) {
                                                    replaceFragment(new qst_hub());

                                                }
                                            });

                secondFragmentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        replaceFragment(new ask_fragment());

                    }
                });




    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}
package com.example.firstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class qst_hub extends Fragment {
    View view2;



    public qst_hub() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view2= inflater.inflate(R.layout.fragment_qst_hub, container, false);

    return view2;
    }
}
package com.example.firstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnBoardingFragment3 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,   ViewGroup container,  Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        return  root;
    }
}

package com.example.firstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {
    TextView username;
    TextView password;
    EditText forgot;
    Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        username =root.findViewById(R.id.username);
        password=root.findViewById(R.id.pass);
        login=root.findViewById(R.id.loginbtn);
        forgot=root.findViewById(R.id.forget);
        username.setTranslationX(800);
        password.setTranslationX(800);
        forgot.setTranslationX(800);
        login.setTranslationX(800);
        username.setAlpha(0);
        password.setAlpha(0);
        forgot.setAlpha(0);
        login.setAlpha(0);
        username.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgot.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();



        return  root;
    }

}

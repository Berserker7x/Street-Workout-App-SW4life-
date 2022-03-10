package com.example.firstapp;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {


    @Override
    public int getCount() {
        return totalTabs;
    }

    private Context context;
    int totalTabs;
    public LoginAdapter(FragmentManager fm, int totalTabs){
       super(fm);
       this.context=context;
       this.totalTabs=totalTabs;


    }


    public Fragment getItem(int posistion){
        switch(posistion){
            case 0:
                LoginTabFragment loginTabFragment=new LoginTabFragment();
                return  loginTabFragment;
            case 1:
                SignUpFragment signUpFragment=new SignUpFragment();
                return  signUpFragment;
            default:
                return  null;


        }
    }

}

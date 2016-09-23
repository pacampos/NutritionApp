package com.example.nutrition.nutritionapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class SignUpActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //get manager
        FragmentManager fm= getSupportFragmentManager();
        //get the ID/ location of where we want to load fragment
        Fragment f=fm.findFragmentById(R.id.fragment_container);

        if(f==null){ // activity and fragment are created for the first time
            f = new signUpFragment(); // instantiate Profile Fragment
            // create transaction
            FragmentTransaction ft= fm.beginTransaction();
            ft.add(R.id.fragment_container,f);
            ft.commit();
        }

    }

}

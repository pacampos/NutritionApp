package com.example.nutrition.nutritionapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//    Test Comment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get manager
        FragmentManager fm= getSupportFragmentManager();
        //get the ID/ location of where we want to load fragment
        Fragment f=fm.findFragmentById(R.id.welcome_fragment_container);

        if(f==null){ // activity and fragment are created for the first time
            f = new welcomeFragment(); // instantiate Profile Fragment
            // create transaction
            FragmentTransaction ft= fm.beginTransaction();
            ft.add(R.id.welcome_fragment_container,f);
            ft.commit();
        }

    }


}

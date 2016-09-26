package com.example.nutrition.nutritionapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivityHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //get manager
        FragmentManager fm= getSupportFragmentManager();
        //get the ID/ location of where we want to load fragment
        Fragment f=fm.findFragmentById(R.id.home_fragment_container);

        if(f==null){ // activity and fragment are created for the first time
            f = new HomeFragment2(); // instantiate Profile Fragment
            // create transaction
            FragmentTransaction ft= fm.beginTransaction();
            ft.add(R.id.home_fragment_container,f);
            ft.commit();
        }

    }

}

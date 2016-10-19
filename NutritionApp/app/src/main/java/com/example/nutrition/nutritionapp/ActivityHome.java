package com.example.nutrition.nutritionapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

public class ActivityHome extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //get manager
        FragmentManager fm = getSupportFragmentManager();
        //get the ID/ location of where we want to load fragment
        Fragment f = fm.findFragmentById(R.id.home_fragment_container);

        if (f == null) { // activity and fragment are created for the first time
            f = new HomeFragment(); // instantiate Profile Fragment
            // create transaction
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.home_fragment_container, f);
            ft.commit();
        }

    }
}

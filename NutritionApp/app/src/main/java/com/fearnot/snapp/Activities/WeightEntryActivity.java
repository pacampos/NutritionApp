package com.fearnot.snapp.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.fearnot.snapp.Fragments.WeightEntryFragment;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.R;

public class WeightEntryActivity extends AppCompatActivity implements ReplaceFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_entry);

        //get manager
        FragmentManager fm = getSupportFragmentManager();
        //get the ID/ location of where we want to load fragment
        Fragment f = fm.findFragmentById(R.id.weightentry_fragment_container);

        if (f == null) { // activity and fragment are created for the first time
            f = new WeightEntryFragment(); // instantiate Profile Fragment
            // create transaction
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.weightentry_fragment_container, f);
            ft.commit();
        }
    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.weightentry_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

package com.fearnot.snapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fearnot.snapp.Fragments.welcomeFragment;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements ReplaceFragmentInterface {
    private static final String TAG = "MainActivity.";
    public Context fragmentContext;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                   // welcomeScreen();
                   NutritionSingleton.getInstance().SetUser(user, MainActivity.this);


                    // Toast
                    Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                    // TODO: this may be a way to enable persistence sign-in while the login token is valid
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    welcomeScreen();

                    Toast.makeText(getApplicationContext(), "You are currently not logged in.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    private void welcomeScreen() {
        //get manager
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new welcomeFragment(); // instantiate Profile Fragment
        // create transaction
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.welcome_fragment_container, f);
        ft.commit();
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.welcome_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

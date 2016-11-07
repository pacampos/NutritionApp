package com.fearnot.snapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fearnot.snapp.Activities.ActivityHome;
import com.fearnot.snapp.Fragments.HomeFragment;
import com.fearnot.snapp.Fragments.LoginFragment;
import com.fearnot.snapp.Fragments.welcomeFragment;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import static java.security.AccessController.getContext;

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

                    welcomeScreen();
                    //NutritionSingleton.getInstance().SetUser(user, );


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

    public void signIn(String email, String password, Context context) {
        fragmentContext = context;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Could not sign in, make sure all your info is correct and try again.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
                            mAuth = FirebaseAuth.getInstance();
                            NutritionSingleton.getInstance().SetUser(mAuth.getCurrentUser(), fragmentContext);
                        }
                    }
                });

    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.welcome_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

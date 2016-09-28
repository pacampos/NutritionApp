package com.example.nutrition.nutritionapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends FragmentActivity {
    /* firebase auth instance variables */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static String TAG="SignupActivity.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth= FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

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

    public void finishSignup(String email, String password, final Bundle bundle){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Could not sign up",
                                    Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Toast.makeText(SignUpActivity.this,"Signed In", Toast.LENGTH_SHORT).show();
                            NutritionSingleton.getInstance().CreateNewAccount(mAuth.getCurrentUser().getUid());

                            NutritionSingleton.getInstance().CreateNewProfile(bundle.getDouble(signUpFragment.IMAGE_POS),
                                    bundle.getString(signUpFragment.NAME),
                                    bundle.getDouble(signUpFragment.AGE),
                                    bundle.getDouble(goalInformationFragment.HEIGHT),
                                    bundle.getBoolean(signUpFragment.GENDER),
                                    bundle.getDouble(goalInformationFragment.WEIGHT),
                                    bundle.getDouble(goalInformationFragment.GOAL),
                                    bundle.getDouble(signUpFragment.BIRTH_DATE),
                                    bundle.getDouble(signUpFragment.BIRTH_MONTH),
                                    bundle.getDouble(signUpFragment.BIRTH_YEAR),
                                    bundle.getDouble(MeasurementFragment.WAIST),
                                    bundle.getDouble(MeasurementFragment.THIGH),
                                    bundle.getDouble(MeasurementFragment.ARM),
                                    bundle.getDouble(goalInformationFragment.ACTIVITY));

                            Intent i = new Intent(SignUpActivity.this, ActivityHome.class);
                            startActivity(i);
                        }

                    }
                });
    }

}

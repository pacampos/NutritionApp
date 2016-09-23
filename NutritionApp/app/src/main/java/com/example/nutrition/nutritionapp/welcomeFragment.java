package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class welcomeFragment extends Fragment {
    public welcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        // Get references to elements on page
        Button signupButton = (Button) v.findViewById(R.id.signupButton);
        Button loginButton = (Button) v.findViewById(R.id.loginButton);
        signupButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        // Go to new activity when clicking sign up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SignUpActivity.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                replaceFragment(fragment);
            }
        });
        return v;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.welcome_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

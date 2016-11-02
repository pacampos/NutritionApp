package com.fearnot.snapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fearnot.snapp.Activities.SignUpActivity;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.R;


public class welcomeFragment extends Fragment {
    private ReplaceFragmentInterface replaceFragmentInterface;

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
        Button testButton = (Button) v.findViewById(R.id.testButton);
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
                replaceFragmentInterface.replaceFragment(fragment);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            replaceFragmentInterface = (ReplaceFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}

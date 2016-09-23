package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //get references
        Button loginButton = (Button) v.findViewById(R.id.loginButton1);
        loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        EditText usernameInput = (EditText) v.findViewById(R.id.usernameInput1);
        EditText passwordInput = (EditText) v.findViewById(R.id.passwordInput1);


        return v;
    }
}

package com.fearnot.snapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fearnot.snapp.MainActivity;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;

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

        final EditText usernameInput = (EditText) v.findViewById(R.id.usernameInput1);
        final EditText passwordInput = (EditText) v.findViewById(R.id.passwordInput1);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NutritionSingleton.getInstance().setCurrProfile(null);
                NutritionSingleton.getInstance().setCurrDay(null);
                // Get rid of whitespace in username field
                String username = usernameInput.getText().toString().replaceAll("\\s+","");
                ((MainActivity) getActivity()).signIn(username, passwordInput.getText().toString(), getContext());
            }
        });

        return v;
    }
}

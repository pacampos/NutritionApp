package com.example.nutrition.nutritionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//    Test Comment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to elements on page
        Button signupButton = (Button) this.findViewById(R.id.signupButton);
        Button loginButton = (Button) this.findViewById(R.id.loginButton);
        signupButton.setBackgroundColor(Color.parseColor("#5167bd"));
        loginButton.setBackgroundColor(Color.parseColor("#5167bd"));
    }


}

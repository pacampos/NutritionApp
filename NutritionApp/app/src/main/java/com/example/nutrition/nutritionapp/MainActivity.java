package com.example.nutrition.nutritionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        signupButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        // Go to new activity when clicking sign up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
    }


}

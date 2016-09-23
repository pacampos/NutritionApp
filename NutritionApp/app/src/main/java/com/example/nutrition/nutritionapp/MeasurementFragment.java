package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MeasurementFragment extends Fragment {
    private String arm;
    private String waist;
    private String thigh;

    public MeasurementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View v = inflater.inflate(R.layout.fragment_measurement, container, false);

        // Get References
        Button goToButton = (Button) v.findViewById(R.id.goToButton);
        goToButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        EditText armInput = (EditText) v.findViewById(R.id.armInput);
        arm = armInput.getText().toString();

        EditText waistInput = (EditText) v.findViewById(R.id.waistInput);
        waist = waistInput.getText().toString();

        EditText thighInput = (EditText) v.findViewById(R.id.thighInput);
        thigh = thighInput.getText().toString();

        goToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}

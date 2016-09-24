package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MeasurementFragment extends Fragment {
    private String arm;
    private String waist;
    private String thigh;

    private Bundle bundle;

    public static String ARM ="com.example.nutritionapp.arm";
    public static String WAIST ="com.example.nutritionapp.waist";
    public static String THIGH ="com.example.nutritionapp.thigh";

    public MeasurementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = this.getArguments();

        // Inflate the layout for this fragment
       final View v = inflater.inflate(R.layout.fragment_measurement, container, false);

        // Get References
        Button goToButton = (Button) v.findViewById(R.id.goToButton);
        goToButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        final EditText armInput = (EditText) v.findViewById(R.id.armInput);

        final EditText waistInput = (EditText) v.findViewById(R.id.waistInput);

        final EditText thighInput = (EditText) v.findViewById(R.id.thighInput);




        goToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arm = armInput.getText().toString();
                waist = waistInput.getText().toString();
                thigh = thighInput.getText().toString();

                bundle.putInt(ARM, Integer.parseInt(arm));
                bundle.putInt(WAIST, Integer.parseInt(waist));
                bundle.putInt(THIGH, Integer.parseInt(thigh));

                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}

package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nutrition.nutritionapp.R;




public class goalInformationFragment extends Fragment {
    private String weight;
    private String height;
    private String goalWeight;
    private int activityLevelFactor;
    public goalInformationFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal_information, container, false);

        // get references
        EditText weightInput = (EditText) v.findViewById(R.id.weightInput);
        weight = weightInput.getText().toString();

        EditText heightInput = (EditText) v.findViewById(R.id.heightInput);
        height = heightInput.getText().toString();

        EditText goalWeightInput = (EditText) v.findViewById(R.id.goalWeightInput);
        goalWeight = goalWeightInput.getText().toString();

        final Spinner activityLevelSpinner = (Spinner) v.findViewById(R.id.spinner);
        activityLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityLevelFactor = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button continueButton = (Button) v.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MeasurementFragment();
                replaceFragment(fragment);
            }
        });
        return v;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

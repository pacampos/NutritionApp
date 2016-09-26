package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nutrition.nutritionapp.R;
import java.util.Calendar;

import static android.R.attr.defaultValue;

public class goalInformationFragment extends Fragment {
    private String weight;
    private String height;
    private String goalWeight;
    private int activityLevelFactor;

    private Bundle bundle;
    public static String WEIGHT ="com.example.nutritionapp.weight";
    public static String HEIGHT ="com.example.nutritionapp.height";
    public static String GOAL ="com.example.nutritionapp.goal";
    public static String ACTIVITY ="com.example.nutritionapp.activity";

    public goalInformationFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        bundle = this.getArguments();

      //  Toast.makeText(getActivity(), String.valueOf(), Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal_information, container, false);

        // get references
        final EditText weightInput = (EditText) v.findViewById(R.id.weightInput);

        final EditText heightInput = (EditText) v.findViewById(R.id.heightInput);

        final EditText goalWeightInput = (EditText) v.findViewById(R.id.goalWeightInput);


        Spinner activityLevelSpinner = (Spinner) v.findViewById(R.id.spinner);
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
                weight = weightInput.getText().toString();
                height = heightInput.getText().toString();
                goalWeight = goalWeightInput.getText().toString();
                Fragment fragment = new MeasurementFragment();
                bundle.putDouble(WEIGHT,Double.parseDouble(weight));
                bundle.putDouble(HEIGHT,Double.parseDouble(height));
                bundle.putDouble(GOAL, Double.parseDouble(goalWeight));
                bundle.putDouble(ACTIVITY, activityLevelFactor);
                fragment.setArguments(bundle);
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

package com.example.nutrition.nutritionapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.nutrition.nutritionapp.Model.DayModel;
import com.example.nutrition.nutritionapp.Model.ExerciseModel;
import com.example.nutrition.nutritionapp.Model.ProfileModel;


public class ExerciseFragment extends Fragment {
    private double exerciseType = 0.0;
    private double duration = 10.0;
    private double durationArray [] = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0};


    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise, container, false);

        // Get references
        final Spinner typeSpinner = (Spinner) v.findViewById(R.id.typeSpinner);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer intPos = new Integer(position);
                exerciseType = intPos.doubleValue();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner durationSpinner = (Spinner) v.findViewById(R.id.durationSpinner);
        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration=durationArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button recordButton = (Button) v.findViewById(R.id.recordButton);
        Button journalButton = (Button) v.findViewById(R.id.journalButton);

        ImageView image = (ImageView) v.findViewById(R.id.footprint);
        image.setRotation(320);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileModel profile= NutritionSingleton.getInstance().getCurrProfile();
                DayModel day = NutritionSingleton.getInstance().getCurrDay();
                if(profile.getIsImperial()){
                    NutritionSingleton.getInstance().addExercise(new ExerciseModel(profile.getIsImperial(),duration,exerciseType,profile.getCurrWeightPounds()));
                }

                else{
                    NutritionSingleton.getInstance().addExercise(new ExerciseModel(profile.getIsImperial(),duration,exerciseType,profile.getCurrWeightKilos()));
                }
            }
        });


        return v;
    }

}

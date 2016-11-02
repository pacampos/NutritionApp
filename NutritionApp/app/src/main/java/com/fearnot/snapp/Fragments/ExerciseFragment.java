package com.fearnot.snapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.Model.ExerciseModel;
import com.fearnot.snapp.Model.ProfileModel;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;


public class ExerciseFragment extends Fragment {
    private double exerciseType = 0.0;
    private double calories = 0.0;
    private double duration = 10.0;
    private double durationArray[] = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0};
    private boolean manualEntry = false;
    private ReplaceFragmentInterface replaceFragmentInterface;
    private Spinner typeSpinner;
    private EditText manual_calorie_burned;
    private Spinner durationSpinner;
    private LinearLayout manualEntryLayout;
    private Button journalButton;


    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.exerciseEntryToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Exercise Entry");

        // Get references
        typeSpinner = (Spinner) v.findViewById(R.id.typeSpinner);
        journalButton = (Button) v.findViewById(R.id.journalButton);
        manual_calorie_burned = (EditText) v.findViewById(R.id.manual_calorie_burned);
        durationSpinner = (Spinner) v.findViewById(R.id.durationSpinner);
        manualEntryLayout = (LinearLayout) v.findViewById(R.id.exerciseManualEntryEditText);

        manual_calorie_burned.setEnabled(false);

        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentInterface.replaceFragment(new JournalFragment());
            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer intPos = new Integer(position);
                exerciseType = intPos.doubleValue();
                if (intPos != 5) {
                    manualEntry = false;
                    manual_calorie_burned.setEnabled(false);
                    durationSpinner.setEnabled(true);
                    manualEntryLayout.setVisibility(View.GONE);
                } else {
                    manualEntry = true;
                    manual_calorie_burned.setEnabled(true);
                    durationSpinner.setEnabled(false);
                    manualEntryLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration = durationArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button recordButton = (Button) v.findViewById(R.id.recordButton);

        ImageView image = (ImageView) v.findViewById(R.id.footprint);
        image.setRotation(320);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NutritionSingleton singleton = NutritionSingleton.getInstance();
                ProfileModel profile = singleton.getCurrProfile();

                if (!manualEntry) {
                    if (profile.getIsImperial()) {
                        singleton.addExercise(new ExerciseModel(profile.getIsImperial(), duration, exerciseType, profile.getCurrWeightPounds()));
                        Toast.makeText(getActivity(), "Added Exercise Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        singleton.addExercise(new ExerciseModel(profile.getIsImperial(), duration, exerciseType, profile.getCurrWeightKilos()));
                        Toast.makeText(getActivity(), "Added Exercise Successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    singleton.addExercise(new ExerciseModel(exerciseType, Double.valueOf(manual_calorie_burned.getText().toString())));
                    Toast.makeText(getActivity(), "Added Exercise Successfully", Toast.LENGTH_SHORT).show();
                    manualEntry = false;
                }
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
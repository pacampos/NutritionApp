package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.defaultValue;

public class goalInformationFragment extends Fragment {
    public static String WEIGHT = "com.example.nutritionapp.weight";
    public static String HEIGHT = "com.example.nutritionapp.height";
    public static String GOAL = "com.example.nutritionapp.goal";
    public static String ACTIVITY = "com.example.nutritionapp.activity";
    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";
    private String weight;
    private String height;
    private String goalWeight;
    private int activityLevelFactor;
    private Bundle bundle;

    signUpFragment.ReplaceFragmentInterface replaceFragmentInterface;

    public goalInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        bundle = this.getArguments();

        boolean isImperial= bundle.getBoolean(signUpFragment.METRIC);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal_information, container, false);

        // get references
        final EditText weightInput = (EditText) v.findViewById(R.id.weightInput);
        final TextView weightTextView = (TextView) v.findViewById(R.id.currentWeightLabel);
        final EditText heightInput = (EditText) v.findViewById(R.id.heightInput);
        final TextView heightTextView = (TextView) v.findViewById(R.id.currentHeightLabel);
        final EditText goalWeightInput = (EditText) v.findViewById(R.id.goalWeightInput);
        final TextView goalWeightTextView = (TextView) v.findViewById(R.id.goalWeightLabel);

        if(isImperial == true){
            weightTextView.setText(R.string.weight_text_imperial);
            heightTextView.setText(R.string.height_text_imperial);
            goalWeightTextView.setText(R.string.goalWeight_text_imperial);
        }

        ImageView icon = (ImageView) v.findViewById(R.id.iconImage);

        double imagePos = bundle.getDouble(IMAGE_POS, defaultValue);

        // get image from array
        icon.setImageResource(CheckableImageView.mOriginalIds[(int) imagePos]);

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

                if (weight.length() == 0 || height.length() == 0 || goalWeight.length() == 0) {
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }

                else if(!isNumeric(weight)){
                    weightInput.setError("Please enter a valid weight.");
                }
                else if(!isNumeric(height)){
                    heightInput.setError("Please enter a valid height.");
                }

                else if(!isNumeric(goalWeight)){
                    goalWeightInput.setError("Please enter a valid weight.");
                }

                else {
                    Fragment fragment = new MeasurementFragment();
                    bundle.putDouble(WEIGHT, Double.parseDouble(weight));
                    bundle.putDouble(HEIGHT, Double.parseDouble(height));
                    bundle.putDouble(GOAL, Double.parseDouble(goalWeight));
                    bundle.putDouble(ACTIVITY, activityLevelFactor);
                    fragment.setArguments(bundle);
                    replaceFragmentInterface.replaceFragment(fragment);
                }
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

    public final static boolean isNumeric(String number){
        Pattern pattern;
        Matcher matcher;
        final String DATE_PATTERN = "^(0|[1-9][0-9]*)$";
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(number);

        return matcher.matches();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            replaceFragmentInterface = (signUpFragment.ReplaceFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}

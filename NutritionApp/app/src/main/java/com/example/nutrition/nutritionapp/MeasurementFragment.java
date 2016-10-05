package com.example.nutrition.nutritionapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.defaultValue;


public class MeasurementFragment extends Fragment {
    public static String ARM = "com.example.nutritionapp.arm";
    public static String WAIST = "com.example.nutritionapp.waist";
    public static String THIGH = "com.example.nutritionapp.thigh";
    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";
    NutritionSingleton singleton;
    private String arm;
    private String waist;
    private String thigh;
    private Bundle bundle;

    public MeasurementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = this.getArguments();

        boolean isImperial = bundle.getBoolean(signUpFragment.METRIC);
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_measurement, container, false);

        // Get References
        Button goToButton = (Button) v.findViewById(R.id.goToButton);
        goToButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        final EditText armInput = (EditText) v.findViewById(R.id.armInput);
        final TextView armTextView = (TextView) v.findViewById(R.id.armTextView);
        final EditText waistInput = (EditText) v.findViewById(R.id.waistInput);
        final TextView waistTextView = (TextView) v.findViewById(R.id.waistTextView);
        final EditText thighInput = (EditText) v.findViewById(R.id.thighInput);
        final TextView thighTextView = (TextView) v.findViewById(R.id.thighTextView);

        if(isImperial){
            armTextView.setText(R.string.arm_text_imperial);
            waistTextView.setText(R.string.waist_text_imperial);
            thighTextView.setText(R.string.thigh_text_imperial);
        }

        ImageView icon = (ImageView) v.findViewById(R.id.secondPine);

        double imagePos = bundle.getDouble(IMAGE_POS, defaultValue);

        // get image from array
        icon.setImageResource(CheckableImageView.mOriginalIds[(int) imagePos]);

        singleton = NutritionSingleton.getInstance();


        goToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arm = armInput.getText().toString();
                waist = waistInput.getText().toString();
                thigh = thighInput.getText().toString();

                if(arm.length() == 0 || waist.length() == 0 || thigh.length() == 0){
                    Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }

                else if(!goalInformationFragment.isNumeric(arm)){
                    armInput.setError("Please enter a valid arm measurement.");
                }

                else if(!goalInformationFragment.isNumeric(waist)){
                    waistInput.setError("Please enter a valid waist measurement");
                }

                else if(!goalInformationFragment.isNumeric(thigh)){
                    thighInput.setError("Please enter a valid thigh measurement");
                }

                else{
                    double armMeasure = 0.0;
                    double waistMeasure = 0.0;
                    double thighMeasure = 0.0;
                    if (arm.length() > 0) {
                        armMeasure = Double.parseDouble(arm);
                    }

                    if (waist.length() > 0) {
                        waistMeasure = Double.parseDouble(waist);
                    }

                    if (thigh.length() > 0) {
                        thighMeasure = Double.parseDouble(thigh);
                    }

                    bundle.putDouble(ARM, armMeasure);
                    bundle.putDouble(WAIST, waistMeasure);
                    bundle.putDouble(THIGH, thighMeasure);


                    String email = bundle.getString(signUpFragment.EMAIL);
                    String password = bundle.getString(signUpFragment.PASSWORD);

                    ((SignUpActivity) getActivity()).finishSignup(email, password, bundle);
                }



            }
        });
        return v;
    }


}

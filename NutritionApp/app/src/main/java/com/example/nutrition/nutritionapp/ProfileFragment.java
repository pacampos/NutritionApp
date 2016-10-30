package com.example.nutrition.nutritionapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrition.nutritionapp.Model.ProfileModel;


public class ProfileFragment extends Fragment {
    boolean isEditMode=false;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_material_profile, container, false);

        // get references
        final TextView height = (TextView) v.findViewById(R.id.height);
        final TextView currentWeight = (TextView) v.findViewById(R.id.currentWeight);
        final TextView targetWeight = (TextView) v.findViewById(R.id.targetWeight);
        final TextView calorieCount = (TextView) v.findViewById(R.id.calorieCount);
        final TextView bmi = (TextView) v.findViewById(R.id.bmi);
        ImageView icon = (ImageView) v.findViewById(R.id.profileImage);
        TextView nameProfile = (TextView) v.findViewById(R.id.nameProfile);

        final TextView currentWeightLabel = (TextView) v.findViewById(R.id.profileCurrentWeightTextView);
        final TextView currentHeightLabel = (TextView) v.findViewById(R.id.profileCurrentHeightLabel);
        final TextView goalWeightLabel = (TextView) v.findViewById(R.id.profileGoalWeightTextView);

        final NutritionSingleton singleton = NutritionSingleton.getInstance();
        final FloatingActionButton editButton = (FloatingActionButton) v.findViewById(R.id.edit_fab);
        final EditText currWeightEditText = (EditText) v.findViewById(R.id.editCurrentWeightEditText);
        final EditText currHeightEditText = (EditText) v.findViewById(R.id.editCurrentHeightEditText);
        final EditText goalWeightEditText = (EditText) v.findViewById(R.id.editGoalWeightEditText);
        final EditText currHeightFeetEditText = (EditText) v.findViewById(R.id.profileHeightFeetEditText);
        final EditText currHeightInchesEditText = (EditText) v.findViewById(R.id.profileHeightInchesEditText);
        final LinearLayout currHeightImperialLayout = (LinearLayout) v.findViewById(R.id.profileHeightLayout);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEditMode) {
                    height.setVisibility(View.INVISIBLE);
                    currentWeight.setVisibility(View.INVISIBLE);
                    targetWeight.setVisibility(View.INVISIBLE);

                    if(singleton.getCurrProfile().getIsImperial()){
                        currHeightImperialLayout.setVisibility(View.VISIBLE);
                        currHeightFeetEditText.setText(String.valueOf((int) singleton.getCurrProfile().getHeightFeetPart()));
                        currHeightInchesEditText.setText(String.valueOf((int) singleton.getCurrProfile().getHeightInchesPart()));
                    }

                    else{
                        currHeightEditText.setVisibility(View.VISIBLE);
                        currHeightEditText.setText(height.getText().toString());
                    }

                    currWeightEditText.setVisibility(View.VISIBLE);
                    currWeightEditText.setText(currentWeight.getText().toString());
                    goalWeightEditText.setVisibility(View.VISIBLE);
                    goalWeightEditText.setText(targetWeight.getText().toString());

                    editButton.setImageResource(R.drawable.ic_done_white_24dp);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(convertDPToPixels(55), convertDPToPixels(50), convertDPToPixels(0), convertDPToPixels(0));

                    currentHeightLabel.setLayoutParams(layoutParams);
                    currentWeightLabel.setLayoutParams(layoutParams);
                    goalWeightLabel.setLayoutParams(layoutParams);

                    isEditMode=!isEditMode;
                }

                else{
                    if(currWeightEditText.getText().toString().length() > 0 &&
                            (currHeightEditText.getText().toString().length() > 0 ||
                                    (currHeightFeetEditText.getText().toString().length() >0
                                            && currHeightInchesEditText.getText().toString().length() > 0 )) &&
                            goalWeightEditText.getText().toString().length() >0){
                        isEditMode=!isEditMode;

                        String currHeight = null;
                        String currHeightFeet = null;
                        String currHeightInches = null;

                        if(singleton.getCurrProfile().getIsImperial()){
                            currHeightFeet = currHeightFeetEditText.getText().toString();
                            currHeightInches = currHeightInchesEditText.getText().toString();
                        }

                        else{
                            currHeight=currHeightEditText.getText().toString();
                        }

                        String currWeight= currWeightEditText.getText().toString();
                        String goalWeight=goalWeightEditText.getText().toString();

                        NutritionSingleton singleton=NutritionSingleton.getInstance();

                        Double doubleCurrHeight = 0.0;
                        Double doubleCurrHeightInches = 0.0;
                        Double doubleCurrHeightFeet = 0.0;
                        if(singleton.getCurrProfile().getIsImperial()){
                            doubleCurrHeightInches=new Double(currHeightInches);
                            doubleCurrHeightFeet = new Double(currHeightFeet);
                        }

                        else{
                            doubleCurrHeight=new Double(currHeight);
                        }

                        Double doubleCurrWeight=new Double(currWeight);
                        Double doubleGoalWeight=new Double(goalWeight);

                        if(singleton.getCurrProfile().getIsImperial()){

                            singleton.updateCurrHeight(doubleCurrHeightFeet, doubleCurrHeightInches);

                            if(singleton.getCurrProfile().getCurrWeightPounds() != doubleCurrWeight){
                                singleton.updateCurrWeight(doubleCurrWeight);
                            }

                            if(singleton.getCurrProfile().getGoalWeightPounds() != doubleGoalWeight){
                                singleton.updateGoalWeight(doubleGoalWeight);
                            }
                        }

                        else{
                            if(singleton.getCurrProfile().getHeightCentimeters() != doubleCurrHeight ){
                                singleton.updateCurrHeight(doubleCurrHeight);
                            }

                            if(singleton.getCurrProfile().getCurrWeightKilos() != doubleCurrWeight){
                                singleton.updateCurrWeight(doubleCurrWeight);
                            }

                            if(singleton.getCurrProfile().getGoalWeightKilos() != doubleGoalWeight){
                                singleton.updateCurrHeight(doubleGoalWeight);
                            }
                        }

                        if(singleton.getCurrProfile().getIsImperial()){
                            height.setText(String.valueOf(doubleCurrHeightFeet.intValue())+"'"+String.valueOf(doubleCurrHeightInches.intValue()));
                        }

                        else{
                            height.setText(currHeight);
                        }

                        currentWeight.setText(currWeight);
                        targetWeight.setText(goalWeight);

                        bmi.setText(String.valueOf((int) singleton.getCurrProfile().calculateBMI()));
                        calorieCount.setText(String.valueOf((int) singleton.getCurrProfile().calcCaloriesBurnedNaturally()));

                        height.setVisibility(View.VISIBLE);
                        currentWeight.setVisibility(View.VISIBLE);
                        targetWeight.setVisibility(View.VISIBLE);

                        currHeightEditText.setVisibility(View.INVISIBLE);
                        currHeightImperialLayout.setVisibility(View.INVISIBLE);
                        currWeightEditText.setVisibility(View.INVISIBLE);
                        goalWeightEditText.setVisibility(View.INVISIBLE);

                        editButton.setImageResource(R.drawable.ic_mode_edit_white_24dp);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(convertDPToPixels(55), convertDPToPixels(30), convertDPToPixels(0), convertDPToPixels(0));

                        currentHeightLabel.setLayoutParams(layoutParams);
                        currentWeightLabel.setLayoutParams(layoutParams);
                        goalWeightLabel.setLayoutParams(layoutParams);
                    }

                    else{
                        Toast.makeText(getContext(),"Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        ProfileModel model = singleton.getCurrProfile();
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) v.findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(model.getName()+"'s Profile");
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        nameProfile.setText(model.getName());
        if(model.getIsImperial()){
            height.setText(String.valueOf((int) model.getHeightFeetPart())+"'"+String.valueOf((int) model.getHeightInchesPart()));
            currentWeight.setText(String.valueOf(model.getCurrWeightPounds()));
            targetWeight.setText(String.valueOf(model.getGoalWeightPounds()));
        }

        else{
            height.setText(String.valueOf(model.getHeightCentimeters()));
            currentWeight.setText(String.valueOf(model.getCurrWeightKilos()));
            targetWeight.setText(String.valueOf(model.getGoalWeightKilos()));
        }

        icon.setImageResource(CheckableImageView.mOriginalIds[(int) model.getImagePos()]);
        bmi.setText(String.valueOf((int) model.calculateBMI()));
        calorieCount.setText(String.valueOf((int) model.calcCaloriesBurnedNaturally()));
        if(model.getIsImperial()){
            currentWeightLabel.setText(R.string.weight_text_imperial);
            currentHeightLabel.setText(R.string.height_text_imperial);
            goalWeightLabel.setText(R.string.goalWeight_text_imperial);
        }

        // get references
        Button progressButton = (Button) v.findViewById(R.id.progressButton);
        progressButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        progressButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent i = new Intent(getActivity(), ProgressActivity.class);
                    startActivity(i);
            }
        });



        return v;
    }

    private int convertDPToPixels(int dp){
        Resources r = getContext().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );

        return px;
    }
}

package com.fearnot.snapp.Fragments;

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

import com.fearnot.snapp.Activities.ProgressActivity;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.Model.ProfileModel;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;
import com.fearnot.snapp.Views.CheckableImageView;


public class ProfileFragment extends Fragment {
    boolean isEditMode = false;
    private ReplaceFragmentInterface replaceFragmentInterface;
    private TextView height;
    private TextView currentWeight;
    private TextView targetWeight;
    private TextView calorieCount;
    private TextView arm;
    private TextView waist;
    private TextView thigh;
    private TextView bmi;
    private ImageView icon;
    private TextView nameProfile;
    private TextView currentWeightLabel;
    private TextView currentHeightLabel;
    private TextView goalWeightLabel;
    private TextView currentArmLabel;
    private TextView currentWaistLabel;
    private TextView currentThighLabel;
    private FloatingActionButton editButton;
    private EditText currWeightEditText;
    private EditText currHeightEditText;
    private EditText goalWeightEditText;
    private EditText currHeightFeetEditText;
    private EditText currHeightInchesEditText;
    private EditText currentArmEditText;
    private EditText currentWaistEditText;
    private EditText currentThighEdiText;
    private LinearLayout currHeightImperialLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_material_profile, container, false);

        // get references
        height = (TextView) v.findViewById(R.id.height);
        currentWeight = (TextView) v.findViewById(R.id.currentWeight);
        targetWeight = (TextView) v.findViewById(R.id.targetWeight);
        calorieCount = (TextView) v.findViewById(R.id.calorieCount);
        arm = (TextView) v.findViewById(R.id.arm);
        waist = (TextView) v.findViewById(R.id.waist);
        thigh = (TextView) v.findViewById(R.id.thigh);
        bmi = (TextView) v.findViewById(R.id.bmi);
        icon = (ImageView) v.findViewById(R.id.profileImage);
        nameProfile = (TextView) v.findViewById(R.id.nameProfile);

        currentWeightLabel = (TextView) v.findViewById(R.id.profileCurrentWeightTextView);
        currentHeightLabel = (TextView) v.findViewById(R.id.profileCurrentHeightLabel);
        goalWeightLabel = (TextView) v.findViewById(R.id.profileGoalWeightTextView);
        currentArmLabel = (TextView) v.findViewById(R.id.profileArmTextView);
        currentWaistLabel = (TextView) v.findViewById(R.id.profileWaistTextView);
        currentThighLabel = (TextView) v.findViewById(R.id.profileThighTextView);

        final NutritionSingleton singleton = NutritionSingleton.getInstance();
        editButton = (FloatingActionButton) v.findViewById(R.id.edit_fab);
        currWeightEditText = (EditText) v.findViewById(R.id.editCurrentWeightEditText);
        currHeightEditText = (EditText) v.findViewById(R.id.editCurrentHeightEditText);
        goalWeightEditText = (EditText) v.findViewById(R.id.editGoalWeightEditText);
        currHeightFeetEditText = (EditText) v.findViewById(R.id.profileHeightFeetEditText);
        currHeightInchesEditText = (EditText) v.findViewById(R.id.profileHeightInchesEditText);
        currentArmEditText = (EditText) v.findViewById(R.id.editArm);
        currentWaistEditText = (EditText) v.findViewById(R.id.editWaist);
        currentThighEdiText = (EditText) v.findViewById(R.id.editThigh);
        currHeightImperialLayout = (LinearLayout) v.findViewById(R.id.profileHeightLayout);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditMode) {
                    height.setVisibility(View.INVISIBLE);
                    currentWeight.setVisibility(View.INVISIBLE);
                    targetWeight.setVisibility(View.INVISIBLE);
                    arm.setVisibility(View.INVISIBLE);
                    waist.setVisibility(View.INVISIBLE);
                    thigh.setVisibility(View.INVISIBLE);
                    if (singleton.getCurrProfile().getIsImperial()) {
                        currHeightImperialLayout.setVisibility(View.VISIBLE);
                        currHeightFeetEditText.setText(String.valueOf((int) singleton.getCurrProfile().getHeightFeetPart()));
                        currHeightInchesEditText.setText(String.valueOf((int) singleton.getCurrProfile().getHeightInchesPart()));
                        currentArmEditText.setText(String.valueOf((int) singleton.getCurrProfile().getArmMeasureInches()));
                        currentWaistEditText.setText(String.valueOf((int) singleton.getCurrProfile().getWaistMeasureInches()));
                        currentThighEdiText.setText(String.valueOf((int) singleton.getCurrProfile().getThighMeasureInches()));

                    } else {
                        currHeightEditText.setVisibility(View.VISIBLE);
                        currHeightEditText.setText(height.getText().toString());
                        currentArmEditText.setText(String.valueOf((int) singleton.getCurrProfile().getArmMeasureCentimeter()));
                        currentWaistEditText.setText(String.valueOf((int) singleton.getCurrProfile().getWaistMeasureCentimeter()));
                        currentThighEdiText.setText(String.valueOf((int) singleton.getCurrProfile().getThighMeasureCentimeter()));
                    }

                    currWeightEditText.setVisibility(View.VISIBLE);
                    currWeightEditText.setText(currentWeight.getText().toString());
                    goalWeightEditText.setVisibility(View.VISIBLE);
                    goalWeightEditText.setText(targetWeight.getText().toString());
                    currentArmEditText.setVisibility(View.VISIBLE);
                    currentArmEditText.setText(arm.getText().toString());
                    currentWaistEditText.setVisibility(View.VISIBLE);
                    currentWaistEditText.setText(waist.getText().toString());
                    currentThighEdiText.setVisibility(View.VISIBLE);
                    currentThighEdiText.setText(thigh.getText().toString());

                    editButton.setImageResource(R.drawable.ic_done_white_24dp);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(convertDPToPixels(55), convertDPToPixels(50), convertDPToPixels(0), convertDPToPixels(0));

                    currentHeightLabel.setLayoutParams(layoutParams);
                    currentWeightLabel.setLayoutParams(layoutParams);
                    goalWeightLabel.setLayoutParams(layoutParams);
                    currentArmLabel.setLayoutParams(layoutParams);
                    currentWaistLabel.setLayoutParams(layoutParams);
                    currentThighLabel.setLayoutParams(layoutParams);

                    isEditMode = !isEditMode;
                } else {
                    if (currWeightEditText.getText().toString().length() > 0 &&
                            (currHeightEditText.getText().toString().length() > 0 ||
                                    (currHeightFeetEditText.getText().toString().length() > 0
                                            && currHeightInchesEditText.getText().toString().length() > 0)) &&
                            goalWeightEditText.getText().toString().length() > 0 && currentArmEditText.getText().toString().length() > 0
                            && currentWaistEditText.getText().toString().length() > 0 && currentThighEdiText.getText().toString().length() > 0) {
                        isEditMode = !isEditMode;

                        String currHeight = null;
                        String currHeightFeet = null;
                        String currHeightInches = null;

                        if (singleton.getCurrProfile().getIsImperial()) {
                            currHeightFeet = currHeightFeetEditText.getText().toString();
                            currHeightInches = currHeightInchesEditText.getText().toString();
                        } else {
                            currHeight = currHeightEditText.getText().toString();
                        }

                        String currWeight = currWeightEditText.getText().toString();
                        String goalWeight = goalWeightEditText.getText().toString();
                        String currArm = currentArmEditText.getText().toString();
                        String currWaist = currentWaistEditText.getText().toString();
                        String currThigh = currentThighEdiText.getText().toString();

                        NutritionSingleton singleton = NutritionSingleton.getInstance();

                        Double doubleCurrHeight = 0.0;
                        Double doubleCurrHeightInches = 0.0;
                        Double doubleCurrHeightFeet = 0.0;
                        if (singleton.getCurrProfile().getIsImperial()) {
                            doubleCurrHeightInches = Double.valueOf(currHeightInches);
                            doubleCurrHeightFeet = Double.valueOf(currHeightFeet);
                        } else {
                            doubleCurrHeight = Double.valueOf(currHeight);
                        }

                        Double doubleCurrWeight = Double.valueOf(currWeight);
                        Double doubleGoalWeight = Double.valueOf(goalWeight);
                        Double doubleCurrArm = Double.valueOf(currArm);
                        Double doubleCurrWaist = Double.valueOf(currWaist);
                        Double doubleCurrThigh = Double.valueOf(currThigh);

                        if (singleton.getCurrProfile().getIsImperial()) {

                            singleton.updateCurrHeight(doubleCurrHeightFeet, doubleCurrHeightInches);

                            if (singleton.getCurrProfile().getCurrWeightPounds() != doubleCurrWeight) {
                                singleton.updateCurrWeight(doubleCurrWeight);
                            }

                            if (singleton.getCurrProfile().getGoalWeightPounds() != doubleGoalWeight) {
                                singleton.updateGoalWeight(doubleGoalWeight);
                            }

                            if (singleton.getCurrProfile().getArmMeasureInches() != doubleCurrArm) {
                                singleton.updateArm(doubleCurrArm);
                            }
                            if (singleton.getCurrProfile().getWaistMeasureInches() != doubleCurrWaist) {
                                singleton.updateWaist(doubleCurrWaist);
                            }
                            if (singleton.getCurrProfile().getThighMeasureInches() != doubleCurrThigh) {
                                singleton.updateThigh(doubleCurrThigh);
                            }

                        } else {
                            if (singleton.getCurrProfile().getHeightCentimeters() != doubleCurrHeight) {
                                singleton.updateCurrHeight(doubleCurrHeight);
                            }

                            if (singleton.getCurrProfile().getCurrWeightKilos() != doubleCurrWeight) {
                                singleton.updateCurrWeight(doubleCurrWeight);
                            }

                            if (singleton.getCurrProfile().getGoalWeightKilos() != doubleGoalWeight) {
                                singleton.updateCurrHeight(doubleGoalWeight);
                            }
                            if (singleton.getCurrProfile().getArmMeasureCentimeter() != doubleCurrArm) {
                                singleton.updateArm(doubleCurrArm);
                            }
                            if (singleton.getCurrProfile().getWaistMeasureCentimeter() != doubleCurrWaist) {
                                singleton.updateWaist(doubleCurrWaist);
                            }
                            if (singleton.getCurrProfile().getThighMeasureCentimeter() != doubleCurrThigh) {
                                singleton.updateThigh(doubleCurrThigh);
                            }
                        }

                        if (singleton.getCurrProfile().getIsImperial()) {
                            height.setText(String.valueOf(doubleCurrHeightFeet.intValue()) + "'" + String.valueOf(doubleCurrHeightInches.intValue()));
                        } else {
                            height.setText(currHeight);
                        }

                        currentWeight.setText(currWeight);
                        targetWeight.setText(goalWeight);

                        arm.setText(currArm);
                        waist.setText(currWaist);
                        thigh.setText(currThigh);

                        bmi.setText(String.valueOf((int) singleton.getCurrProfile().calculateBMI()));
                        calorieCount.setText(String.valueOf((int) singleton.getCurrProfile().calcCaloriesBurnedNaturally()));

                        height.setVisibility(View.VISIBLE);
                        currentWeight.setVisibility(View.VISIBLE);
                        targetWeight.setVisibility(View.VISIBLE);
                        arm.setVisibility(View.VISIBLE);
                        waist.setVisibility(View.VISIBLE);
                        thigh.setVisibility(View.VISIBLE);

                        currHeightEditText.setVisibility(View.INVISIBLE);
                        currHeightImperialLayout.setVisibility(View.INVISIBLE);
                        currWeightEditText.setVisibility(View.INVISIBLE);
                        goalWeightEditText.setVisibility(View.INVISIBLE);
                        currentArmEditText.setVisibility(View.INVISIBLE);
                        currentWaistEditText.setVisibility(View.INVISIBLE);
                        currentThighEdiText.setVisibility(View.INVISIBLE);

                        editButton.setImageResource(R.drawable.ic_mode_edit_white_24dp);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(convertDPToPixels(55), convertDPToPixels(30), convertDPToPixels(0), convertDPToPixels(0));

                        currentHeightLabel.setLayoutParams(layoutParams);
                        currentWeightLabel.setLayoutParams(layoutParams);
                        goalWeightLabel.setLayoutParams(layoutParams);
                        currentArmLabel.setLayoutParams(layoutParams);
                        currentWaistLabel.setLayoutParams(layoutParams);
                        currentThighLabel.setLayoutParams(layoutParams);
                    } else {
                        Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        ProfileModel model = singleton.getCurrProfile();
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) v.findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(model.getName() + "'s Profile");
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        nameProfile.setText(model.getName());
        if (model.getIsImperial()) {
            height.setText(String.valueOf((int) model.getHeightFeetPart()) + "'" + String.valueOf((int) model.getHeightInchesPart()));
            currentWeight.setText(String.valueOf(model.getCurrWeightPounds()));
            targetWeight.setText(String.valueOf(model.getGoalWeightPounds()));
            if (model.getArmMeasureInches() == -1.0) {
                arm.setText("");
            } else {
                arm.setText(String.valueOf(model.getArmMeasureInches()));
            }
            if (model.getWaistMeasureInches() == -1.0) {
                waist.setText("");
            } else {
                waist.setText(String.valueOf(model.getWaistMeasureInches()));
            }
            if (model.getThighMeasureInches() == -1.0) {
                waist.setText("");
            } else {
                thigh.setText(String.valueOf(model.getThighMeasureInches()));
            }
        } else {
            height.setText(String.valueOf(model.getHeightCentimeters()));
            currentWeight.setText(String.valueOf(model.getCurrWeightKilos()));
            targetWeight.setText(String.valueOf(model.getGoalWeightKilos()));

            if (model.getArmMeasureCentimeter() == -1.0) {
                arm.setText("");
            } else {
                arm.setText(String.valueOf(model.getArmMeasureCentimeter()));
            }
            if (model.getWaistMeasureCentimeter() == -1.0) {
                waist.setText("");
            } else {
                waist.setText(String.valueOf(model.getWaistMeasureCentimeter()));
            }
            if (model.getThighMeasureCentimeter() == -1.0) {
                thigh.setText(String.valueOf(model.getThighMeasureCentimeter()));
            }

        }

        icon.setImageResource(CheckableImageView.mOriginalIds[(int) model.getImagePos()]);
        bmi.setText(String.valueOf((int) model.calculateBMI()));
        calorieCount.setText(String.valueOf((int) model.calcCaloriesBurnedNaturally()));
        if (model.getIsImperial()) {
            currentWeightLabel.setText(R.string.weight_text_imperial);
            currentHeightLabel.setText(R.string.height_text_imperial);
            goalWeightLabel.setText(R.string.goalWeight_text_imperial);
            currentArmLabel.setText(R.string.arm_text_imperial);
            currentWaistLabel.setText(R.string.waist_text_imperial);
            currentThighLabel.setText(R.string.thigh_text_imperial);
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

    private int convertDPToPixels(int dp) {
        Resources r = getContext().getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }
}

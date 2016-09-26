package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrition.nutritionapp.Model.ProfileModel;

import org.w3c.dom.Text;


public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // get references
        TextView nameProfile = (TextView) v.findViewById(R.id.nameProfile);
        TextView height = (TextView) v.findViewById(R.id.height);
        TextView currentWeight = (TextView) v.findViewById(R.id.currentWeight);
        TextView targetWeight = (TextView) v.findViewById(R.id.targetWeight);
        TextView calorieCount = (TextView) v.findViewById(R.id.calorieCount);
        TextView bmi = (TextView) v.findViewById(R.id.bmi);
        ImageView icon = (ImageView) v.findViewById(R.id.profileImage);
        NutritionSingleton singleton = NutritionSingleton.getInstance();



        ProfileModel model = singleton.getCurrProfile();

        nameProfile.setText(model.getName());
        height.setText(String.valueOf(model.getHeightCentimeters()));
        currentWeight.setText(String.valueOf(model.getCurrWeightKilos()));
        targetWeight.setText(String.valueOf(model.getGoalWeightKilos()));
        icon.setImageResource(CheckableImageView.mOriginalIds[(int) model.getImagePos()]);


        // get references
        Button progressButton = (Button) v.findViewById(R.id.progressButton);
        progressButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        return v;
    }
}

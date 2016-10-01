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

import com.example.nutrition.nutritionapp.Model.DayModel;

public class waterEntryFragment extends Fragment {
    public waterEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_water_entry, container, false);

        final EditText waterEntryEditText = (EditText) v.findViewById(R.id.waterInput);
        Button waterEntryButton = (Button) v.findViewById(R.id.logWaterButton);
        ImageView cupImageView = (ImageView) v.findViewById(R.id.cupImageView);
        final TextView waterDrankTextView = (TextView) v.findViewById(R.id.amountWaterDrankLabel);

        waterEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountOfWater=waterEntryEditText.getText().toString();
                if(amountOfWater.length() > 0 && amountOfWater.matches("^[0-9]+$")){
                    DayModel day = NutritionSingleton.getInstance().getCurrDay();
                    day.addWaterAmount(Double.parseDouble(amountOfWater));
                    waterDrankTextView.setText(String.valueOf(day.getWaterAmountDrank()));
                }
            }
        });

        cupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayModel day = NutritionSingleton.getInstance().getCurrDay();
                day.addEightOuncesWaterAmount();
                waterDrankTextView.setText(String.valueOf(day.getWaterAmountDrank()));
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}

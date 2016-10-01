package com.example.nutrition.nutritionapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class waterEntryFragment extends Fragment {
    public waterEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_water_entry, container, false);

        final EditText waterEntryEditText = (EditText) v.findViewById(R.id.waterInput);
        Button waterEntryButton = (Button) v.findViewById(R.id.waterEntryButton);
        ImageView cupImageView = (ImageView) v.findViewById(R.id.cupImageView);

        waterEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountOfWater=waterEntryEditText.getText().toString();
                if(amountOfWater.length() > 0 && amountOfWater.matches("[0-9]+")){
                    NutritionSingleton.getInstance().getCurrDay().addWaterAmount(Double.parseDouble(amountOfWater));
                }
            }
        });

        cupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NutritionSingleton.getInstance().getCurrDay().addEightOuncesWaterAmount();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}

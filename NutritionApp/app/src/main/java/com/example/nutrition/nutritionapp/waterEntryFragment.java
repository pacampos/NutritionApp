package com.example.nutrition.nutritionapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrition.nutritionapp.Model.DayModel;
import com.example.nutrition.nutritionapp.libs.WaveView;

public class waterEntryFragment extends Fragment {
    private WaveHelper mWaveHelper;
    private int mBorderColor = Color.LTGRAY;
    private int mBorderWidth = 1;
    public WaveView waveView;
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
        DayModel currDay = NutritionSingleton.getInstance().getCurrDay();
        double water= currDay.getWaterAmountDrank();
        waterDrankTextView.setText(String.valueOf(water));

        waveView = (WaveView) v.findViewById(R.id.wave);
        mWaveHelper = new WaveHelper(waveView);

        waveView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                waveView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                waveView.setWaveColor(Color.WHITE,Color.BLUE);
                waveView.setShapeType(WaveView.ShapeType.SQUARE);
                waveView.setBorder(mBorderWidth, mBorderColor);

            }
        });



        waterEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountOfWater=waterEntryEditText.getText().toString();
                if(amountOfWater.length() > 0 && amountOfWater.matches("^[0-9]+$")){
                    DayModel day = NutritionSingleton.getInstance().getCurrDay();
                    NutritionSingleton.getInstance().updateWater(Double.parseDouble(amountOfWater));
                    waterDrankTextView.setText(String.valueOf(day.getWaterAmountDrank()));
                }
            }
        });

        cupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayModel day = NutritionSingleton.getInstance().getCurrDay();
                NutritionSingleton.getInstance().updateWater(8.0);
                waterDrankTextView.setText(String.valueOf(day.getWaterAmountDrank()));
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mWaveHelper.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveHelper.cancel();

    }
}

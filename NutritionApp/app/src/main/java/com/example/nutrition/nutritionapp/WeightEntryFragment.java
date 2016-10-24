package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class WeightEntryFragment extends Fragment {

    public WeightEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_weight_entry, container, false);

        final EditText weightEntryEditText = (EditText) v.findViewById(R.id.weightEntryEditText);
        Button weightEntryButton = (Button) v.findViewById(R.id.weightEnterButton);
        weightEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(weightEntryEditText.getText().length() > 0 && goalInformationFragment.isNumeric(weightEntryEditText.getText().toString()) ){
                    NutritionSingleton.getInstance().updateCurrWeight(Double.valueOf(weightEntryEditText.getText().toString()));
                    Intent i =new Intent(getContext(), ActivityHome.class);
                    startActivity(i);
                }

            }
        });
        return v;
    }


}

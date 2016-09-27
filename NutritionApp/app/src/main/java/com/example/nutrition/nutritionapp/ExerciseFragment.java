package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;


public class ExerciseFragment extends Fragment {

    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_exercise, container, false);

        // Get references
        Spinner typeSpinner = (Spinner) v.findViewById(R.id.typeSpinner);
        Spinner durationSpinner = (Spinner) v.findViewById(R.id.durationSpinner);

        Button recordButton = (Button) v.findViewById(R.id.recordButton);
        Button journalButton = (Button) v.findViewById(R.id.journalButton);

        ImageView image = (ImageView) v.findViewById(R.id.footprint);
        image.setRotation(320);
        return v;
    }

}

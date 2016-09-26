package com.example.nutrition.nutritionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
        Button progressButton = (Button) v.findViewById(R.id.progressButton);
        progressButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        progressButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), ActivityHome.class);
                    startActivity(i);
                }
        });

        return v;
    }
}

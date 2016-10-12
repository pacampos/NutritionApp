package com.example.nutrition.nutritionapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static android.R.attr.defaultValue;

public class SwitchUserFragment extends Fragment {
    private Bundle bundle;

    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";

    public SwitchUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal_information, container, false);

        bundle = this.getArguments();

        ImageView icon = (ImageView) v.findViewById(R.id.iconImage);
        double imagePos = bundle.getDouble(IMAGE_POS, defaultValue);
        // get image from array
        icon.setImageResource(CheckableImageView.mOriginalIds[(int) imagePos]);

        return v;
    }
}

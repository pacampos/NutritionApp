package com.example.nutrition.nutritionapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import static android.R.attr.id;
import static android.R.attr.start;


public class HomeFragment extends Fragment {

    private float grainsPortion = 0f;
    private float veggiePortion = 0f;
    private float fruitPortion= 0f;
    private float dairyPortion = 0f;
    private float meatPortion = 0f;


    PyramidView pyramid;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        pyramid = (PyramidView) v.findViewById(R.id.pyramid);

        Button profileButton = (Button) v.findViewById(R.id.profileButton);
        Button exerciseButton = (Button) v.findViewById(R.id.exerciseButton);
        Button waterEntryButton = (Button) v.findViewById(R.id.waterEntryButton);
        Button foodEntryButton = (Button) v.findViewById(R.id.foodDrinkEntrybutton);
        Button servingButton = (Button) v.findViewById(R.id.servingButton);

        ImageView hamburgerIcon = (ImageView) v.findViewById(R.id.hamburgerIcon);
        ImageView waterIcon = (ImageView) v.findViewById(R.id.waterIcon);
        ImageView weightIcon = (ImageView) v.findViewById(R.id.weightIcon);

        servingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment servingFragment = new servingFragment();
                replaceFragment(servingFragment);
            }
        });

        hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("com.example.nutrition.nutritionapp.Model.Food Entry");
                alertDialog.setMessage("Input and log your meals here.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        waterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Water Entry");
                alertDialog.setMessage("Input and log your water intake here.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        weightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Exercise Entry");
                alertDialog.setMessage("Input and log your activity here.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ExerciseFragment();
                replaceFragment(fragment);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
            }
        });

        waterEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new waterEntryFragment();
                replaceFragment(fragment);
            }
        });

        foodEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), FoodActivity.class);
                startActivity(i);
            }
        });


        return v;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateServings(float grainsPercent, float veggiesPercent, float fruitsPercent, float dairyPercent, float meatPercent ){
        addToGrains(grainsPercent);
        addToVeggies(veggiesPercent);
        addToFruits(fruitsPercent);
        addToDairy(dairyPercent);
        addToMeat(meatPercent);
        pyramid.setFirstPortion(grainsPortion);
        pyramid.setSecondPortion(veggiePortion);
        pyramid.setThirdPortion(fruitPortion);
        pyramid.setFourthPortion(dairyPortion);
        pyramid.setFifthPortion(meatPortion);
        pyramid.invalidate();
    }


    private void addToGrains(float percentage){
        grainsPortion+=percentage;
    }

    private void addToFruits(float percentage){
        fruitPortion+=percentage;
    }

    private void addToVeggies(float percentage){
        veggiePortion+=percentage;
    }

    private void addToDairy(float percentage){
        dairyPortion=+percentage;
    }

    private void addToMeat(float percentage){
        meatPortion+=percentage;
    }
    
}

package com.fearnot.snapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fearnot.snapp.Model.DayModel;
import com.fearnot.snapp.Model.ProfileModel;
import com.fearnot.snapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    final private float MAX_GRAINS= 6f;
    final private float MAX_MEAT = 2f;
    final private float MAX_VEGGIES = 4f;
    final private float MAX_FRUIT = 4f;
    final private float MAX_DAIRY = 3f;
    private float grainsPortion = 0f;
    private float veggiePortion = 0f;
    private float fruitPortion= 0f;
    private float dairyPortion = 0f;
    private float meatPortion = 0f;
    private Drawer result;
    private TextView calorieRemain;
    private ProgressBar sweetsProgressBar;
    private TextView grainsTextView;
    private TextView fruitsTextView;
    private TextView veggiesTextView;
    private TextView dairyTextView;
    private TextView meatTextView;


    PyramidView pyramid;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        grainsTextView = (TextView) v.findViewById(R.id.grainTextView);
        fruitsTextView = (TextView) v.findViewById(R.id.fruitsTextView);
        veggiesTextView = (TextView) v.findViewById(R.id.veggiesTextView);
        dairyTextView = (TextView) v.findViewById(R.id.dairyTextView);
        meatTextView = (TextView) v.findViewById(R.id.meatTextView);

        // Calorie Remaining
        calorieRemain = (TextView) v.findViewById(R.id.calorieRemain);
        setCaloriesNeeded(getCaloriesNeeded());

        // SIDE MENU

        // Drawer Items
        PrimaryDrawerItem item = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_home);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_profile);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_graphs);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_credits);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_logout);

        ArrayList<IProfile<ProfileDrawerItem>> profiles= new ArrayList<>();
        ArrayList<ProfileModel> profileModels = new ArrayList<>();
        profileModels.add(NutritionSingleton.getInstance().getCurrProfile());
        for(ProfileModel model: NutritionSingleton.getInstance().getAllProfiles()){
            if(model.id != NutritionSingleton.getInstance().getCurrProfile().id){
                profileModels.add(model);
            }
        }

        for(ProfileModel model: profileModels){
            profiles.add(new ProfileDrawerItem().withName(model.getName())
                    .withIcon(getResources().getDrawable(CheckableImageView.mOriginalIds[(int) model.getImagePos()]))
            .withIdentifier(model.id));

        }

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.wallpaperandroid50)
                .addProfiles(profiles.toArray(new IProfile[profiles.size()]))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        NutritionSingleton.getInstance().switchProfiles((int) profile.getIdentifier());
                        setCaloriesNeeded(getCaloriesNeeded());
                        setPyramid();
                        setSweetsBar();
                        return false;
                    }
                })
                .build();

        // Actual Drawer
        result= new DrawerBuilder()
                .withActivity(getActivity())
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item,
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            replaceFragment(new HomeFragment());
                            close();
                        } else if (position == 2) {
                            replaceFragment(new ProfileFragment());
                            close();
                        } else if (position == 3) {
                            replaceFragment(new ProgressFragment());
                            close();
                        } else if (position == 4) {
                            replaceFragment(new CreditFragment());
                            close();
                        } else if (position == 6) {
                            // logout
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                        }
                        return true;
                    }
                })
                .build();

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_2x);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        toolbar.setNavigationOnClickListener(new Toolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
               result.openDrawer();
            }
        });

        sweetsProgressBar = (ProgressBar) v.findViewById(R.id.sweetsProgressBar);
        setSweetsBar();

        pyramid = (PyramidView) v.findViewById(R.id.pyramid);
        setPyramid();

        Button exerciseButton = (Button) v.findViewById(R.id.exerciseButton);
        Button waterEntryButton = (Button) v.findViewById(R.id.waterEntryButton);
        Button foodEntryButton = (Button) v.findViewById(R.id.foodDrinkEntrybutton);
        Button servingButton = (Button) v.findViewById(R.id.servingButton);
        //Button creditButton = (Button) v.findViewById(R.id.CreditButton);

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

    private void close() {
        result.closeDrawer();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private int getCaloriesNeeded(){
       return (int) NutritionSingleton.getInstance().getCurrProfile().calcCaloriesBurnedNaturally() - (int) NutritionSingleton.getInstance().getCurrDay().calcTotalCaloriesAte();
    }

    private void setCaloriesNeeded(int calories){
        calorieRemain.setText(String.valueOf(calories) + " calories remaining today!");
    }

    private void setPyramid(){
        DayModel dayModel=NutritionSingleton.getInstance().getCurrDay();
        grainsPortion = new Float(dayModel.getServingsGrains());
        veggiePortion = new Float(dayModel.getServingsVeggie());
        fruitPortion = new Float(dayModel.getServingsFruit());
        dairyPortion = new Float(dayModel.getServingsDairy());
        meatPortion = new Float(dayModel.getServingsMeat());

        float grainsPercentage = grainsPortion/MAX_GRAINS;
        float veggiePercentage = veggiePortion/MAX_VEGGIES;
        float fruitsPercentage = fruitPortion/MAX_FRUIT;
        float dairyPercentage = dairyPortion/MAX_DAIRY;
        float meatPercentage = meatPortion/MAX_MEAT;

        if(grainsPercentage > 1){
            grainsTextView.setTextColor(Color.RED);
        }

        if(veggiePercentage > 1){
            veggiesTextView.setTextColor(Color.RED);
        }

        if(fruitsPercentage > 1){
            fruitsTextView.setTextColor(Color.RED);
        }

        if(dairyPercentage > 1){
            dairyTextView.setTextColor(Color.RED);
        }

        if(meatPercentage > 1){
            meatTextView.setTextColor(Color.RED);
        }
        grainsTextView.setText(String.valueOf((int) (grainsPercentage*100))+"%");
        veggiesTextView.setText(String.valueOf((int) (veggiePercentage*100))+"%");
        fruitsTextView.setText(String.valueOf((int) (fruitsPercentage*100))+"%");
        dairyTextView.setText(String.valueOf((int) (dairyPercentage*100))+"%");
        meatTextView.setText(String.valueOf((int) (meatPercentage*100))+"%");

        pyramid.setFirstPortion(grainsPercentage);
        pyramid.setSecondPortion(veggiePercentage);
        pyramid.setThirdPortion(fruitsPercentage);
        pyramid.setFourthPortion(dairyPercentage);
        pyramid.setFifthPortion(meatPercentage);
        pyramid.invalidate();
    }

    private void setSweetsBar(){
        sweetsProgressBar.setProgress((int) NutritionSingleton.getInstance().getCurrDay().getServingsSweets());
    }

    
}

package com.example.nutrition.nutritionapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nutrition.nutritionapp.Model.DayModel;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.Calendar;


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


    PyramidView pyramid;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // SIDE MENU

        // Drawer Items
        PrimaryDrawerItem item = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_home);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_profile);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_graphs);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_credits);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_logout);
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.wallpaperandroid50)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(NutritionSingleton.getInstance().getCurrProfile().getName())
                                .withIcon(getResources().getDrawable(CheckableImageView.mOriginalIds[(int) NutritionSingleton.getInstance().getCurrProfile().getImagePos()]))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        // Actual Drawer
        result= new DrawerBuilder()
                .withActivity(getActivity())
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




        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);


        pyramid = (PyramidView) v.findViewById(R.id.pyramid);

        DayModel dayModel=NutritionSingleton.getInstance().getCurrDay();
        grainsPortion = new Float(dayModel.getServingsGrains());
        veggiePortion = new Float(dayModel.getServingsVeggie());
        fruitPortion = new Float(dayModel.getServingsFruit());
        dairyPortion = new Float(dayModel.getServingsDairy());
        meatPortion = new Float(dayModel.getServingsMeat());

        Button profileButton = (Button) v.findViewById(R.id.profileButton);
        Button exerciseButton = (Button) v.findViewById(R.id.exerciseButton);
        Button waterEntryButton = (Button) v.findViewById(R.id.waterEntryButton);
        Button foodEntryButton = (Button) v.findViewById(R.id.foodDrinkEntrybutton);
        Button servingButton = (Button) v.findViewById(R.id.servingButton);
        Button notifyButton = (Button) v.findViewById(R.id.notifyButton);
        Button logoutButton = (Button) v.findViewById(R.id.logoutButton);
        //Button creditButton = (Button) v.findViewById(R.id.CreditButton);

        ImageView hamburgerIcon = (ImageView) v.findViewById(R.id.hamburgerIcon);
        ImageView waterIcon = (ImageView) v.findViewById(R.id.waterIcon);
        ImageView weightIcon = (ImageView) v.findViewById(R.id.weightIcon);

        pyramid.setFirstPortion(grainsPortion/MAX_GRAINS);
        pyramid.setSecondPortion(veggiePortion/MAX_VEGGIES);
        pyramid.setThirdPortion(fruitPortion/MAX_FRUIT);
        pyramid.setFourthPortion(dairyPortion/MAX_DAIRY);
        pyramid.setFifthPortion(meatPortion/MAX_MEAT);
        pyramid.invalidate();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment progressFrament = new ProgressFragment();
                replaceFragment(progressFrament);
            }
        });

        servingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment servingFragment = new servingFragment();
                replaceFragment(servingFragment);
            }
        });

//        creditButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment creditFragment = new CreditFragment();
//                replaceFragment(creditFragment);
//            }
//        });

        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar sevendayalarm = Calendar.getInstance();
                Intent intent = new Intent(getContext(), Receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

                AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

//                am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,sevendayalarm.getTimeInMillis(),pendingIntent);
                am.setRepeating(AlarmManager.RTC_WAKEUP,sevendayalarm.getTimeInMillis(), 5000,pendingIntent);
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

    private void close() {
        result.closeDrawer();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    
}

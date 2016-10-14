package com.example.nutrition.nutritionapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
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

import java.util.Calendar;

import static android.R.attr.id;
import static android.R.attr.start;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Button profileButton = (Button) v.findViewById(R.id.profileButton);
        Button exerciseButton = (Button) v.findViewById(R.id.exerciseButton);
        Button waterEntryButton = (Button) v.findViewById(R.id.waterEntryButton);
        Button foodEntryButton = (Button) v.findViewById(R.id.foodDrinkEntrybutton);
        Button notifyButton = (Button) v.findViewById(R.id.notifyButton);

        ImageView hamburgerIcon = (ImageView) v.findViewById(R.id.hamburgerIcon);
        ImageView waterIcon = (ImageView) v.findViewById(R.id.waterIcon);
        ImageView weightIcon = (ImageView) v.findViewById(R.id.weightIcon);

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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    
}

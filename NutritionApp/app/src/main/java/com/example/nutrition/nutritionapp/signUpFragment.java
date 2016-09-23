package com.example.nutrition.nutritionapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class signUpFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private CheckableImageView clickedImage;
    private int clickedImagePosition = -1;
    private int _day;
    private int _month;
    private int _birthYear;
    EditText dobInput;

    private String date;
    private int age = 0;
    private String username;
    private String password;
    private String name;
    private boolean gender = true;

    public signUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        updateDisplay();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Get references
        Button nextButton = (Button) v.findViewById(R.id.nextButton);
        nextButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        dobInput = (EditText) v.findViewById(R.id.dobInput);
        final GridView gridView = (GridView) v.findViewById(R.id.gridview);


        // Set ImageAdapter to the GridView
        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        gridView.setAdapter(imageAdapter);

        // Use the custom class CheckableImageView to toggle radio-button functionality
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               CheckableImageView imageView = (CheckableImageView) gridView.getChildAt(position);
                imageView.toggle(clickedImage, clickedImagePosition, position);
                clickedImage = imageView;
                clickedImagePosition = position;
            }
            });

        // set dob edit text to be clickable
        dobInput.setFocusable(false);
        dobInput.setClickable(true);
        // When the editText is clicked, launch the Calendar picker
        dobInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), signUpFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        // User information

        // Name
        EditText nameInput = (EditText) v.findViewById(R.id.nameInput);
        name = nameInput.getText().toString();

        // Username
        EditText usernameInput = (EditText) v.findViewById(R.id.usernameInput);
        username = usernameInput.getText().toString();

        // Password
        EditText passInput = (EditText) v.findViewById(R.id.passwordInput);
        password = passInput.getText().toString();


        // Gender
        RadioButton maleButton = (RadioButton) v.findViewById(R.id.maleButton);

        if (maleButton.isChecked()) {
            gender = true;
        } else {
            gender = false;
        }


        // Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new goalInformationFragment();
                replaceFragment(fragment);
            }
        });

        return v;
    }

    // Append the text from the date picker to the editText
    private void updateDisplay() {
        date = new StringBuilder()
                .append(_month + 1).append("/").append(_day).append("/").append(_birthYear).append(" ").toString();

        dobInput.setText(date);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


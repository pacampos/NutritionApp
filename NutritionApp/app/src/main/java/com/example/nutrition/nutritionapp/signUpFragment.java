package com.example.nutrition.nutritionapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;


public class signUpFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private CheckableImageView clickedImage;
    private int _day;
    private int _month;
    private int _birthYear;
    EditText dobInput;

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

        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               CheckableImageView imageView = (CheckableImageView) gridView.getChildAt(position);
                imageView.toggle(clickedImage);
                clickedImage = imageView;


            }
            });
        // set dob edit text to be clickable
        dobInput.setFocusable(false);
        dobInput.setClickable(true);

        dobInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), signUpFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        return v;
    }
    private void updateDisplay() {
        dobInput.setText(new StringBuilder()
        .append(_month + 1).append("/").append(_day).append("/").append(_birthYear).append(" "));

    }

}

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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class signUpFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static String EMAIL="com.example.nutritionapp.username";
    public static String PASSWORD="com.example.nutritionapp.password";
    public static String NAME = "com.example.nutritionapp.name";
    public static String GENDER = "com.example.nutritionapp.gender";
    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";
    public static String BIRTH_DATE = "com.exam`ple.nutritionapp.birth_date";
    public static String BIRTH_MONTH = "com.example.nutritionapp.birth_month";
    public static String BIRTH_YEAR = "com.example.nutritionapp.birth_year";
    public static String AGE = "com.example.nutritionapp.age";

    private CheckableImageView clickedImage;
    private int clickedImagePosition = -1;
    private int _day;
    private int _month;
    private int _birthYear;
    EditText dobInput;

    private String date;
    private int age = 0;
    private String email;
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
        final EditText nameInput = (EditText) v.findViewById(R.id.nameInput);

        // Username
        final EditText usernameInput = (EditText) v.findViewById(R.id.usernameInput);


        // Password
        final EditText passInput = (EditText) v.findViewById(R.id.passwordInput);


        // Gender
        final RadioButton maleButton = (RadioButton) v.findViewById(R.id.maleButton);
        maleButton.setChecked(true);


        // Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                email = usernameInput.getText().toString();
                password = passInput.getText().toString();
                if (maleButton.isChecked()) {
                    gender = true;
                } else {
                    gender = false;
                }
                age = getAge(_birthYear, _month, _day);
                if(name.length() == 0 || email.length() == 0 || password.length() == 0 || dobInput.length() == 0 || clickedImagePosition == -1) {
                    Toast.makeText(getActivity(),"Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Fragment fragment = new goalInformationFragment();
                    Bundle data= new Bundle();
                    data.putString(EMAIL,email);
                    data.putString(PASSWORD, password);
                    data.putString(NAME, name);
                    data.putBoolean(GENDER,gender);
                    data.putDouble(IMAGE_POS, clickedImagePosition);
                    data.putDouble(BIRTH_DATE,_day);
                    data.putDouble(BIRTH_MONTH, _month);
                    data.putDouble(BIRTH_YEAR, _birthYear);
                    data.putDouble(AGE, age);
                    fragment.setArguments(data);
                    replaceFragment(fragment);
                }
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
    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);

        return ageInt;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


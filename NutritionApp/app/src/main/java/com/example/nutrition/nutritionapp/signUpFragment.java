package com.example.nutrition.nutritionapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signUpFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static String IS_NEW_ACCOUNT = "com.example.nutritionapp.is_new_account";

    public static String EMAIL = "com.example.nutritionapp.username";
    public static String PASSWORD = "com.example.nutritionapp.password";
    public static String NAME = "com.example.nutritionapp.name";
    public static String GENDER = "com.example.nutritionapp.gender";
    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";
    public static String BIRTH_DATE = "com.exam`ple.nutritionapp.birth_date";
    public static String BIRTH_MONTH = "com.example.nutritionapp.birth_month";
    public static String BIRTH_YEAR = "com.example.nutritionapp.birth_year";
    public static String AGE = "com.example.nutritionapp.age";
    public static String METRIC = "com.example.nutritionapp.metric";
    EditText dobInput;
    private CheckableImageView clickedImage;
    private int clickedImagePosition = -1;
    private int _day;
    private int _month;
    private int _birthYear;
    private String date;
    private int age = 0;
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private boolean isImperial = false;
    private boolean gender = true;
    private boolean isNewAccount = true;

    signUpFragment.ReplaceFragmentInterface mReplaceFragment;
    
    public static signUpFragment newInstance(boolean isNewAccount) {

        Bundle args = new Bundle();
        args.putBoolean(IS_NEW_ACCOUNT, isNewAccount);
        signUpFragment fragment = new signUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isNewAccount = bundle.getBoolean(IS_NEW_ACCOUNT, true);
        }

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
                dobInput.setError(null);
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), signUpFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        if(!isNewAccount){
            ((LinearLayout) v.findViewById(R.id.signup_email_container)).setVisibility(View.GONE);
            ((LinearLayout) v.findViewById(R.id.signup_confirm_password_container)).setVisibility(View.GONE);
            ((LinearLayout) v.findViewById(R.id.signup_password_container)).setVisibility(View.GONE);
        }



        // User information

        // Name
        final EditText nameInput = (EditText) v.findViewById(R.id.nameInput);


        // Username
        final EditText usernameInput = (EditText) v.findViewById(R.id.usernameInput);

        // Password
        final EditText passInput = (EditText) v.findViewById(R.id.passwordInput);

        final EditText confirmPassInput = (EditText) v.findViewById(R.id.confirmPasswordEditText);


        // Gender
        final RadioButton maleButton = (RadioButton) v.findViewById(R.id.maleButton);
        maleButton.setChecked(true);

        // Switch
        Switch unitSwitch = (Switch) v.findViewById(R.id.unitSwitch);
        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isImperial =isChecked;
            }
        });


        if(isNewAccount){
            // Next button
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = nameInput.getText().toString();
                    email = usernameInput.getText().toString();
                    password = passInput.getText().toString();
                    confirmPassword = confirmPassInput.getText().toString();
                    if (maleButton.isChecked()) {
                        gender = true;
                    } else {
                        gender = false;
                    }

                    age = getAge(_birthYear, _month, _day);
                    if (name.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0|| dobInput.length() == 0 || clickedImagePosition == -1) {
                        Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }

//                else if(!isValidDate(date)){
//                    Toast.makeText(getActivity(), "Invalid date format", Toast.LENGTH_SHORT).show();
//                }
                /* check if email is valid */
                    else if(!isValidEmail(email)){
//                    Toast.makeText(getActivity(), "Please type in a valid email", Toast.LENGTH_SHORT).show();
                        usernameInput.setError("Invalid format");

                    }

                /* check if password is secure */
                    else if(!isValidPassword(password)){
//                    Toast.makeText(getActivity(), "Password must be a minimum 8 characters with at" +
//                            " least 1 Uppercase Alphabet, 1 Lowercase Alphabet and 1 Number", Toast.LENGTH_SHORT).show();
                        passInput.setError("Password must be a minimum 8 characters with at least 1 Uppercase Alphabet, 1 Lowercase Alphabet and 1 Number");
                    }

                    else if(!password.equals(confirmPassword)){
//                    Toast.makeText(getActivity(), "Your passwords do not match. Please try again.",Toast.LENGTH_SHORT).show();
                        confirmPassInput.setError("Passwords do not match. Please try again.");
                    }
                    else {
                        Fragment fragment = new goalInformationFragment();
                        Bundle data = new Bundle();
                        data.putBoolean(IS_NEW_ACCOUNT, isNewAccount);
                        data.putString(EMAIL, email);
                        data.putString(PASSWORD, password);
                        data.putString(NAME, name);
                        data.putBoolean(GENDER, gender);
                        data.putDouble(IMAGE_POS, clickedImagePosition);
                        data.putDouble(BIRTH_DATE, _day);
                        data.putDouble(BIRTH_MONTH, _month);
                        data.putDouble(BIRTH_YEAR, _birthYear);
                        data.putDouble(AGE, age);
                        data.putBoolean(METRIC, isImperial);
                        fragment.setArguments(data);
                        mReplaceFragment.replaceFragment(fragment);
                    }
                }
            });
        }

        else{
            // Next button
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = nameInput.getText().toString();
                    if (maleButton.isChecked()) {
                        gender = true;
                    } else {
                        gender = false;
                    }

                    age = getAge(_birthYear, _month, _day);
                    if (name.length() == 0 ||  dobInput.length() == 0 || clickedImagePosition == -1) {
                        Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Fragment fragment = new goalInformationFragment();
                        Bundle data = new Bundle();
                        data.putBoolean(IS_NEW_ACCOUNT, isNewAccount);
                        data.putString(NAME, name);
                        data.putBoolean(GENDER, gender);
                        data.putDouble(IMAGE_POS, clickedImagePosition);
                        data.putDouble(BIRTH_DATE, _day);
                        data.putDouble(BIRTH_MONTH, _month);
                        data.putDouble(BIRTH_YEAR, _birthYear);
                        data.putDouble(AGE, age);
                        data.putBoolean(METRIC, isImperial);
                        fragment.setArguments(data);
                        mReplaceFragment.replaceFragment(fragment);
                    }
                }
            });
        }


        return v;
    }

    // Append the text from the date picker to the editText
    private void updateDisplay() {
        date = new StringBuilder()
                .append(_month + 1).append("/").append(_day).append("/").append(_birthYear).append(" ").toString();

        dobInput.setText(date);

    }

    private int getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean isValidDate(final String date){
        Pattern pattern;
        Matcher matcher;
        final String DATE_PATTERN = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(date);

        return matcher.matches();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mReplaceFragment = (signUpFragment.ReplaceFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface ReplaceFragmentInterface{
        public void replaceFragment(Fragment fragment);
    }
}


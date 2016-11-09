package com.fearnot.snapp.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.R;
import com.fearnot.snapp.Views.CheckableImageView;

import static android.R.attr.defaultValue;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.fearnot.snapp.RegexHelper.isNumeric;

public class goalInformationFragment extends Fragment {
    public static String WEIGHT = "com.example.nutritionapp.weight";
    public static String HEIGHT = "com.example.nutritionapp.height";
    public static String IMPERIAL_HEIGHT_INCHES = "com.example.nutritionapp.height_inches";
    public static String IMPERIAL_HEIGHT_FEET = "com.example.nutritionapp.height_feet";
    public static String GOAL = "com.example.nutritionapp.goal";
    public static String ACTIVITY = "com.example.nutritionapp.activity";
    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";
    ReplaceFragmentInterface replaceFragmentInterface;
    private String weight;
    private String height;
    private String heightInches;
    private String heightFeet;
    private String goalWeight;
    private TextView notSureActivityLevel;
    private int activityLevelFactor;
    private Bundle bundle;
    private PopupWindow mPopupWindow;
    private TextView tv;
    private FrameLayout mRelativeLayout;

    public goalInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = this.getArguments();

        boolean isImperial = bundle.getBoolean(signUpFragment.METRIC);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal_information, container, false);

        // get references
        notSureActivityLevel= (TextView) v.findViewById(R.id.notSureLevel);
        final EditText weightInput = (EditText) v.findViewById(R.id.weightInput);
        final TextView weightTextView = (TextView) v.findViewById(R.id.currentWeightLabel);
        final EditText heightInput = (EditText) v.findViewById(R.id.heightInput);
        final EditText heightInputFeet = (EditText) v.findViewById(R.id.heightFeetImperialEditText);
        final EditText heightInputInches = (EditText) v.findViewById(R.id.heightInchesImperialEditText);
        final EditText goalWeightInput = (EditText) v.findViewById(R.id.goalWeightInput);
        final TextView goalWeightTextView = (TextView) v.findViewById(R.id.goalWeightLabel);
        LinearLayout heightImperialLayout = (LinearLayout) v.findViewById(R.id.imperialHeightLayout);
        LinearLayout heightMetricLayout = (LinearLayout) v.findViewById(R.id.metricHeightLayout);
        mRelativeLayout = (FrameLayout) v.findViewById(R.id.rl1); // TODO this is null - instantiate correctly

        notSureActivityLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView(0);
                notSureActivityLevel.setClickable(false);
            }
        });

        if (isImperial == true) {
            weightTextView.setText(R.string.weight_text_imperial);
            goalWeightTextView.setText(R.string.goalWeight_text_imperial);
            heightImperialLayout.setVisibility(View.VISIBLE);
            heightMetricLayout.setVisibility(View.GONE);
        }

        ImageView icon = (ImageView) v.findViewById(R.id.iconImage);

        double imagePos = bundle.getDouble(IMAGE_POS, defaultValue);

        // get image from array
        icon.setImageResource(CheckableImageView.mOriginalIds[(int) imagePos]);

        Spinner activityLevelSpinner = (Spinner) v.findViewById(R.id.spinner);
        activityLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityLevelFactor = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button continueButton = (Button) v.findViewById(R.id.continueButton);
        if (isImperial) {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weight = weightInput.getText().toString();
//                    height = heightInput.getText().toString();
                    heightInches = heightInputInches.getText().toString();
                    heightFeet = heightInputFeet.getText().toString();
                    goalWeight = goalWeightInput.getText().toString();

                    if (weight.length() == 0 || heightInches.length() == 0 || heightFeet.length() == 0 || goalWeight.length() == 0) {
                        Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else if (!isNumeric(weight)) {
                        weightInput.setError("Please enter a valid weight.");
                    } else if (!isNumeric(heightInches) && !isNumeric(heightFeet)) {
                        heightInput.setError("Please enter a valid height.");
                    } else if (!isNumeric(goalWeight)) {
                        goalWeightInput.setError("Please enter a valid weight.");
                    } else {
                        Fragment fragment = new MeasurementFragment();
                        bundle.putDouble(WEIGHT, Double.parseDouble(weight));
                        bundle.putDouble(IMPERIAL_HEIGHT_INCHES, Double.parseDouble(heightInches));
                        bundle.putDouble(IMPERIAL_HEIGHT_FEET, Double.parseDouble(heightFeet));
                        bundle.putDouble(GOAL, Double.parseDouble(goalWeight));
                        bundle.putDouble(ACTIVITY, activityLevelFactor);
                        fragment.setArguments(bundle);
                        replaceFragmentInterface.replaceFragment(fragment);
                    }
                }
            });
        } else {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weight = weightInput.getText().toString();
                    height = heightInput.getText().toString();
                    goalWeight = goalWeightInput.getText().toString();

                    if (weight.length() == 0 || height.length() == 0 || goalWeight.length() == 0) {
                        Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else if (!isNumeric(weight)) {
                        weightInput.setError("Please enter a valid weight.");
                    } else if (!isNumeric(height)) {
                        heightInput.setError("Please enter a valid height.");
                    } else if (!isNumeric(goalWeight)) {
                        goalWeightInput.setError("Please enter a valid weight.");
                    } else {
                        Fragment fragment = new MeasurementFragment();
                        bundle.putDouble(WEIGHT, Double.parseDouble(weight));
                        bundle.putDouble(HEIGHT, Double.parseDouble(height));
                        bundle.putDouble(GOAL, Double.parseDouble(goalWeight));
                        bundle.putDouble(ACTIVITY, activityLevelFactor);
                        fragment.setArguments(bundle);
                        replaceFragmentInterface.replaceFragment(fragment);
                    }
                }
            });
        }

        return v;
    }
    private void createView(int type) {

        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.custom_layout, null);

        tv = (TextView) customView.findViewById(R.id.tv);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
                notSureActivityLevel.setClickable(true);
            }
        });

        if (type == 0) {
            tv.setText("Insert text here on what each level is.");
        }
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            replaceFragmentInterface = (ReplaceFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}

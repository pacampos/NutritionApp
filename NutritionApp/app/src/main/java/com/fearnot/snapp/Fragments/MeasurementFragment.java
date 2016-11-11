package com.fearnot.snapp.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fearnot.snapp.Activities.ActivityHome;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;
import com.fearnot.snapp.Receiver;
import com.fearnot.snapp.Views.CheckableImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import static android.R.attr.defaultValue;


public class MeasurementFragment extends Fragment {
    public static String TAG = "MeasurementFragment";
    public static String ARM = "com.example.nutritionapp.arm";
    public static String WAIST = "com.example.nutritionapp.waist";
    public static String THIGH = "com.example.nutritionapp.thigh";
    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";
    NutritionSingleton singleton;
    private String arm;
    private String waist;
    private String thigh;
    private Bundle bundle;

    private EditText armInput;
    private TextView armTextView;
    private EditText waistInput;
    private TextView waistTextView;
    private EditText thighInput;
    private TextView thighTextView;
    private Button goToButton;
    private ProgressDialog progress;

    private ReplaceFragmentInterface replaceFragmentInterface;

    public MeasurementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = this.getArguments();

        final boolean isImperial = bundle.getBoolean(signUpFragment.METRIC);
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_measurement, container, false);

        // Get References
        goToButton = (Button) v.findViewById(R.id.goToButton);
        goToButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        armInput = (EditText) v.findViewById(R.id.armInput);
        armTextView = (TextView) v.findViewById(R.id.armTextView);
        waistInput = (EditText) v.findViewById(R.id.waistInput);
        waistTextView = (TextView) v.findViewById(R.id.waistTextView);
        thighInput = (EditText) v.findViewById(R.id.thighInput);
        thighTextView = (TextView) v.findViewById(R.id.thighTextView);

        if (isImperial) {
            armTextView.setText(R.string.arm_text_imperial);
            waistTextView.setText(R.string.waist_text_imperial);
            thighTextView.setText(R.string.thigh_text_imperial);
        }

        ImageView icon = (ImageView) v.findViewById(R.id.secondPine);

        double imagePos = bundle.getDouble(IMAGE_POS, defaultValue);

        // get image from array
        icon.setImageResource(CheckableImageView.mOriginalIds[(int) imagePos]);

        singleton = NutritionSingleton.getInstance();

        goToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arm = armInput.getText().toString();
                waist = waistInput.getText().toString();
                thigh = thighInput.getText().toString();

                double armMeasure = -1.0;
                double waistMeasure = -1.0;
                double thighMeasure = -1.0;
                if (arm.length() > 0) {
                    armMeasure = Double.parseDouble(arm);
                }

                if (waist.length() > 0) {
                    waistMeasure = Double.parseDouble(waist);
                }

                if (thigh.length() > 0) {
                    thighMeasure = Double.parseDouble(thigh);
                }

                bundle.putDouble(ARM, armMeasure);
                bundle.putDouble(WAIST, waistMeasure);
                bundle.putDouble(THIGH, thighMeasure);


                String email = bundle.getString(signUpFragment.EMAIL);
                String password = bundle.getString(signUpFragment.PASSWORD);

                boolean isNewAccount = bundle.getBoolean(signUpFragment.IS_NEW_ACCOUNT);
                if (isNewAccount) {
                    finishSignup(email, password, bundle);
                } else {
                    if (isImperial) {
                        NutritionSingleton.getInstance().CreateNewProfile(bundle.getDouble(signUpFragment.IMAGE_POS),
                                bundle.getString(signUpFragment.NAME),
                                bundle.getDouble(signUpFragment.AGE),
                                bundle.getDouble(goalInformationFragment.IMPERIAL_HEIGHT_INCHES),
                                bundle.getDouble(goalInformationFragment.IMPERIAL_HEIGHT_FEET),
                                bundle.getBoolean(signUpFragment.GENDER),
                                bundle.getDouble(goalInformationFragment.WEIGHT),
                                bundle.getDouble(goalInformationFragment.GOAL),
                                bundle.getDouble(signUpFragment.BIRTH_DATE),
                                bundle.getDouble(signUpFragment.BIRTH_MONTH),
                                bundle.getDouble(signUpFragment.BIRTH_YEAR),
                                bundle.getDouble(MeasurementFragment.WAIST),
                                bundle.getDouble(MeasurementFragment.THIGH),
                                bundle.getDouble(MeasurementFragment.ARM),
                                bundle.getDouble(goalInformationFragment.ACTIVITY),
                                bundle.getBoolean(signUpFragment.METRIC));

                        Calendar sevendayalarm = Calendar.getInstance();
                        sevendayalarm.add(Calendar.HOUR_OF_DAY, 20);
                        Intent intent = new Intent(getContext(), Receiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
                        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                        am.setRepeating(AlarmManager.RTC_WAKEUP, sevendayalarm.getTimeInMillis(), 1000 * 60 * 24 * 10, pendingIntent);
                        replaceFragmentInterface.replaceFragment(new SwitchUserFragment());
                    } else {
                        NutritionSingleton.getInstance().CreateNewProfile(bundle.getDouble(signUpFragment.IMAGE_POS),
                                bundle.getString(signUpFragment.NAME),
                                bundle.getDouble(signUpFragment.AGE),
                                bundle.getDouble(goalInformationFragment.HEIGHT),
                                bundle.getBoolean(signUpFragment.GENDER),
                                bundle.getDouble(goalInformationFragment.WEIGHT),
                                bundle.getDouble(goalInformationFragment.GOAL),
                                bundle.getDouble(signUpFragment.BIRTH_DATE),
                                bundle.getDouble(signUpFragment.BIRTH_MONTH),
                                bundle.getDouble(signUpFragment.BIRTH_YEAR),
                                bundle.getDouble(MeasurementFragment.WAIST),
                                bundle.getDouble(MeasurementFragment.THIGH),
                                bundle.getDouble(MeasurementFragment.ARM),
                                bundle.getDouble(goalInformationFragment.ACTIVITY),
                                bundle.getBoolean(signUpFragment.METRIC));

                        Calendar sevendayalarm = Calendar.getInstance();
                        sevendayalarm.add(Calendar.HOUR_OF_DAY, 20);
                        Intent intent = new Intent(getContext(), Receiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
                        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                        am.setRepeating(AlarmManager.RTC_WAKEUP, sevendayalarm.getTimeInMillis(), 1000 * 60 * 24 * 10, pendingIntent);
                        replaceFragmentInterface.replaceFragment(new SwitchUserFragment());
                    }


                }


            }
        });
        return v;
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

    public void finishSignup(String email, String password, final Bundle bundle) {
        progress = new ProgressDialog(getContext());
        progress.setMessage("Finishing signup...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.hide();
                                progress.dismiss();
                            }
                        });
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Could not sign up",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Signed In", Toast.LENGTH_SHORT).show();
                            NutritionSingleton.getInstance().CreateNewAccount(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            if (bundle.getBoolean(signUpFragment.METRIC)) {
                                NutritionSingleton.getInstance().CreateNewProfile(bundle.getDouble(signUpFragment.IMAGE_POS),
                                        bundle.getString(signUpFragment.NAME),
                                        bundle.getDouble(signUpFragment.AGE),
                                        bundle.getDouble(goalInformationFragment.IMPERIAL_HEIGHT_INCHES),
                                        bundle.getDouble(goalInformationFragment.IMPERIAL_HEIGHT_FEET),
                                        bundle.getBoolean(signUpFragment.GENDER),
                                        bundle.getDouble(goalInformationFragment.WEIGHT),
                                        bundle.getDouble(goalInformationFragment.GOAL),
                                        bundle.getDouble(signUpFragment.BIRTH_DATE),
                                        bundle.getDouble(signUpFragment.BIRTH_MONTH),
                                        bundle.getDouble(signUpFragment.BIRTH_YEAR),
                                        bundle.getDouble(MeasurementFragment.WAIST),
                                        bundle.getDouble(MeasurementFragment.THIGH),
                                        bundle.getDouble(MeasurementFragment.ARM),
                                        bundle.getDouble(goalInformationFragment.ACTIVITY),
                                        bundle.getBoolean(signUpFragment.METRIC));

                                NutritionSingleton.getInstance().switchProfiles(0);
                            } else {
                                NutritionSingleton.getInstance().CreateNewProfile(bundle.getDouble(signUpFragment.IMAGE_POS),
                                        bundle.getString(signUpFragment.NAME),
                                        bundle.getDouble(signUpFragment.AGE),
                                        bundle.getDouble(goalInformationFragment.HEIGHT),
                                        bundle.getBoolean(signUpFragment.GENDER),
                                        bundle.getDouble(goalInformationFragment.WEIGHT),
                                        bundle.getDouble(goalInformationFragment.GOAL), bundle.getDouble(signUpFragment.BIRTH_DATE),
                                        bundle.getDouble(signUpFragment.BIRTH_MONTH),
                                        bundle.getDouble(signUpFragment.BIRTH_YEAR),
                                        bundle.getDouble(MeasurementFragment.WAIST),
                                        bundle.getDouble(MeasurementFragment.THIGH),
                                        bundle.getDouble(MeasurementFragment.ARM),
                                        bundle.getDouble(goalInformationFragment.ACTIVITY),
                                        bundle.getBoolean(signUpFragment.METRIC));
                                NutritionSingleton.getInstance().switchProfiles(0);
                            }

                            Calendar sevendayalarm = Calendar.getInstance();
                            sevendayalarm.add(Calendar.HOUR_OF_DAY, 20);
                            Intent intent = new Intent(getContext(), Receiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

                            AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                            am.setRepeating(AlarmManager.RTC_WAKEUP, sevendayalarm.getTimeInMillis(), 1000 * 60 * 24 * 10, pendingIntent);
                            startActivity(new Intent(getActivity(), ActivityHome.class));
                        }

                    }
                });
    }
}

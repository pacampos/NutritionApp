package com.example.nutrition.nutritionapp;

import com.example.nutrition.nutritionapp.Model.AccountModel;
import com.example.nutrition.nutritionapp.Model.DayModel;
import com.example.nutrition.nutritionapp.Model.ExerciseModel;
import com.example.nutrition.nutritionapp.Model.FoodModel;
import com.example.nutrition.nutritionapp.Model.ProfileModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class NutritionSingleton {
    private static NutritionSingleton mInstance = null;
    private static String USERS_CHILD = "users";
    private String currUser = null;
    private AccountModel currAccount;
    private ProfileModel currProfile;
    private DayModel currDay;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mUser;

    private NutritionSingleton() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static NutritionSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new NutritionSingleton();
        }
        return mInstance;
    }

    public FirebaseUser GetUser() {
        return mUser;
    }

    void SetUser(FirebaseUser user) {
        mUser = user;
        currUser = mUser.getUid();
        final DatabaseReference ref = mFirebaseDatabaseReference.child(USERS_CHILD).child(mUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    name = snapshot.getKey();
                    final DatabaseReference profileRef = ref.child(name);
                    profileRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            currProfile = dataSnapshot.getValue(ProfileModel.class);
                            /*
                                we use java date to find out what day it is,
                                if it's a day not in the database, we create a new date and add that to the profile
                                else, we create a new day, to begin appending water, exercise, and food to it
                             */
                            currDay = currProfile.getDays().get(generateCurrDayString());
                            if(currDay == null){
                                currDay = new DayModel();
                                currProfile.getDays().put(generateCurrDayString(), currDay);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * @param username Should only be called during the signup process, it creates a new account that can contain
     *                 several users
     */
    public void CreateNewAccount(String username) {
        // TODO: figure out if we actually need to keep track of an account model or if the database does enough of that on its own
        AccountModel newAccount = new AccountModel(username);
        currAccount = newAccount;
        currUser = username;
        mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser);
    }

    public void CreateNewProfile(double imagePos, String name, double age, double heightCentimeters, boolean gender,
                                 double currWeightKilos, double goalWeightKilos, double dayBirth, double monthBirth, double yearBirth,
                                 double waistMeasureCentimeter, double thighMeasureCentimeter,
                                 double armMeasureCentimeter, double activityLevel, boolean isImperial) {
        currProfile = new ProfileModel(imagePos, name, age, heightCentimeters, gender, currWeightKilos,
                goalWeightKilos, dayBirth, monthBirth, yearBirth, waistMeasureCentimeter,
                thighMeasureCentimeter, armMeasureCentimeter, activityLevel, isImperial);

        currAccount.addUserProfile(currProfile);


        if (currUser != null) {
            mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser).child(name).setValue(currProfile);
        }
    }

    public void CreateNewProfile(double imagePos, String name, double age, double heightInchesPart,
                                 double heightFeetPart, boolean gender, double currWeightPounds,
                                 double goalWeightPounds, double dayBirth, double monthBirth, double yearBirth, double waistMeasureInches,
                                 double thighMeasureInches, double armMeasureInches, double activityLevel, boolean isImperial) {

        currProfile = new ProfileModel(imagePos, name, age, heightInchesPart, heightFeetPart, gender, currWeightPounds,
                goalWeightPounds, dayBirth, monthBirth, yearBirth, waistMeasureInches, thighMeasureInches, armMeasureInches, activityLevel, isImperial);

        currAccount.addUserProfile(currProfile);
        currDay = currProfile.getDays().get(generateCurrDayString());

        if (currUser != null) {
            mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser).child(name).setValue(currProfile);
        }
    }

    public AccountModel getCurrAccount() {
        return currAccount;
    }

    public void setCurrAccount(AccountModel currAccount) {
        this.currAccount = currAccount;
    }

    public ProfileModel getCurrProfile() {
        return currProfile;
    }

    public void setCurrProfile(ProfileModel currProfile) {
        this.currProfile = currProfile;
    }

    public DayModel getCurrDay() {
        return currDay;
    }

    public void setCurrDay(DayModel currDay) {
        this.currDay = currDay;
    }

    public String generateCurrDayString(){
        Date today=new Date();
        SimpleDateFormat format= new SimpleDateFormat("MMddyyyy");
        String date = format.format(today);
        return date;
    }

    public void addDay(DayModel day){

    }

    public void addExercise(ExerciseModel exercise){
        currDay.addExercise(exercise);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put(String.valueOf(currDay.getExercises().size()-1),exercise);
       mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).child("exercises").updateChildren(updateChildren);
    }

    public void addFood(FoodModel food){

    }

    public void updateWater(double water){
        currDay.addWaterAmount(water);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("waterAmountDrank", currDay.getWaterAmountDrank()+water);
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }
}

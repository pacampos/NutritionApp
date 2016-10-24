package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.content.Intent;

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
import java.util.ArrayList;
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

    void SetUser(FirebaseUser user, final Intent intent, final Context context) {
        mUser = user;
        currUser = mUser.getUid();
        final DatabaseReference ref = mFirebaseDatabaseReference.child(USERS_CHILD).child(mUser.getUid());

        ValueEventListener valueEventListener =new ValueEventListener() {
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
                                NutritionSingleton.getInstance().addDay(currDay);

                            }


                            context.startActivity(intent);
                            profileRef.removeEventListener(this);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(valueEventListener);

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
        currProfile.getDays().put(generateCurrDayString(), currDay);
        mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).setValue(currDay);
    }

    public void addExercise(ExerciseModel exercise){
        if(currDay.getExercises() == null || currDay.getExercises().isEmpty()){
            currDay.setExercises(new ArrayList<ExerciseModel>());
        }

        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put(String.valueOf(currDay.getExercises().size()),exercise);
        currDay.addExercise(exercise);
       mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).child("exercises").updateChildren(updateChildren);
    }

    public void addFood(FoodModel food){
        if(currDay.getFoods() == null){
            currDay.setFoods(new ArrayList<FoodModel>());
        }
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put(String.valueOf(currDay.getFoods().size()),food);
        currDay.addFood(food);
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).child("foods").updateChildren(updateChildren);
    }

    public void updateWater(double water){
        currDay.addWaterAmount(water);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("waterAmountDrank", currDay.getWaterAmountDrank());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }

    public void updateGrains(double grains){
        currDay.addServingsGrains(grains);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("servingsGrains", currDay.getServingsGrains());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }

    public void updateVeggies(double veggie){
        currDay.addServingsVeggie(veggie);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("servingsVeggie", currDay.getServingsVeggie());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }

    public void updateFruits(double fruits){
        currDay.addServingsFruit(fruits);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("servingsFruit", currDay.getServingsFruit());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }

    public void updateMeat(double meat){
        currDay.addServingsMeat(meat);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("servingsMeat", currDay.getServingsMeat());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }

    public void updateDairy(double dairy){
        currDay.addServingsDairy(dairy);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("servingsDairy", currDay.getServingsDairy());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }

    public void updateCurrWeight(double weight){
        if(currProfile.getIsImperial()){
            currProfile.setCurrWeightPounds(weight);
            Map<String,Object> updateChildren=new HashMap<>();
            updateChildren.put("currWeightPounds", currProfile.getCurrWeightPounds());
            mFirebaseDatabaseReference.child(USERS_CHILD).
                    child(currUser).child(currProfile.getName()).updateChildren(updateChildren);
        }

        else{
            currProfile.setCurrWeightKilos(weight);
            Map<String,Object> updateChildren=new HashMap<>();
            updateChildren.put("currWeightKilos", currProfile.getCurrWeightKilos());
            mFirebaseDatabaseReference.child(USERS_CHILD).
                    child(currUser).child(currProfile.getName()).updateChildren(updateChildren);
        }
    }

    public void updateGoalWeight(double weight){
        if(currProfile.getIsImperial()){
            currProfile.setGoalWeightPounds(weight);
            Map<String,Object> updateChildren=new HashMap<>();
            updateChildren.put("goalWeightPounds", currProfile.getGoalWeightPounds());
            mFirebaseDatabaseReference.child(USERS_CHILD).
                    child(currUser).child(currProfile.getName()).updateChildren(updateChildren);
        }

        else{
            currProfile.setGoalWeightKilos(weight);
            Map<String,Object> updateChildren=new HashMap<>();
            updateChildren.put("goalWeightKilos", currProfile.getGoalWeightKilos());
            mFirebaseDatabaseReference.child(USERS_CHILD).
                    child(currUser).child(currProfile.getName()).updateChildren(updateChildren);
        }
    }

    public void updateCurrHeight(double height){
        if(currProfile.getIsImperial()){
            currProfile.setHeightCentimeters(height);
            Map<String,Object> updateChildren=new HashMap<>();
            updateChildren.put("heightInchesPart", currProfile.getHeightInchesPart());
            mFirebaseDatabaseReference.child(USERS_CHILD).
                    child(currUser).child(currProfile.getName()).updateChildren(updateChildren);
        }

        else{
            currProfile.setHeightCentimeters(height);
            Map<String,Object> updateChildren=new HashMap<>();
            updateChildren.put("heightCentimeters", currProfile.getHeightCentimeters());
            mFirebaseDatabaseReference.child(USERS_CHILD).
                    child(currUser).child(currProfile.getName()).updateChildren(updateChildren);
        }
    }
}

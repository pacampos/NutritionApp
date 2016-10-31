package com.example.nutrition.nutritionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class NutritionSingleton {
    private static NutritionSingleton mInstance = null;
    private static String USERS_CHILD = "users";
    private String currUser = null;
    private ProfileModel currProfile;
    private ArrayList<ProfileModel>  profiles;
    private DayModel currDay;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mUser;

    private NutritionSingleton() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        profiles = new ArrayList<>();
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

    void SetUser(FirebaseUser user, final Context context) {
        final ProgressDialog progress=new ProgressDialog(context);
        progress.setMessage("Retrieving Profile");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        /* set the firebase user given to use by the auth object */
        mUser = user;
        /* use this user's uid as the unique recognizer for the user */
        currUser = mUser.getUid();

        /* we get the children of current user, which are all the profiles */
        final DatabaseReference ref = mFirebaseDatabaseReference.child(USERS_CHILD).child(mUser.getUid());

        /* once we have the reference to this point in the database, we get access to all the children of the user */
        ValueEventListener valueEventListener =new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = null;
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    name = snapshot.getKey();
                    final DatabaseReference profileRef = ref.child(name);
                    profileRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            /* add all profiles to the profiles array */
                           profiles.add(dataSnapshot.getValue(ProfileModel.class));
                            ProfileModel currModel = profiles.get(profiles.size()-1);
                            currModel.id = profiles.size()-1;
                            long childrenCount = dataSnapshot.getChildrenCount();
                            int profileSize = profiles.size();

                            if(childrenCount/23 == profileSize){
                                progress.hide();
                                /* start the fragment that switches profiles */

                                AppCompatActivity beforeLogin = (AppCompatActivity) context;
                                //get manager
                                FragmentManager fm = beforeLogin.getSupportFragmentManager();

                                SwitchUserFragment f = new SwitchUserFragment(); // instantiate switch profile Fragment
                                // create transaction
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.welcome_fragment_container, f);
                                ft.commit();

                                profileRef.removeEventListener(this);
                            }
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
        profiles.add(currProfile);
        currProfile.id = profiles.size()-1;

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
        profiles.add(currProfile);
        currProfile.id = profiles.size()-1;

        if (currUser != null) {
            mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser).child(name).setValue(currProfile);
        }
    }

    /*
        switches profiles by getting the position of the profiles in the listview the user has clicked
     */
    public void switchProfiles(int position){
        if(position < profiles.size()){
            currProfile = profiles.get(position);
            currDay = currProfile.getDays().get(generateCurrDayString());
            if(currDay == null){
                currDay = new DayModel();
                NutritionSingleton.getInstance().addDay(currDay);
            }
        }
    }

    public ProfileModel getCurrProfile() {
        return currProfile;
    }

    public void setCurrProfile(ProfileModel currProfile) {
        this.currProfile = currProfile;
    }

    public ArrayList<ProfileModel> getAllProfiles() {return profiles;}

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
        if(currProfile.getIsImperial()){
            currDay.setCurrentWeight(currProfile.getCurrWeightPounds());
        }

        else{
            currDay.setCurrentWeight(currProfile.getCurrWeightKilos());
        }

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

    public void updateSweet(double sweet){
        currDay.addServingsSweet(sweet);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("servingsSweet", currDay.getServingsSweets());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).child("days").child(generateCurrDayString()).updateChildren(updateChildren);
    }

    public void updateTodayWeight(double weight){
        currDay.setCurrentWeight(weight);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("currentWeight", currDay.getServingsDairy());
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

        updateCurrWeight(weight);
    }

    public void updateCurrHeight(double height){
            currProfile.setHeightCentimeters(height);
            Map<String,Object> updateChildren=new HashMap<>();
            updateChildren.put("heightCentimeters", currProfile.getHeightCentimeters());
            mFirebaseDatabaseReference.child(USERS_CHILD).
                    child(currUser).child(currProfile.getName()).updateChildren(updateChildren);
    }

    public void updateCurrHeight(double heightFeet, double heightInches){
        currProfile.setHeightInchesPart(heightInches);
        Map<String,Object> updateChildren=new HashMap<>();
        updateChildren.put("heightInchesPart", currProfile.getHeightInchesPart());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).updateChildren(updateChildren);

        currProfile.setHeightFeetPart(heightFeet);
        Map<String,Object> updateChildren1=new HashMap<>();
        updateChildren.put("heightFeetPart", currProfile.getHeightFeetPart());
        mFirebaseDatabaseReference.child(USERS_CHILD).
                child(currUser).child(currProfile.getName()).updateChildren(updateChildren1);
    }
}

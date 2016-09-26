package com.example.nutrition.nutritionapp;

import android.accounts.Account;

import com.example.nutrition.nutritionapp.Model.AccountModel;
import com.example.nutrition.nutritionapp.Model.ProfileModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class NutritionSingleton {
    private static NutritionSingleton mInstance = null;
    private static String USERS_CHILD= "users";
    private String currUser= null;


    private AccountModel currAccount;
    private ProfileModel currProfile;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;

    private List<AccountModel> accounts;

    private NutritionSingleton(){
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static NutritionSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new NutritionSingleton();
        }
        return mInstance;
    }

    /**
     *
     * @param username
     * Should only be called during the signup process, it creates a new account that can contain
     * several users
     */
    public void CreateNewAccount(String username){
        AccountModel newAccount= new AccountModel(username);
        currAccount=newAccount;
        currUser=username;

        mFirebaseDatabaseReference.child(USERS_CHILD).push().setValue(currAccount);
    }

    public void CreateNewProfile(double imagePos, String name, double age, double heightCentimeters, boolean gender,
                                 double currWeightKilos, double goalWeightKilos, double dayBirth, double monthBirth, double yearBirth,
                                 double waistMeasureCentimeter , double thighMeasureCentimeter,
                                 double armMeasureCentimeter, double activityLevel){
        currProfile=new ProfileModel(imagePos, name, age, heightCentimeters, gender, currWeightKilos,
                goalWeightKilos, dayBirth, monthBirth, yearBirth, waistMeasureCentimeter,
                thighMeasureCentimeter, armMeasureCentimeter, activityLevel);

        currAccount.addUserProfile(currProfile);

        if(currUser!=null){
            mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser).setValue(currProfile);
//            mFirebaseDatabaseReference.child(currUser).push().setValue(currProfile);
        }
    }

    public void CreateNewProfile(double imagePos, String name, double age, double heightInchesPart,
                                 double heightFeetPart, boolean gender, double currWeightPounds,
                                 double goalWeightPounds,double dayBirth, double monthBirth, double yearBirth, double waistMeasureInches,
                                 double thighMeasureInches, double armMeasureInches, double activityLevel){
        currProfile=new ProfileModel(imagePos, name, age, heightInchesPart, heightFeetPart, gender, currWeightPounds,
                goalWeightPounds, dayBirth, monthBirth, yearBirth, waistMeasureInches, thighMeasureInches, armMeasureInches, activityLevel);

        currAccount.addUserProfile(currProfile);

        if(currUser!=null){
            mFirebaseDatabaseReference.child(currUser).setValue(currProfile);
        }
    }


}

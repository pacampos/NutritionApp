package com.example.nutrition.nutritionapp;

import com.example.nutrition.nutritionapp.Model.AccountModel;
import com.example.nutrition.nutritionapp.Model.ProfileModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class NutritionSingleton {
    private static NutritionSingleton mInstance = null;
    private static String USERS_CHILD= "users";



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



}

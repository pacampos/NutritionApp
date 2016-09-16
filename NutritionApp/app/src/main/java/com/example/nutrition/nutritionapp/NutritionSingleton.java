package com.example.nutrition.nutritionapp;

import com.example.nutrition.nutritionapp.Model.AccountModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class NutritionSingleton {
//    private FirebaseDatabase databaseInstance;
    private static NutritionSingleton mInstance = null;

    private List<AccountModel> accounts;

    private NutritionSingleton(){
        DatabaseReference databaseInstance=FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static NutritionSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new NutritionSingleton();
        }
        return mInstance;
    }

}

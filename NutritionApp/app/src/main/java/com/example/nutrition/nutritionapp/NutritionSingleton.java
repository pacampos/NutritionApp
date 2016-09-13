package com.example.nutrition.nutritionapp;

import com.example.nutrition.nutritionapp.Model.AccountModel;

import java.util.List;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class NutritionSingleton {
    private static NutritionSingleton mInstance = null;

    private List<AccountModel> accounts;

    private NutritionSingleton(){
    }

    public static NutritionSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new NutritionSingleton();
        }
        return mInstance;
    }

}

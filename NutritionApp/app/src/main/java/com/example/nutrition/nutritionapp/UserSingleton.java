package com.example.nutrition.nutritionapp;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class UserSingleton {
    private static UserSingleton mInstance = null;

    private UserSingleton(){
    }

    public static UserSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new UserSingleton();
        }
        return mInstance;
    }

}

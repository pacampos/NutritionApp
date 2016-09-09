package com.example.nutrition.nutritionapp.Model;

/**
 * Created by phoenixcampos01 on 9/8/16.
 */
public class UserModel {
    private String name;
    private int age;
    private int heightFeet;
    private int heightInches;

    private int currWeightPounds;
    private boolean gender;

    /* body measures for use in body fat calculation */
    private int waistMeasure;

    /* only required for women */
    private int wristMeasure;
    private int hipMeasure;
    private int forearmMeasure;

    private int goalWeightPounds;

}

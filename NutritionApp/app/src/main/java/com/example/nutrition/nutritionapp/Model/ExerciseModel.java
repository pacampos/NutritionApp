package com.example.nutrition.nutritionapp.Model;

import com.example.nutrition.nutritionapp.NutritionSingleton;

/**
 * This model describes the calories burned from a single exercise. Each exercise has a specific
 * amount of calories you would burn for a specific amount of time.
 */
public class ExerciseModel {
    private double calories;
    private double minutes;
    private double exerciseType;
    public static final double exerciseTypeWalking = 0.0;
    public static final double exerciseTypeRunning = 1.0;
    public static final double exerciseTypeYoga = 2.0;
    public static final double exerciseTypeBiking = 3.0;
    public static final double exerciseTypeSwimming = 4.0;


    public ExerciseModel() {
    }

    public ExerciseModel(boolean isImperial, double minutes, double exerciseType, double weight) {
        this.minutes = minutes;
        this.exerciseType=exerciseType;
        calcCaloriesBurned(isImperial, exerciseType, minutes, weight);
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(double exerciseType) {
        this.exerciseType = exerciseType;
    }

    private void calcCaloriesBurned(boolean isImperial, double exerciseType, double minutes, double weight) {
        double hours = minutes / 60.0;
        double step1 = 0;// = NutritionSingleton.getInstance().getCurrProfile()
        if (isImperial) {
            step1 = weight / 2.2;
        } else {
            step1 = weight;
        }
        double MET = 0.0; //needs to be database call in the future
        if (exerciseType == exerciseTypeWalking) //walking for exercise MET value
        {
            MET = 4.3;
        } else if (exerciseType == exerciseTypeRunning) //general jogging value
        {
            MET = 7;
        } else if (exerciseType == exerciseTypeYoga) //light effort exercise
        {
            MET = 2.8;
        } else if (exerciseType == exerciseTypeBiking) //general bicycling
        {
            MET = 7.5;
        } else //if(exerciseType == "Swimming") //general swimming
        {
            MET = 6.0;
        }
        double step3 = step1 * MET;
        calories = step3 * hours;
    }
}

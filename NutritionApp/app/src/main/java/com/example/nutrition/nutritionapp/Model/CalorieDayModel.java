package com.example.nutrition.nutritionapp.Model;

import com.example.nutrition.nutritionapp.NutritionSingleton;

import java.util.List;

/**
 * This class keeps track of a day for a person. It combines data from a FoodModel and ExerciseModel
 * for a single day which provides us with their net calorie gain.
 */

public class CalorieDayModel {
    private List<FoodModel> foods;
    private List<ExerciseModel> exercises;
    private List<WaterModel> waters;

    private int waterAmountGoal;
    private int exerciseCalorieGoal;
    private int foodCalorieGoal;
    private double caloriesBurnedExercising;

    public static String exerciseType = "Walking";

    public CalorieDayModel() {
    }

    public void addFood(FoodModel food){
        foods.add(food);
    }

    public void addExercise(ExerciseModel exercise){
        exercises.add(exercise);
    }

    public void addWater(WaterModel water){
        waters.add(water);
    }

    //remove water, food, exercise, profile

    public void setCaloriesBurned(){
        ProfileModel profile = NutritionSingleton.getInstance().getCurrProfile();
        //double hours = minutes from exercise model / 60
        double step1 = 0;// = NutritionSingleton.getInstance().getCurrProfile()
        if(profile.getIsMetric() == true)
        {
            step1 = profile.getCurrWeightKilos();
        }
        else
        {
            step1 = profile.getCurrWeightPounds() / 2.2;
        }
        double MET = 0; //needs to be database call in the future
        if(exerciseType == "Walking") //walking for exercise MET value
        {
            MET = 4.3;
        }
        else if(exerciseType == "Running") //general jogging value
        {
            MET = 7;
        }
        else if(exerciseType == "Yoga") //light effort exercise
        {
            MET = 2.8;
        }
        else if(exerciseType == "Biking") //general bicycling
        {
            MET = 7.5;
        }
        else //if(exerciseType == "Swimming") //general swimming
        {
            MET = 6.0;
        }
        double step3 = step1 * MET;
        //caloriesBurnedExercising += step3 * hours;
    }

    public int getTotalWater(){
        int totalOunces=0;
        for(WaterModel water:waters){
            totalOunces+=water.getOuncesDrank();
        }

        return totalOunces;
    }

    public void removeWater(String id){
        WaterModel water=null;
        for(int i=0;i<waters.size();++i){
            if(waters.get(i).getWaterID()==id){
                water=waters.get(i);
            }
        }
        if(water!=null)
        {
            waters.remove(water);
        }
    }


    /* remove food, remove exercise */

    public int getTotalCaloriesBurned(){
        int sum=0;
        for(ExerciseModel exercise: exercises){
            sum+=exercise.getCalories();
        }

        return sum;
    }

    public int getTotalCaloriesAte(){
        int sum=0;
        for(FoodModel food:foods){
            sum+=food.getCalories();
        }

        return sum;
    }

    public int getWaterAmountGoal() {
        return waterAmountGoal;
    }

    public void setWaterAmountGoal(int waterAmountGoal) {
        this.waterAmountGoal = waterAmountGoal;
    }

    public int getExerciseCalorieGoal() {
        return exerciseCalorieGoal;
    }

    public void setExerciseCalorieGoal(int exerciseCalorieGoal) {
        this.exerciseCalorieGoal = exerciseCalorieGoal;
    }

    public int getFoodCalorieGoal() {
        return foodCalorieGoal;
    }

    public void setFoodCalorieGoal(int foodCalorieGoal) {
        this.foodCalorieGoal = foodCalorieGoal;
    }
}

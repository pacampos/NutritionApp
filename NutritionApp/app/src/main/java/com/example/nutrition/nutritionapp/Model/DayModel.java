package com.example.nutrition.nutritionapp.Model;

import com.example.nutrition.nutritionapp.NutritionSingleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class keeps track of a day for a person. It combines data from a FoodModel and ExerciseModel
 * for a single day which provides us with their net calorie gain.
 */

public class DayModel {
    public static String exerciseType = "Walking";
    private ArrayList<FoodModel> foods;
    private ArrayList<ExerciseModel> exercises;
    private double waterAmountDrank;
    private double waterAmountGoal;
    private double exerciseCalorieGoal;
    private double foodCalorieGoal;
    private double caloriesBurnedExercising;

    public DayModel() {
        foods=new ArrayList<>();
        foods.add(new FoodModel());
        exercises=new ArrayList<>();
        exercises.add(new ExerciseModel());
        waterAmountDrank=0.0;
    }

    public void addFood(FoodModel food) {
        foods.add(food);
    }

    public void addExercise(ExerciseModel exercise) {
        exercises.add(exercise);
    }

    public ArrayList<FoodModel> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<FoodModel> foods) {
        this.foods = foods;
    }

    public ArrayList<ExerciseModel> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<ExerciseModel> exercises) {
        this.exercises = exercises;
    }

    //remove water, food, exercise, profile

    public void setCaloriesBurned() {
        ProfileModel profile = NutritionSingleton.getInstance().getCurrProfile();
        //double hours = minutes from exercise model / 60
        double step1 = 0;// = NutritionSingleton.getInstance().getCurrProfile()
        if (profile.getIsImperial() == true) {
            step1 = profile.getCurrWeightKilos();
        } else {
            step1 = profile.getCurrWeightPounds() / 2.2;
        }
        double MET = 0; //needs to be database call in the future
        if (exerciseType == "Walking") //walking for exercise MET value
        {
            MET = 4.3;
        } else if (exerciseType == "Running") //general jogging value
        {
            MET = 7;
        } else if (exerciseType == "Yoga") //light effort exercise
        {
            MET = 2.8;
        } else if (exerciseType == "Biking") //general bicycling
        {
            MET = 7.5;
        } else //if(exerciseType == "Swimming") //general swimming
        {
            MET = 6.0;
        }
        double step3 = step1 * MET;
        //caloriesBurnedExercising += step3 * hours;
    }

    public double getWaterAmountDrank() {
        return waterAmountDrank;
    }

    public void setWaterAmountDrank(double waterAmountDrank) {
        this.waterAmountDrank = waterAmountDrank;
    }

    public void addWaterAmount(double waterAmount){
        setWaterAmountDrank(getWaterAmountDrank()+waterAmount);
    }

    public void addEightOuncesWaterAmount(){
        setWaterAmountDrank(getWaterAmountDrank()+8.0);
    }

    /* remove food, remove exercise */

    public int getTotalCaloriesBurned() {
        int sum = 0;
        for (ExerciseModel exercise : exercises) {
            sum += exercise.getCalories();
        }

        return sum;
    }

    public int getTotalCaloriesAte() {
        int sum = 0;
        for (FoodModel food : foods) {
            sum += food.getCalories();
        }

        return sum;
    }

    public double getWaterAmountGoal() {
        return waterAmountGoal;
    }

    public void setWaterAmountGoal(double waterAmountGoal) {
        this.waterAmountGoal = waterAmountGoal;
    }

    public double getExerciseCalorieGoal() {
        return exerciseCalorieGoal;
    }

    public void setExerciseCalorieGoal(double exerciseCalorieGoal) {
        this.exerciseCalorieGoal = exerciseCalorieGoal;
    }

    public double getFoodCalorieGoal() {
        return foodCalorieGoal;
    }

    public void setFoodCalorieGoal(double foodCalorieGoal) {
        this.foodCalorieGoal = foodCalorieGoal;
    }

}

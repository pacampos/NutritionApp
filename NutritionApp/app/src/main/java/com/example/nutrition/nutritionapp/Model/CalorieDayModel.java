package com.example.nutrition.nutritionapp.Model;

import java.util.List;

/**
 * This class keeps track of a day for a person. It combines data from a FoodModel and ExerciseModel
 * for a single day which provides us with their net calorie gain.
 */

public class CalorieDayModel {
    private List<FoodModel> foods;
    private List<ExerciseModel> exercises;
    private List<WaterModel> waters;


    public void addFood(FoodModel food){
        foods.add(food);
    }

    public void addExercise(ExerciseModel exercise){
        exercises.add(exercise);
    }

    public void addWater(WaterModel water){
        waters.add(water);
    }





}

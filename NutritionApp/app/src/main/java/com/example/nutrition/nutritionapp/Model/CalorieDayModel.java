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

    private int waterGoal;
    private int exerciseGoal;
    private int foodGoal;


    public void addFood(FoodModel food){
        foods.add(food);
    }

    public void addExercise(ExerciseModel exercise){
        exercises.add(exercise);
    }

    public void addWater(WaterModel water){
        waters.add(water);
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
}

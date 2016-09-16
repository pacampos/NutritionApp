package com.example.nutrition.nutritionapp.Model;

/**
 * This model describes the calories burned from a single exercise. Each exercise has a specific
 * amount of calories you would burn for a specific amount of time.
 */
public class ExerciseModel {
    private int calories;
    private int minutes;
    private String exerciseID;

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }
}

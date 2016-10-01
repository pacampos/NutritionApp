package com.example.nutrition.nutritionapp.Model;

/**
 * This model describes the calories burned from a single exercise. Each exercise has a specific
 * amount of calories you would burn for a specific amount of time.
 */
public class ExerciseModel {
    private double calories;
    private double minutes;
    private String exerciseID;
    private String exerciseType;

    public ExerciseModel() {
    }

    public ExerciseModel(double calories, double minutes, String exerciseID, String exerciseType) {
        this.calories = calories;
        this.minutes = minutes;
        this.exerciseID = exerciseID;
        this.exerciseType=exerciseType;
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

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }
}

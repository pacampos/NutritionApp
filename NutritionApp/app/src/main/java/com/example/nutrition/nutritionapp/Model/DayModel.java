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
    private double servingsGrains;
    private double servingsVeggie;
    private double servingsFruit;
    private double servingsDairy;
    private double servingsMeat;


    public DayModel() {
        foods=new ArrayList<>();
        exercises=new ArrayList<>();
        waterAmountDrank=0.0;
        servingsGrains=0.0;
        servingsVeggie=0.0;
        servingsFruit=0.0;
        servingsDairy=0.0;
        servingsMeat=0.0;
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

    public double calcTotalCaloriesBurned() {
        double sum = 0.0;
        for (ExerciseModel exercise : exercises) {
            sum += exercise.getCalories();
        }

        return sum;
    }

    public double calcTotalCaloriesAte() {
        double sum = 0.0;
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

    public double getServingsGrains() {
        return servingsGrains;
    }

    public void setServingsGrains(double servingsGrains) {
        this.servingsGrains = servingsGrains;
    }

    public double getServingsVeggie() {
        return servingsVeggie;
    }

    public void setServingsVeggie(double servingsVeggie) {
        this.servingsVeggie = servingsVeggie;
    }

    public double getServingsFruit() {
        return servingsFruit;
    }

    public void setServingsFruit(double servingsFruit) {
        this.servingsFruit = servingsFruit;
    }

    public double getServingsDairy() {
        return servingsDairy;
    }

    public void setServingsDairy(double servingsDairy) {
        this.servingsDairy = servingsDairy;
    }

    public double getServingsMeat() {
        return servingsMeat;
    }

    public void setServingsMeat(double servingsMeat) {
        this.servingsMeat = servingsMeat;
    }

    public void addServingsGrains(double addition){
        setServingsGrains(getServingsGrains()+addition);
    }

    public void addServingsVeggie(double addition){
        setServingsVeggie(getServingsVeggie()+addition);
    }

    public void addServingsFruit(double addition){
        setServingsFruit(getServingsFruit()+addition);
    }

    public void addServingsDairy(double addition){
        setServingsDairy(getServingsDairy()+addition);
    }

    public void addServingsMeat(double addition){
        setServingsMeat(getServingsMeat()+addition);
    }

}

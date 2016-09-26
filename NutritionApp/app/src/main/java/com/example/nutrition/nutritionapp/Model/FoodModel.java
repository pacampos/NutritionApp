package com.example.nutrition.nutritionapp.Model;

/**
 * Created by phoenixcampos01 on 9/8/16.
 */
public class FoodModel {
    public static final int GRAINS=0;
    public static final int DAIRY=1;
    public static final int FRUITS=2;
    public static final int VEGGIES=3;
    public static final int MEAT=4;
    public static final int FATS=5;


    private String name;
    private double calories;
    private double servings;
//    we need to uniquely identify every food item so we can remove it easily
    private String foodID;
    private double foodType;
//    Must decide how to split food into the food pyramid
//    private enum FoodType
    /* GRAINS, DAIRY, FRUITS, VEGGIES, MEAT, FATS,OILS,SWEETS */

    public FoodModel() {
    }

    public FoodModel(String name, double calories, double servings, String foodID, double foodType) {
        this.name = name;
        this.calories = calories;
        this.servings = servings;
        this.foodID = foodID;
        this.foodType = foodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public double getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

}

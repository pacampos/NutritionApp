package com.example.nutrition.nutritionapp.Model;

/**
 * Created by phoenixcampos01 on 9/8/16.
 */
public class FoodModel {
    public static final double GRAINS = 0.0;
    public static final double DAIRY = 1.0;
    public static final double FRUITS = 2.0;
    public static final double VEGGIES = 3.0;
    public static final double MEAT = 4.0;
    public static final double FATS = 5.0;
    private String name;
    private double calories;
    private double servings;
    private double foodType;
//    Must decide how to split food into the food pyramid
//    private enum FoodType
    /* GRAINS, DAIRY, FRUITS, VEGGIES, MEAT, FATS,OILS,SWEETS */

    public FoodModel() {
    }

    public FoodModel(String name, double calories, double servings, double foodType) {
        this.name = name;
        this.calories = calories;
        this.servings = servings;
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

    public double getFoodType() {
        return foodType;
    }

    public void setFoodType(double foodType) {
        this.foodType = foodType;
    }

}

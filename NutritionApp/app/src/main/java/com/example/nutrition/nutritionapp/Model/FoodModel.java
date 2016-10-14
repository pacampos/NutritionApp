package com.example.nutrition.nutritionapp.Model;

/**
 * Created by phoenixcampos01 on 9/8/16.
 */
public class FoodModel {
    private String name;
    private double calories;

    public FoodModel() {
    }

    public FoodModel(String name, double calories) {
        this.name = name;
        this.calories = calories;
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

    public void setCalories(double calories) {
        this.calories = calories;
    }

}

package com.example.nutrition.nutritionapp.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phoenixcampos01 on 9/8/16.
 */
public class ProfileModel {
    public static final double FEET_TO_METERS = 30.48;
    public static final double INCHES_TO_CENTIMETERS = 2.54;
    public static final double CENTIMETERS_TO_INCHES = 0.393701;
    public static final double POUNDS_TO_KILOS = 0.453592;
    public static final double[] ACTIVITY_LEVEL = {1.2, 1.55, 1.725};
    private double imagePos;
    private String name;
    private double age;
    private double heightCentimeters;
    private double heightInchesPart;
    private double heightFeetPart;
    private boolean gender;
    private boolean isImperial;
    private double currWeightPounds;
    private double currWeightKilos;
    private double goalWeightPounds;
    private double goalWeightKilos;
    private double dayBirth;
    private double monthBirth;
    private double yearBirth;
    private double waistMeasureInches;
    private double thighMeasureInches;
    private double armMeasureInches;
    private double waistMeasureCentimeter;
    private double thighMeasureCentimeter;
    private double armMeasureCentimeter;
    private double activityLevel;
    public int id;
    /* this models everyday this specific user inputs info within a day */
    private HashMap<String,DayModel> days;

    public ProfileModel() {
    }

    public ProfileModel(double imagePos, String name, double age, double heightInchesPart,
                        double heightFeetPart, boolean gender, double currWeightPounds,
                        double goalWeightPounds, double dayBirth, double monthBirth, double yearBirth, double waistMeasureInches,
                        double thighMeasureInches, double armMeasureInches, double activityLevel, boolean isImperial) {
        this.imagePos = imagePos;
        this.name = name;
        this.age = age;
        this.heightInchesPart = heightInchesPart;
        this.heightFeetPart = heightFeetPart;
        this.gender = gender;
        this.currWeightPounds = currWeightPounds;
        this.goalWeightPounds = goalWeightPounds;
        this.dayBirth = dayBirth;
        this.monthBirth = monthBirth;
        this.yearBirth = yearBirth;
        this.waistMeasureInches = waistMeasureInches;
        this.thighMeasureInches = thighMeasureInches;
        this.armMeasureInches = armMeasureInches;
        this.activityLevel = activityLevel;
        this.isImperial=isImperial;
        days=new HashMap<>();
        days.put("first test",new DayModel());
        days.put("second test",new DayModel());
    }

    public ProfileModel(double imagePos, String name, double age, double heightCentimeters,
                        boolean gender, double currWeightKilos, double goalWeightKilos,
                        double dayBirth, double monthBirth, double yearBirth, double waistMeasureCentimeter,
                        double thighMeasureCentimeter, double armMeasureCentimeter, double activityLevel, boolean isImperial) {
        this.imagePos = imagePos;
        this.name = name;
        this.age = age;
        this.heightCentimeters = heightCentimeters;
        this.gender = gender;
        this.currWeightKilos = currWeightKilos;
        this.goalWeightKilos = goalWeightKilos;
        this.dayBirth = dayBirth;
        this.monthBirth = monthBirth;
        this.yearBirth = yearBirth;
        this.waistMeasureCentimeter = waistMeasureCentimeter;
        this.thighMeasureCentimeter = thighMeasureCentimeter;
        this.armMeasureCentimeter = armMeasureCentimeter;
        this.activityLevel = activityLevel;
        this.isImperial=isImperial;
        days=new HashMap<>();
        addDay(new DayModel());
    }

    public double calcCaloriesBurnedNaturally() {
        double DCE = 0;
        if (gender == true) {
            if (!isImperial) {
                DCE = ACTIVITY_LEVEL[(int) activityLevel] * ((13.75 * currWeightKilos) + (5 * heightCentimeters) - (6.76 * age) + 66);
            } else {
                double feetToInches = heightFeetPart * 12;
                double totalHeightInches = heightInchesPart + feetToInches;
                DCE = ACTIVITY_LEVEL[(int) activityLevel] * ((6.25 * currWeightPounds) + (12.7 * totalHeightInches) - (6.76 * age) + 66);
            }

        } else {
            if (!isImperial) {
                DCE = ACTIVITY_LEVEL[(int) activityLevel] * ((9.56 * currWeightKilos) + (1.85 * heightCentimeters) - (4.68 * age) + 655);
            } else {
                double feetToInches = heightFeetPart * 12;
                double totalHeightInches = heightInchesPart + feetToInches;
                DCE = ACTIVITY_LEVEL[(int) activityLevel] * ((4.35 * currWeightPounds) + (4.7 * totalHeightInches) - (4.68 * age) + 655);
            }
        }
        return DCE;
    }

    public void setImperial(boolean imperial) {
        isImperial = imperial;
    }

    public boolean getIsImperial() {
        return isImperial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getHeightInchesPart() {
        return heightInchesPart;
    }

    private void setHeightInchesPart(double heightInchesPart) {
        this.heightInchesPart = heightInchesPart;
    }

    public double getHeightFeetPart() {
        return heightFeetPart;
    }

    private void setHeightFeetPart(double heightFeetPart) {
        this.heightFeetPart = heightFeetPart;
    }

    public double getHeightCentimeters() {
        return heightCentimeters;
    }

    public void setHeightCentimeters(double centimeters) {
        heightCentimeters = centimeters;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }


    public double getGoalWeightPounds() {
        return goalWeightPounds;
    }

    public void setGoalWeightPounds(double goalWeightPounds) {
        this.goalWeightPounds = goalWeightPounds;
//        setGoalWeightKilos(poundsToKilos(getGoalWeightPounds()));
    }

    public double getGoalWeightKilos() {
        return goalWeightKilos;
    }

    public void setGoalWeightKilos(double goalWeightKilos) {
        this.goalWeightKilos = goalWeightKilos;
//        setGoalWeightPounds(kilosToPounds(getGoalWeightKilos()));
    }

    public double getCurrWeightPounds() {
        return currWeightPounds;
    }

    public void setCurrWeightPounds(double currWeightPounds) {
        this.currWeightPounds = currWeightPounds;
//        setGoalWeightKilos(poundsToKilos(getCurrWeightPounds()));
    }

    public double getCurrWeightKilos() {
        return currWeightKilos;
    }

    public void setCurrWeightKilos(double currWeightKilos) {
        this.currWeightKilos = currWeightKilos;
//        setCurrWeightPounds(kilosToPounds(getCurrWeightKilos()));
    }

    public HashMap<String,DayModel> getDays() { return days; }

    public void setDays(HashMap<String,DayModel> days) { this.days = days; }

    public void addDay(DayModel dayModel){
        // Setting the day which this day represents
        Date today=new Date();
        SimpleDateFormat format= new SimpleDateFormat("MMddyyyy");
        String date = format.format(today);
        days.put(date,dayModel);
    }

    public double calculateBMI() {
        return  (currWeightKilos / ((heightCentimeters/100) * (heightCentimeters/100)));
    }
//
//    /* update to create acceptable ranges for the BMI */
//    public int getBMIHealth(){
//        return -1;
//    }

    public double getDayBirth() {
        return dayBirth;
    }

    public void setDayBirth(int dayBirth) {
        this.dayBirth = dayBirth;
    }

    public double getMonthBirth() {
        return monthBirth;
    }

    public void setMonthBirth(int monthBirth) {
        this.monthBirth = monthBirth;
    }

    public double getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(int yearBirth) {
        this.yearBirth = yearBirth;
    }

    private double poundsToKilos(double pounds) {
        return pounds * POUNDS_TO_KILOS;
    }

    private double kilosToPounds(double kilos) {
        return kilos / POUNDS_TO_KILOS;
    }

    public double getImagePos() {
        return imagePos;
    }

    public void setImagePos(int imagePos) {
        this.imagePos = imagePos;
    }

    public double getWaistMeasureCentimeter() {
        return waistMeasureCentimeter;
    }

    public void setWaistMeasureCentimeter(double waistMeasureCentimeter) {
        this.waistMeasureCentimeter = waistMeasureCentimeter;
    }

    public double getThighMeasureCentimeter() {
        return thighMeasureCentimeter;
    }

    public void setThighMeasureCentimeter(double thighMeasureCentimeter) {
        this.thighMeasureCentimeter = thighMeasureCentimeter;
    }

    public double getArmMeasureCentimeter() {
        return armMeasureCentimeter;
    }

    public void setArmMeasureCentimeter(double armMeasureCentimeter) {
        this.armMeasureCentimeter = armMeasureCentimeter;
    }

    public double getWaistMeasureInches() {
        return waistMeasureInches;
    }

    public void setWaistMeasureInches(double waistMeasureInches) {
        this.waistMeasureInches = waistMeasureInches;
    }

    public double getThighMeasureInches() {
        return thighMeasureInches;
    }

    public void setThighMeasureInches(double thighMeasureInches) {
        this.thighMeasureInches = thighMeasureInches;
    }

    public double getArmMeasureInches() {
        return armMeasureInches;
    }

    public void setArmMeasureInches(double armMeasureInches) {
        this.armMeasureInches = armMeasureInches;
    }

    public double getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(double activityLevel) {
        this.activityLevel = activityLevel;
    }
}

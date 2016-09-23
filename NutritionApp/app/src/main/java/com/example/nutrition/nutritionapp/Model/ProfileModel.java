package com.example.nutrition.nutritionapp.Model;

import android.media.Image;

import java.util.Date;
import java.util.List;

/**
 * Created by phoenixcampos01 on 9/8/16.
 */
public class ProfileModel {
    private String imageName;
    public static final double FEET_TO_METERS=30.48;
    public static final double INCHES_TO_CENTIMETERS=2.54;
    public static final double CENTIMETERS_TO_INCHES=0.393701;
    public static final double POUNDS_TO_KILOS=0.453592;
    private String name;
    private int age;
    private double heightCentimeters;
    private int heightInchesPart;
    private int heightFeetPart;
    private boolean gender;
    private double currWeightPounds;
    private double currWeightKilos;
    private double goalWeightPounds;
    private double goalWeightKilos;
    private Date dateOfBirth;
    private double waistMeasureInches;
    private double thighMeasureInches;
    private double armMeasureInches;
    private double waistMeasureCentimeter;
    private double thighMeasureCentimeter;
    private double armMeasureCentimeter;

    public ProfileModel(String imageName, String name, int age, int heightInchesPart,
                        int heightFeetPart, boolean gender, double currWeightPounds,
                        double goalWeightPounds, Date dateOfBirth, double waistMeasureInches,
                        double thighMeasureInches, double armMeasureInches, List<CalorieDayModel> days) {
        this.imageName = imageName;
        this.name = name;
        this.age = age;
        this.heightInchesPart = heightInchesPart;
        this.heightFeetPart = heightFeetPart;
        this.gender = gender;
        this.currWeightPounds = currWeightPounds;
        this.goalWeightPounds = goalWeightPounds;
        this.dateOfBirth = dateOfBirth;
        this.waistMeasureInches = waistMeasureInches;
        this.thighMeasureInches = thighMeasureInches;
        this.armMeasureInches = armMeasureInches;
        this.days = days;
    }

    public ProfileModel(String imageName, String name, int age, double heightCentimeters,
                        boolean gender, double currWeightKilos, double goalWeightKilos,
                        Date dateOfBirth, double waistMeasureCentimeter,
                        double thighMeasureCentimeter, double armMeasureCentimeter,
                        List<CalorieDayModel> days) {
        this.imageName = imageName;
        this.name = name;
        this.age = age;
        this.heightCentimeters = heightCentimeters;
        this.gender = gender;
        this.currWeightKilos = currWeightKilos;
        this.goalWeightKilos = goalWeightKilos;
        this.dateOfBirth = dateOfBirth;
        this.waistMeasureCentimeter = waistMeasureCentimeter;
        this.thighMeasureCentimeter = thighMeasureCentimeter;
        this.armMeasureCentimeter = armMeasureCentimeter;
        this.days = days;
    }

    /* this models everyday this specific user inputs info within a day */
    private List<CalorieDayModel> days;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeightInchPart(){
       return heightInchesPart;
    }

    public double getHeightInchesPart() {
        return heightInchesPart;
    }

    private void setHeightInchesPart(int heightInchesPart) {
        this.heightInchesPart = heightInchesPart;
    }

    private void setHeightFeetPart(int heightFeetPart) {
        this.heightFeetPart = heightFeetPart;
    }

    public double getHeightFeetPart(){
        return heightFeetPart;
    }

    public double getHeightCentimeters(){ return heightCentimeters; }

    public void setHeightCentimeters(double centimeters){
        heightCentimeters=centimeters;

        int feet=(int)(heightCentimeters*CENTIMETERS_TO_INCHES)/12;
        int inches=(int)(heightCentimeters*CENTIMETERS_TO_INCHES)%12;
        setHeightWithFeetAndInches(feet,inches);
    }

    public void setHeightWithFeetAndInches(int feet, int inches){
        setHeightFeetPart(feet);
        setHeightInchesPart(inches);
        setHeightCentimeters((feet*FEET_TO_METERS)+(inches*INCHES_TO_CENTIMETERS));
    }

    public boolean isGender() { return gender; }

    public void setGender(boolean gender) { this.gender = gender; }


    public double getGoalWeightPounds() {
        return goalWeightPounds;
    }

    public void setGoalWeightPounds(double goalWeightPounds) {
        this.goalWeightPounds = goalWeightPounds;
        setGoalWeightKilos(poundsToKilos(getGoalWeightPounds()));
    }

    public double getGoalWeightKilos() {
        return goalWeightKilos;
    }

    public void setGoalWeightKilos(double goalWeightKilos) {
        this.goalWeightKilos = goalWeightKilos;
        setGoalWeightPounds(kilosToPounds(getGoalWeightKilos()));
    }

    public double getCurrWeightPounds() { return currWeightPounds; }

    public void setCurrWeightPounds(double currWeightPounds) {
        this.currWeightPounds = currWeightPounds;
        setGoalWeightKilos(poundsToKilos(getCurrWeightPounds()));
    }

    public double getCurrWeightKilos() { return currWeightKilos; }

    public void setCurrWeightKilos(double currWeightKilos) {
        this.currWeightKilos = currWeightKilos;
        setCurrWeightPounds(kilosToPounds(getCurrWeightKilos()));
    }

    public List<CalorieDayModel> getDays() { return days; }

    public void setDays(List<CalorieDayModel> days) { this.days = days; }

    public void addDay(CalorieDayModel day){
        days.add(day);
    }

    public double getUserBMI(){ return currWeightKilos/(heightCentimeters*heightCentimeters); }

    /* update to create acceptable ranges for the BMI */
    public int getBMIHealth(){
        return -1;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    private double poundsToKilos(double pounds){
        return pounds*POUNDS_TO_KILOS;
    }

    private double kilosToPounds(double kilos){
        return kilos/POUNDS_TO_KILOS;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
}

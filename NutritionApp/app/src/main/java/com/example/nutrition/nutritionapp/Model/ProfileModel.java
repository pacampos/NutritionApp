package com.example.nutrition.nutritionapp.Model;

import android.media.Image;

import java.util.Date;
import java.util.List;

/**
 * Created by phoenixcampos01 on 9/8/16.
 */
public class ProfileModel {
    private String profileID;
    private Image profilePic;
    public static final double FEET_TO_METERS=0.3048;
    public static final double INCHES_TO_METERS=0.0254;
    public static final double METERS_TO_INCHES=39.3700;
    public static final double POUNDS_TO_KILOS=0.453592;
    private String name;
    private int age;
    private double heightMeters;
    private int heightInchesPart;
    private int heightFeetPart;
    private boolean gender;
    private double currWeightPounds;
    private double currWeightKilos;
    private double goalWeightPounds;
    private double goalWeightKilos;
    private Date dateOfBirth;
    private double waistMeasure;
    private double thighMeasure;
    private double armMeasure;

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

    public double getHeightMeters(){ return heightMeters; }

    public void setHeightMeters(double meters){
        heightMeters=meters;

        int feet=(int)(heightMeters*METERS_TO_INCHES)/12;
        int inches=(int)(heightMeters*METERS_TO_INCHES)%12;
        setHeightWithFeetAndInches(feet,inches);
    }

    public void setHeightWithFeetAndInches(int feet, int inches){
        setHeightFeetPart(feet);
        setHeightInchesPart(inches);
        setHeightMeters((feet*FEET_TO_METERS)+(inches*INCHES_TO_METERS));
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

    public double getUserBMI(){ return currWeightKilos/(heightMeters*heightMeters); }

    /* update to create acceptable ranges for the BMI */
    public int getBMIHealth(){
        return -1;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }

    private double poundsToKilos(double pounds){
        return pounds*POUNDS_TO_KILOS;
    }

    private double kilosToPounds(double kilos){
        return kilos/POUNDS_TO_KILOS;
    }


}

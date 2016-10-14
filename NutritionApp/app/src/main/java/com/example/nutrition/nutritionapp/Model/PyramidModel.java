package com.example.nutrition.nutritionapp.Model;

/**
 * Created by phoenixcampos01 on 10/11/16.
 */

public class PyramidModel {
    private double servingsGrains;
    private double servingsVeggie;
    private double servingsFruit;
    private double servingsDairy;
    private double servingsMeat;

    public PyramidModel(){
        servingsGrains=0.0;
        servingsVeggie=0.0;
        servingsFruit=0.0;
        servingsDairy=0.0;
        servingsMeat=0.0;
    }

    public PyramidModel(double servingsGrains, double servingsVeggie, double servingsFruit, double servingsDairy, double servingsMeat) {
        this.servingsGrains = servingsGrains;
        this.servingsVeggie = servingsVeggie;
        this.servingsFruit = servingsFruit;
        this.servingsDairy = servingsDairy;
        this.servingsMeat = servingsMeat;
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

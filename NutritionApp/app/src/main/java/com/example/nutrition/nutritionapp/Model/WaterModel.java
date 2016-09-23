package com.example.nutrition.nutritionapp.Model;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class WaterModel {
    private int ouncesDrank;
    private String waterID;


    public WaterModel(int ouncesDrank, String waterID) {
        this.ouncesDrank = ouncesDrank;
        this.waterID = waterID;
    }

    public String getWaterID() {
        return waterID;
    }

    public void setWaterID(String waterID) {
        this.waterID = waterID;
    }

    public int getOuncesDrank() {
        return ouncesDrank;
    }

    public void setOuncesDrank(int ouncesDrank) {
        this.ouncesDrank = ouncesDrank;
    }
}

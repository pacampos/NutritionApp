package com.example.nutrition.nutritionapp.Model;

import java.util.HashMap;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class AccountModel {
    private HashMap<String, ProfileModel> profiles;
    private String username;
    private String password;

    public AccountModel() {
    }

    public AccountModel(String username) {
        this.username = username;
        profiles = new HashMap<String, ProfileModel>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addUserProfile(ProfileModel model) {
        profiles.put(model.getName(), model);
    }

    public HashMap<String, ProfileModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<String, ProfileModel> profiles) {
        this.profiles = profiles;
    }
}

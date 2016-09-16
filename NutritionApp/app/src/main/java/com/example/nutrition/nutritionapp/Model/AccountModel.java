package com.example.nutrition.nutritionapp.Model;

import java.util.List;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class AccountModel {
    private List<ProfileModel> profiles;
    private String username;
    private String password;
    private String hashedPassword;

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

    public List<ProfileModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileModel> profiles) {
        this.profiles = profiles;
    }
}

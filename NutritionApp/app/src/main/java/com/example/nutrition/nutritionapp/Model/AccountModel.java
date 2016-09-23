package com.example.nutrition.nutritionapp.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class AccountModel {
    private HashMap<String,ProfileModel> profiles;
    private String username;
    private String password;
    private String hashedPassword;

    public AccountModel(String username) {
        this.username = username;
        profiles = new HashMap<String,ProfileModel>();
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

    public HashMap<String,ProfileModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<String,ProfileModel> profiles) {
        this.profiles = profiles;
    }
}

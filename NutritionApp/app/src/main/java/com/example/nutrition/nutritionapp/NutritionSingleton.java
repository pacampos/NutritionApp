package com.example.nutrition.nutritionapp;

import android.accounts.Account;
import android.provider.ContactsContract;

import com.example.nutrition.nutritionapp.Model.AccountModel;
import com.example.nutrition.nutritionapp.Model.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phoenixcampos01 on 9/12/16.
 */
public class NutritionSingleton {
    private static NutritionSingleton mInstance = null;
    private static String USERS_CHILD= "users";
    private String currUser= null;


    private AccountModel currAccount;
    private ProfileModel currProfile;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mUser;

    private List<AccountModel> accounts;

    private NutritionSingleton(){
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static NutritionSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new NutritionSingleton();
        }
        return mInstance;
    }

    public FirebaseUser GetUser(){
        return mUser;
    }

    void SetUser(FirebaseUser user){
        mUser=user;
        final DatabaseReference ref=mFirebaseDatabaseReference.child(USERS_CHILD).child(mUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=null;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    name=snapshot.getKey();
                }


                final DatabaseReference profileRef=ref.child(name);
                profileRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currProfile=dataSnapshot.getValue(ProfileModel.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /**
     *
     * @param username
     * Should only be called during the signup process, it creates a new account that can contain
     * several users
     */
    public void CreateNewAccount(String username){
        AccountModel newAccount= new AccountModel(username);
        currAccount=newAccount;
        currUser=username;
        mFirebaseDatabaseReference.child(USERS_CHILD).setValue(currAccount);
    }

    public void CreateNewProfile(double imagePos, String name, double age, double heightCentimeters, boolean gender,
                                 double currWeightKilos, double goalWeightKilos, double dayBirth, double monthBirth, double yearBirth,
                                 double waistMeasureCentimeter , double thighMeasureCentimeter,
                                 double armMeasureCentimeter, double activityLevel){
        currProfile=new ProfileModel(imagePos, name, age, heightCentimeters, gender, currWeightKilos,
                goalWeightKilos, dayBirth, monthBirth, yearBirth, waistMeasureCentimeter,
                thighMeasureCentimeter, armMeasureCentimeter, activityLevel);

        currAccount.addUserProfile(currProfile);

        if(currUser!=null){
            mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser).child(name).setValue(currProfile);
        }
    }

    public void CreateNewProfile(double imagePos, String name, double age, double heightInchesPart,
                                 double heightFeetPart, boolean gender, double currWeightPounds,
                                 double goalWeightPounds,double dayBirth, double monthBirth, double yearBirth, double waistMeasureInches,
                                 double thighMeasureInches, double armMeasureInches, double activityLevel){
        currProfile=new ProfileModel(imagePos, name, age, heightInchesPart, heightFeetPart, gender, currWeightPounds,
                goalWeightPounds, dayBirth, monthBirth, yearBirth, waistMeasureInches, thighMeasureInches, armMeasureInches, activityLevel);

        currAccount.addUserProfile(currProfile);

        if(currUser!=null){
            mFirebaseDatabaseReference.child(USERS_CHILD).child(currUser).child(name).setValue(currProfile);
        }
    }

    public AccountModel getCurrAccount() {
        return currAccount;
    }

    public void setCurrAccount(AccountModel currAccount) {
        this.currAccount = currAccount;
    }

    public ProfileModel getCurrProfile() {
        return currProfile;
    }

    public void setCurrProfile(ProfileModel currProfile) {
        this.currProfile = currProfile;
    }
}

package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nutrition.nutritionapp.Model.ProfileModel;

import static android.R.attr.defaultValue;

public class SwitchUserFragment extends Fragment {
    private Bundle bundle;
    private int profilePos = 0;
    signUpFragment.ReplaceFragmentInterface replaceFragmentInterface;

    public static String IMAGE_POS = "com.example.nutritionapp.image_pos";

    public SwitchUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_switch_user, container, false);


        ProfileArrayAdapter itemsAdapter =
                new ProfileArrayAdapter(getContext(), NutritionSingleton.getInstance().getAllProfiles());

        ListView profileList = (ListView)  v.findViewById(R.id.profilesListView);
        profileList.setAdapter(itemsAdapter);

        //get references
        Button loginButton = (Button) v.findViewById(R.id.loginButton1);
        loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        Button addUserButton = (Button) v.findViewById(R.id.addUserButton);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFragment signUpFragment = (com.example.nutrition.nutritionapp.signUpFragment) com.example.nutrition.nutritionapp.signUpFragment.newInstance(false);
                replaceFragmentInterface.replaceFragment(signUpFragment);
            }
        });

        profileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profilePos=position;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NutritionSingleton.getInstance().switchProfiles(profilePos);
                Intent i = new Intent(getContext(), ActivityHome.class);
                startActivity(i);
            }
        });


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            replaceFragmentInterface = (signUpFragment.ReplaceFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface ReplaceFragmentInterface{
        public void replaceFragment(Fragment fragment);
    }
}

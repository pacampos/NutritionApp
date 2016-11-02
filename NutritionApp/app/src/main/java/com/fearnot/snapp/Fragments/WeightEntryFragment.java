package com.fearnot.snapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fearnot.snapp.Activities.ActivityHome;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;

import static com.fearnot.snapp.RegexHelper.isNumeric;


public class WeightEntryFragment extends Fragment {
    private ReplaceFragmentInterface replaceFragmentInterface;

    public WeightEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weight_entry, container, false);

        final EditText weightEntryEditText = (EditText) v.findViewById(R.id.weightEntryEditText);
        Button weightEntryButton = (Button) v.findViewById(R.id.weightEnterButton);
        weightEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weightEntryEditText.getText().length() > 0 && isNumeric(weightEntryEditText.getText().toString())) {
                    NutritionSingleton.getInstance().updateCurrWeight(Double.valueOf(weightEntryEditText.getText().toString()));
                    Intent i = new Intent(getContext(), ActivityHome.class);
                    startActivity(i);
                }

            }
        });
        return v;
    }


}

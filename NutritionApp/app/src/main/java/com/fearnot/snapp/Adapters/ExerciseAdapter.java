package com.fearnot.snapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fearnot.snapp.Model.ExerciseModel;
import com.fearnot.snapp.R;

import java.util.ArrayList;

/**
 * Created by vishnuvenkateswaran on 10/9/16.
 */

public class ExerciseAdapter extends ArrayAdapter<ExerciseModel> {
    public ExerciseAdapter(Context context, ArrayList<ExerciseModel> exercises) {
        super(context, 0, exercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ExerciseModel exercise = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_exercise, parent, false);
        }
        // Lookup view for data population
        TextView tvExercise = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvCalories = (TextView) convertView.findViewById(R.id.tvCalories);
        // Populate the data into the template view using the data object
        String caloriesBurned = String.valueOf((int) exercise.getCalories()) + " Calories Burned";

        tvExercise.setText(getContext().getResources().getStringArray(R.array.exerciseType_array)[(int) exercise.getExerciseType()]);
        tvCalories.setText(caloriesBurned);
        // Return the completed view to render on screen
        return convertView;
    }
}

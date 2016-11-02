package com.fearnot.snapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fearnot.snapp.Model.Serving;
import com.fearnot.snapp.R;

import java.util.ArrayList;

/**
 * Created by vishnuvenkateswaran on 10/22/16.
 */

public class ServingAdapter extends ArrayAdapter<Serving> {


    public ServingAdapter(Context context, ArrayList<Serving> servings) {
        super(context, 0, servings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Serving serving = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_exercise, parent, false);
        }
        // Lookup view for data population
        TextView tvExercise = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvCalories = (TextView) convertView.findViewById(R.id.tvCalories);
        // Populate the data into the template view using the data object
        String calories = String.valueOf(serving.getCalories());

        tvExercise.setText(serving.getServingDescription());
        tvCalories.setText(calories);
        // Return the completed view to render on screen
        return convertView;
    }

}

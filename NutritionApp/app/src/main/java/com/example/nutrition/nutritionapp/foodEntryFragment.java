package com.example.nutrition.nutritionapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.nutrition.nutritionapp.Model.CompactFood;
import com.example.nutrition.nutritionapp.fatservices.FoodService;
import com.example.nutrition.nutritionapp.fatservices.Response;

import org.w3c.dom.Text;

import java.util.List;

public class foodEntryFragment extends Fragment {
    public foodEntryFragment() {
        // Required empty public constructor
    }
    TextView textView;
    String API_SECRET= "118c4828b97848de9d1576137f9541b1";
    String API_CONSUMER = "739bfa1b0dd3407882ac1b24c5be4167";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_food_entry, container, false);
        Button foodEntryButton = (Button) v.findViewById(R.id.button_log_food);
        final android.widget.SearchView searchView = (SearchView) v.findViewById(R.id.search_bar_food);
        textView= (TextView) v.findViewById(R.id.textview_serving_size);

        foodEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=searchView.getQuery().toString();
                if(!query.isEmpty()){

                    new MyTask().execute(query);


                }
            }
        });

        return v;
    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        List<CompactFood> listFoods;
        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }


        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            String query=params[0];
            FoodService userFood= new FoodService(API_CONSUMER,API_SECRET);
            Response<CompactFood> foods=userFood.searchFoods(query);
            if(foods != null){
              listFoods=foods.getResults();
                return "results";
            }

            return null;
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute("result");
            textView.setText( listFoods.get(0).getName());

            // Do things like hide the progress bar or change a TextView
        }
    }
}

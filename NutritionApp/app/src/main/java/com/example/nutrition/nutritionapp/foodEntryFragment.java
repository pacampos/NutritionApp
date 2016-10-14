package com.example.nutrition.nutritionapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrition.nutritionapp.Model.CompactFood;
import com.example.nutrition.nutritionapp.Model.Food;
import com.example.nutrition.nutritionapp.Model.FoodModel;
import com.example.nutrition.nutritionapp.Model.Serving;
import com.example.nutrition.nutritionapp.fatservices.FoodService;
import com.example.nutrition.nutritionapp.fatservices.Response;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.List;

public class foodEntryFragment extends Fragment {
    public foodEntryFragment() {
        // Required empty public constructor
    }

    final static String OUNCES = "oz";
    final static String GRAMS = "g";
    final static String MILLILETERS = "mL";
    String API_SECRET= "118c4828b97848de9d1576137f9541b1";
    String API_CONSUMER = "739bfa1b0dd3407882ac1b24c5be4167";
    String measurements [] = {"oz", "g", "mL"};
    int servingPos=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_food_entry, container, false);
        Button foodEntryButton = (Button) v.findViewById(R.id.button_log_food);
        Button journalButton = (Button) v.findViewById(R.id.button_food_journal);
        final EditText editTextFoodName = (EditText) v.findViewById(R.id.search_bar_food);
        final EditText editTextFoodAmount = (EditText) v.findViewById(R.id.editTextFoodEaten);
        Spinner servingSpinner = (Spinner) v.findViewById(R.id.spinnerFoodMeasurements);

        servingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                servingPos=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Fragment fragment = new FoodJournalFragment();
                    replaceFragment(fragment);
                }
        });

        foodEntryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String foodName=editTextFoodName.getText().toString();
                String foodAmount = editTextFoodAmount.getText().toString();
                if(!foodName.isEmpty() && !foodAmount.isEmpty()){
                    new MyTask().execute(foodName);
                }

                else if(foodName.isEmpty() && foodAmount.isEmpty()){
                    editTextFoodName.setError("Please enter a food.");
                    editTextFoodAmount.setError("Please enter an amount.");
                }

                else if(foodName.isEmpty()){
                    editTextFoodName.setError("Please enter a food.");
                }

                else{
                    editTextFoodAmount.setError("Please enter an amount.");
                }
            }
        });

        return v;
    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        Food foodItem;
        CompactFood compactFood;
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
                List<CompactFood> listFoods=foods.getResults();
                 compactFood = listFoods.get(0);
                foodItem=userFood.getFood(compactFood.getId());
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
            if(foodItem!=null){
//                List<Serving> foodServingList=foodItem.getServings();
//                Serving serving=foodServingList.get(0);
//                String servingUnit=serving.getMetricServingUnit();
//                BigDecimal servingAmount = serving.getMetricServingAmount();
//                if()
//                String name=compactFood.getName();
//                double calories=serving.getCalories().doubleValue();
//                double servingNum = servings[servingPos];
//                String foodType = "Food";
////                NutritionSingleton.getInstance().addFood(new FoodModel(name, calories, servingNum, 1.0));
//                Toast.makeText(getActivity(), "New Food item was added.", Toast.LENGTH_SHORT).show();
            }

//
            // Do things like hide the progress bar or change a TextView
        }



    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.food_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

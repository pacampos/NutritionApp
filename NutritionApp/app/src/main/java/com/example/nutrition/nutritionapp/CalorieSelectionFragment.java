package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nutrition.nutritionapp.Model.CompactFood;
import com.example.nutrition.nutritionapp.Model.Food;
import com.example.nutrition.nutritionapp.Model.FoodModel;
import com.example.nutrition.nutritionapp.Model.Serving;
import com.example.nutrition.nutritionapp.fatservices.FoodService;
import com.example.nutrition.nutritionapp.fatservices.Response;

import java.util.ArrayList;
import java.util.List;

import static com.example.nutrition.nutritionapp.R.id.calorieList;


public class CalorieSelectionFragment extends Fragment {

    private String foodName;
    private int position;
    String API_SECRET= "118c4828b97848de9d1576137f9541b1";
    String API_CONSUMER = "739bfa1b0dd3407882ac1b24c5be4167";
    private ListView calorieList;
    private EditText portionInput;
    private List<CompactFood> listFoods;
    private List<Serving> foodServingList;
    private int listPosition;

    public CalorieSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calorie_selection, container, false);

        calorieList = (ListView) v.findViewById(R.id.calorieList);
        portionInput = (EditText) v.findViewById(R.id.editPortion);
        Button submitButton = (Button) v.findViewById(R.id.submitCalories);

        new MyTask().execute(foodName);
        calorieList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        calorieList.setSelector(android.R.color.darker_gray);
        calorieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPosition = position;
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (portionInput.length() > 0) {
                    double portion = Double.parseDouble(portionInput.getText().toString());
                    double calories = Double.parseDouble(String.valueOf(foodServingList.get(listPosition).getCalories()));
                    double result = portion * calories;
                    FoodModel food = new FoodModel(listFoods.get(position).getName(), result);
                    NutritionSingleton.getInstance().addFood(food);


                    Fragment fragment = new FoodJournalFragment();
                    replaceFragment(fragment);
                }
            }
        });

        calorieList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        calorieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return v;
    }

    public void setFood(String foodName, int position) {
        this.foodName= foodName;
        this.position = position;
    }
    private class MyTask extends AsyncTask<String, Integer, String> {
        Food foodItem;
        CompactFood compactFood;

        @Override
        protected String doInBackground(String... params) {
            String query=params[0];

            /* autheticated foodservice */
            FoodService userFood= new FoodService(API_CONSUMER,API_SECRET);
             /* search for list of foods that match the query */
            Response<CompactFood> foods=userFood.searchFoods(query);
            /* if we received a valid foodservice, we look for the query */
            if(userFood != null){

                if(foods != null){
                    /* get the list of all foods that match the query */
                    listFoods=foods.getResults();
                    compactFood = listFoods.get(position);
                    foodItem=userFood.getFood(compactFood.getId());
                    return "results";
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute("result");
            if(foodItem!=null){
                foodServingList=foodItem.getServings();
                if (!foodServingList.isEmpty() || foodServingList != null) {
                    calorieList.setAdapter(new ServingAdapter(getContext(), new ArrayList<>(foodServingList)));
                }

            }
        }

    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.food_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

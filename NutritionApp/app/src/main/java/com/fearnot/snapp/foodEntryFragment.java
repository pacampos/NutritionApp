package com.fearnot.snapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import android.widget.ListView;

import com.fearnot.snapp.Model.CompactFood;
import com.fearnot.snapp.Model.Food;
import com.fearnot.snapp.R;
import com.fearnot.snapp.fatservices.FoodService;
import com.fearnot.snapp.fatservices.Response;

import java.util.ArrayList;
import java.util.List;

public class foodEntryFragment extends Fragment {
    public foodEntryFragment() {
        // Required empty public constructor
    }
    private ListView foodList;
    private double OUNCE_TO_ML = 29.5735;
    private double OUNCE_TO_G = 28.3495;
    private double ML_TO_G = 1;
    final static String OUNCES = "oz";
    final static String GRAMS = "g";
    final static String MILLILETERS = "ml";
    String API_SECRET= "118c4828b97848de9d1576137f9541b1";
    String API_CONSUMER = "739bfa1b0dd3407882ac1b24c5be4167";
    String measurements [] = {"oz", "g", "ml"};
    int servingPos=0;
    FoodService userFood;
    private String foodName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_food_entry, container, false);
        Button foodEntryButton = (Button) v.findViewById(R.id.button_log_food);
        Button journalButton = (Button) v.findViewById(R.id.button_food_journal);
        final EditText editTextFoodName = (EditText) v.findViewById(R.id.search_bar_food);
        foodList = (ListView) v.findViewById(R.id.foodList);

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
                foodName=editTextFoodName.getText().toString();
                if(!foodName.isEmpty()){
                    new MyTask().execute(foodName);
                }

                else if(foodName.isEmpty()){
                    editTextFoodName.setError("Please enter a food.");
                }

            }
        });

        return v;
    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        Food foodItem;
        CompactFood compactFood;
        List<CompactFood> listFoods;
        Response<CompactFood> foods;
        ProgressDialog progress;
        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
            progress=new ProgressDialog(getContext());
            progress.setMessage("Searching for foods...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }


        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            String query=params[0];
            /* autheticated foodservice */
            userFood= new FoodService(API_CONSUMER,API_SECRET);
             /* search for list of foods that match the query */
            foods=userFood.searchFoods(query);
            /* if we received a valid foodservice, we look for the query */
            if(userFood != null){

                if(foods != null){
                    /* get the list of all foods that match the query */
                    listFoods=foods.getResults();
                    return "results";
                }
            }
            return null;
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute("result");

            progress.hide();
            if(listFoods!=null){
                List<String> foodNames = new ArrayList<>();
                for (CompactFood food : listFoods) {
                    foodNames.add(food.getName());
                }
                String[] foodArr = new String[foodNames.size()];
                foodArr = foodNames.toArray(foodArr);



                /* once we find a serving unit, we have to convert it to unit the user selected
                * ml to oz
                * ml to g
                * oz to ml
                * oz to g
                * g to oz
                * g to ml
                * */


               // NutritionSingleton.getInstance().addFood(new FoodModel(name, calories));
                //Toast.makeText(getActivity(), "New Food item was added.", Toast.LENGTH_SHORT).show();

                // list view
                foodList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.list_food, foodArr));

                foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       // Food cf =  userFood.getFood(listFoods.get(position).getId());
                        CalorieSelectionFragment fragment = new CalorieSelectionFragment();
                        fragment.setFood(foodName, position);
                        replaceFragment(fragment);

                    }
                });


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

    private double ozToML(double ounces){
        return ounces*OUNCE_TO_ML;
    }

    private double ozToG(double ounces){
        return ounces*OUNCE_TO_G;
    }
    private double gToOZ(double gram) {
        return gram/OUNCE_TO_G;
    }
    private double gToML(double gram){
        return  gram/ML_TO_G;
    }

    private double mlToOz(double ml){
        return ml/OUNCE_TO_ML;
    }

    private double mlToG(double ml){
        return ml*ML_TO_G;
    }

}

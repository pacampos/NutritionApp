package com.fearnot.snapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.Model.CompactFood;
import com.fearnot.snapp.Model.Food;
import com.fearnot.snapp.R;
import com.fearnot.snapp.fatservices.FoodService;
import com.fearnot.snapp.fatservices.Response;

import java.util.ArrayList;
import java.util.List;

public class foodEntryFragment extends Fragment {
    String API_SECRET = "118c4828b97848de9d1576137f9541b1";
    String API_CONSUMER = "739bfa1b0dd3407882ac1b24c5be4167";
    FoodService userFood;
    private ListView foodList;
    private String foodName;
    private ReplaceFragmentInterface replaceFragmentInterface;
    private Button foodEntryButton;
    private Button journalButton;
    private EditText editTextFoodName;
    public foodEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food_entry, container, false);

        foodEntryButton = (Button) v.findViewById(R.id.button_log_food);
        journalButton = (Button) v.findViewById(R.id.button_food_journal);
        editTextFoodName = (EditText) v.findViewById(R.id.search_bar_food);
        foodList = (ListView) v.findViewById(R.id.foodList);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.foodEntryToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Food and Drink Entry");

        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FoodJournalFragment();
                replaceFragmentInterface.replaceFragment(fragment);
            }
        });

        foodEntryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                foodName = editTextFoodName.getText().toString();
                if (!foodName.isEmpty()) {
                    new MyTask().execute(foodName);
                } else if (foodName.isEmpty()) {
                    editTextFoodName.setError("Please enter a food.");
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            replaceFragmentInterface = (ReplaceFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        List<CompactFood> listFoods;
        Response<CompactFood> foods;
        ProgressDialog progress;

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
            progress = new ProgressDialog(getContext());
            progress.setMessage("Searching for foods...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }


        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            String query = params[0];
            /* autheticated foodservice */
            userFood = new FoodService(API_CONSUMER, API_SECRET);
             /* search for list of foods that match the query */
            foods = userFood.searchFoods(query);
            /* if we received a valid foodservice, we look for the query */
            if (userFood != null) {

                if (foods != null) {
                    /* get the list of all foods that match the query */
                    listFoods = foods.getResults();
                    return "results";
                }
            }
            return null;
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute("result");

            progress.dismiss();
            if (listFoods != null) {
                List<String> foodNames = new ArrayList<>();
                for (CompactFood food : listFoods) {
                    foodNames.add(food.getName());
                }
                String[] foodArr = new String[foodNames.size()];
                foodArr = foodNames.toArray(foodArr);

                // list view
                foodList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.list_food, foodArr));

                foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Food cf =  userFood.getFood(listFoods.get(position).getId());
                        CalorieSelectionFragment fragment = new CalorieSelectionFragment();
                        fragment.setFood(foodName, position);
                        replaceFragmentInterface.replaceFragment(fragment);
                    }
                });
            }
        }
    }
}

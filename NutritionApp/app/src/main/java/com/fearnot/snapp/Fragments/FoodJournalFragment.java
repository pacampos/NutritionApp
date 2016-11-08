package com.fearnot.snapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fearnot.snapp.Adapters.FoodAdapter;
import com.fearnot.snapp.DayAxisValueFormatter;
import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.Model.DayModel;
import com.fearnot.snapp.Model.FoodModel;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class FoodJournalFragment extends Fragment {
    private BarChart chart;
    private ReplaceFragmentInterface replaceFragmentInterface;
    public FoodJournalFragment() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food_journal, container, false);

        // get references
        TextView dailyTotal = new TextView(getContext());

        Button addMore = (Button) v.findViewById(R.id.addFoodButton);

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentInterface.replaceFragment(new foodEntryFragment());
            }
        });

        // Construct the data source
        ArrayList<FoodModel> arrayOfFoods = NutritionSingleton.getInstance().getCurrDay().getFoods();

//        // calculate total
//        int count = 0;
//        for (FoodModel food : arrayOfFoods) {
//            count += food.getCalories();
//        }

        int count = (int) NutritionSingleton.getInstance().getCurrDay().calcTotalCaloriesAte();

        // Create the adapter to convert the array to views
        FoodAdapter adapter = new FoodAdapter(getContext(), arrayOfFoods);
        // Attach the adapter to a ListView
        ListView listView = (ListView) v.findViewById(R.id.foodList);
        listView.setAdapter(adapter);


        String totalDaily = "Daily Total: " + count + " Calories Consumed";
        dailyTotal.setText(totalDaily);

        listView.addFooterView(dailyTotal);
        dailyTotal.setGravity(Gravity.CENTER_HORIZONTAL);


        if (adapter.getCount() > 6) {
            View item = adapter.getView(0, null, listView);
            item.measure(0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
            listView.setLayoutParams(params);
        }


        // BarChart
        chart = (BarChart) v.findViewById(R.id.consumedChart);

        List<BarEntry> entries = new ArrayList<>();
        TreeMap<Integer, Integer> thingies = new TreeMap<>();
        HashMap<String, DayModel> dayMap = NutritionSingleton.getInstance().getCurrProfile().getDays();
        Set<String> dayKeys = dayMap.keySet();
        for (String key : dayKeys) {
            String month = key.substring(0, 2);
            String day = key.substring(2, 4);
            String year = key.substring(4);
            Integer monthInt = Integer.parseInt(month);
            Integer dayInt = Integer.parseInt(day);
            Integer yearInt = Integer.parseInt(year);
            Calendar today = Calendar.getInstance();
            today.set(yearInt, monthInt - 1, dayInt - 1);
            int dayOfYear = today.get(Calendar.DAY_OF_YEAR);
            Double caloriesConsumed = Double.valueOf(dayMap.get(key).calcTotalCaloriesAte());
            thingies.put(dayOfYear, caloriesConsumed.intValue());
        }

        for (Map.Entry<Integer, Integer> entry : thingies.entrySet()) {
            entries.add(new BarEntry(entry.getKey(), entry.getValue()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Label");
        BarData barData = new BarData(dataSet);

        barData.setBarWidth(0.9f);

        chart.setData(barData);
        chart.setFitBars(true);

        chart.setDrawGridBackground(false);

        chart.setGridBackgroundColor(R.color.white);

        chart.setDescription("");

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);

        chart.setTouchEnabled(true);

        AxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        xAxis.setValueFormatter(xAxisFormatter);

        // Interval of 1 day
        //  xAxis.setGranularity(1f);

        chart.setScaleYEnabled(false);
        chart.setPinchZoom(true);

        // barData.setDrawValues(false);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawGridLines(false);

        // Pushes the graph down to 0
        yAxis.setAxisMinValue(0f);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        chart.setNoDataText("There is no data available.");

        //CustomMarkerView mv = new CustomMarkerView (getActivity(), R.layout.fragment_progress);
        //chart.setMarkerView(mv);

        chart.animateY(2000);
        chart.invalidate();

        return v;
    }

}

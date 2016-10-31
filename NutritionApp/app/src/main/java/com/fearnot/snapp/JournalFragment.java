package com.fearnot.snapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fearnot.snapp.Model.DayModel;
import com.fearnot.snapp.Model.ExerciseModel;
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


public class JournalFragment extends Fragment {
    private BarChart chart;

    public JournalFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        // get references
        TextView dailyTotal = new TextView(getContext());





        // Construct the data source
        ArrayList<ExerciseModel> arrayOfExercises = NutritionSingleton.getInstance().getCurrDay().getExercises();

        // calculate total
        int count = 0;
        for (ExerciseModel exercise : arrayOfExercises) {
            count += exercise.getCalories();
        }


        // Create the adapter to convert the array to views
        ExerciseAdapter adapter = new ExerciseAdapter(getContext(), arrayOfExercises);
        // Attach the adapter to a ListView
        ListView listView = (ListView) v.findViewById(R.id.exerciseList);
        listView.setAdapter(adapter);


        String totalDaily = "Daily Total: " + count + " Calories Burned";
        dailyTotal.setText(totalDaily);

        listView.addFooterView(dailyTotal);
        dailyTotal.setGravity(Gravity.CENTER_HORIZONTAL);


        if(adapter.getCount() > 6){
            View item = adapter.getView(0, null, listView);
            item.measure(0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
            listView.setLayoutParams(params);
        }


        // BarChart
        chart = (BarChart) v.findViewById(R.id.exerciseChart);

        List<BarEntry> entries = new ArrayList<>();
        TreeMap<Integer, Integer> thingies = new TreeMap<>();
        HashMap<String, DayModel> dayMap = NutritionSingleton.getInstance().getCurrProfile().getDays();
        Set<String> dayKeys = dayMap.keySet();
        for(String key: dayKeys){
            String month=key.substring(0,2);
            String day= key.substring(2,4);
            String year = key.substring(4);
            Integer monthInt = Integer.parseInt(month);
            Integer dayInt = Integer.parseInt(day);
            Integer yearInt = Integer.parseInt(year);
            Calendar today=Calendar.getInstance();
            today.set(yearInt, monthInt-1, dayInt-1);
            int dayOfYear=today.get(Calendar.DAY_OF_YEAR);
            Double caloriesBurn=Double.valueOf(dayMap.get(key).calcTotalCaloriesBurned());
            thingies.put(dayOfYear, caloriesBurn.intValue());
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

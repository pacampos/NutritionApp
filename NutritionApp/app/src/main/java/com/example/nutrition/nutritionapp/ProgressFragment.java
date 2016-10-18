package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrition.nutritionapp.Model.DayModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ProgressFragment extends Fragment  {
    private BarChart chart;
    private BarChart chart2;

    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_progress, container, false);


        // BarChart
        chart = (BarChart) v.findViewById(R.id.chart);
        chart2 = (BarChart) v.findViewById(R.id.chart2);

        List<BarEntry> entries = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();
        TreeMap<Integer, Integer> thingies = new TreeMap<>();
        TreeMap<Integer, Integer> sortedCalories = new TreeMap<>();
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
            Double caloriesConsumed = Double.valueOf(dayMap.get(key).calcTotalCaloriesAte());
            thingies.put(dayOfYear, caloriesBurn.intValue());
            sortedCalories.put(dayOfYear, caloriesConsumed.intValue());
        }

        for (Map.Entry<Integer, Integer> entry : thingies.entrySet()) {
            entries.add(new BarEntry(entry.getKey(), entry.getValue()));
        }

        for(Map.Entry<Integer, Integer> entry : sortedCalories.entrySet()) {
            entries2.add(new BarEntry(entry.getKey(), entry.getValue()));
        }

        addChartSettings(entries, chart);
        addChartSettings(entries2, chart2);


        return v;
    }

    private void addChartSettings(List<BarEntry> entries, BarChart chartEntry) {
        BarDataSet dataSet = new BarDataSet(entries, "Label");
        BarData barData = new BarData(dataSet);

        barData.setBarWidth(0.9f);
        chartEntry.setData(barData);
        chartEntry.setFitBars(true);

        chartEntry.setDrawGridBackground(false);

        chartEntry.setGridBackgroundColor(R.color.white);

        chartEntry.setDescription("");

        YAxis rightAxis = chartEntry.getAxisRight();
        rightAxis.setEnabled(false);


        XAxis xAxis = chartEntry.getXAxis();
        xAxis.setDrawGridLines(false);

        chartEntry.setTouchEnabled(true);

        AxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chartEntry);
        xAxis.setValueFormatter(xAxisFormatter);

        // Interval of 1 day
        //  xAxis.setGranularity(1f);

        chartEntry.setScaleYEnabled(false);
        chartEntry.setPinchZoom(true);

        // barData.setDrawValues(false);
        YAxis yAxis = chartEntry.getAxisLeft();
        yAxis.setDrawGridLines(false);

        // Pushes the graph down to 0
        yAxis.setAxisMinValue(0f);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        chartEntry.setNoDataText("There is no data available.");

        //CustomMarkerView mv = new CustomMarkerView (getActivity(), R.layout.fragment_progress);
        //chart.setMarkerView(mv);

        chartEntry.animateY(2000);
        chartEntry.invalidate();

    }


}

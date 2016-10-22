package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

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
    private BarChart chart3;
    private Drawer result;

    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_progress, container, false);

        // Drawer Items
        PrimaryDrawerItem item = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_home);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_profile);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_graphs);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_credits);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_logout);
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.wallpaperandroid50)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(NutritionSingleton.getInstance().getCurrProfile().getName())
                                .withIcon(getResources().getDrawable(CheckableImageView.mOriginalIds[(int) NutritionSingleton.getInstance().getCurrProfile().getImagePos()]))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        // Actual Drawer
        result= new DrawerBuilder()
                .withActivity(getActivity())
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item,
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            replaceFragment(new HomeFragment());
                            close();
                        } else if (position == 2) {
                            replaceFragment(new ProfileFragment());
                            close();
                        } else if (position == 3) {
                            replaceFragment(new ProgressFragment());
                            close();
                        } else if (position == 4) {
                            replaceFragment(new CreditFragment());
                            close();
                        } else if (position == 6) {
                            // logout
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                        }
                        return true;
                    }
                })
                .build();

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar2);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_2x);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        toolbar.setNavigationOnClickListener(new Toolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();
            }
        });

        // BarChart
        chart = (BarChart) v.findViewById(R.id.chart);
        chart2 = (BarChart) v.findViewById(R.id.chart2);
        chart3 = (BarChart) v.findViewById(R.id.chart3);

        List<BarEntry> entries = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();
        List<BarEntry> entries3 = new ArrayList<>();
        TreeMap<Integer, Integer> sortedCaloriesBurned = new TreeMap<>();
        TreeMap<Integer, Integer> sortedCaloriesConsumed = new TreeMap<>();
        TreeMap<Integer,Integer> sortedWater = new TreeMap<>();
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
            Double waterDrank = Double.valueOf(dayMap.get(key).getWaterAmountDrank());
            sortedCaloriesBurned.put(dayOfYear, caloriesBurn.intValue());
            sortedCaloriesConsumed.put(dayOfYear, caloriesConsumed.intValue());
            sortedWater.put(dayOfYear, waterDrank.intValue());
        }

        for (Map.Entry<Integer, Integer> entry : sortedCaloriesBurned.entrySet()) {
            entries.add(new BarEntry(entry.getKey(), entry.getValue()));
        }

        for(Map.Entry<Integer, Integer> entry : sortedCaloriesConsumed.entrySet()) {
            entries2.add(new BarEntry(entry.getKey(), entry.getValue()));
        }

        for (Map.Entry<Integer, Integer> entry : sortedWater.entrySet()) {
            entries3.add(new BarEntry(entry.getKey(), entry.getValue()));
        }
        addChartSettings(entries, chart);
        addChartSettings(entries2, chart2);
        addChartSettings(entries3, chart3);

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
    private void replaceFragment(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void close() {
        result.closeDrawer();
    }

}

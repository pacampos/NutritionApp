package com.fearnot.snapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.fearnot.snapp.MainActivity;
import com.fearnot.snapp.Model.DayModel;
import com.fearnot.snapp.Model.FoodModel;
import com.fearnot.snapp.Model.ProfileModel;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;
import com.fearnot.snapp.Views.CheckableImageView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
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


public class FoodJournalFragment extends Fragment {
    private BarChart chart;
    private ReplaceFragmentInterface replaceFragmentInterface;
    private Drawer result;
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
// SIDE MENU

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
        result = new DrawerBuilder()
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
                            replaceFragmentInterface.replaceFragment(new HomeFragment());
                            close();
                        } else if (position == 2) {
                            replaceFragmentInterface.replaceFragment(new ProfileFragment());
                            close();
                        } else if (position == 3) {
                            replaceFragmentInterface.replaceFragment(new ProgressFragment());
                            close();
                        } else if (position == 4) {
                            replaceFragmentInterface.replaceFragment(new CreditFragment());
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

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.foodJournalToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_2x);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        toolbar.setNavigationOnClickListener(new Toolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();
            }
        });


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
            View itemx = adapter.getView(0, null, listView);
            itemx.measure(0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (5.5 * itemx.getMeasuredHeight()));
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

    private void close() {
        result.closeDrawer();
    }

}

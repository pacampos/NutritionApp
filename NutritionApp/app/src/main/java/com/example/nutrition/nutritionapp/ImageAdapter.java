package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;

/**
 * Created by AnanthNarayanVenkate on 9/11/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        RadioButton radioButton;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            radioButton = new RadioButton(mContext);
            radioButton.setLayoutParams(new GridView.LayoutParams(85, 85));
//            radioButton.setScaleType(RadioButton.ScaleType.CENTER_CROP);
            radioButton.setPadding(8, 8, 8, 8);
        } else {
            radioButton = (RadioButton) convertView;
        }

//       radioButton.setButtonDrawable(mThumbIds[position]);
//        radioButton.setBackground(null);
//        radioButton.setHeight(10);
//        radioButton.setWidth(10);
        return radioButton;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.apple, R.drawable.pineapple,
            R.drawable.cherries, R.drawable.grapes,
            R.drawable.lemon, R.drawable.orange,
            R.drawable.watermelon, R.drawable.tomato,
            R.drawable.carrot, R.drawable.broccoli,
            R.drawable.cauliflower, R.drawable.mushrooms,
            R.drawable.peas, R.drawable.salad,
            R.drawable.strawberry

    };
}


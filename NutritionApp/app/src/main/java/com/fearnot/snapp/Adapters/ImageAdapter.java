package com.fearnot.snapp.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.fearnot.snapp.R;
import com.fearnot.snapp.Views.CheckableImageView;

/**
 * Created by AnanthNarayanVenkate on 9/11/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.apple, R.drawable.pineapple,
            R.drawable.orange, R.drawable.broccoli,
            R.drawable.carrot, R.drawable.cherries,
            R.drawable.salad, R.drawable.tomato,
            R.drawable.mushrooms, R.drawable.watermelon,
            R.drawable.strawberry, R.drawable.weight,
            R.drawable.jump_rope, R.drawable.strength,
            R.drawable.shoe, R.drawable.bicycle

    };


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
        CheckableImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new CheckableImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

        } else {
            imageView = (CheckableImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}


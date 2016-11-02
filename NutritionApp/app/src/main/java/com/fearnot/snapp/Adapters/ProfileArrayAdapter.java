package com.fearnot.snapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fearnot.snapp.Model.ProfileModel;
import com.fearnot.snapp.R;
import com.fearnot.snapp.Views.CheckableImageView;

import java.util.List;

/**
 * Created by phoenixcampos01 on 10/23/16.
 */

public class ProfileArrayAdapter extends ArrayAdapter {
    public ProfileArrayAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProfileModel item = (ProfileModel) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_profile, parent, false);
        }
        ImageView profileImg = (ImageView) convertView.findViewById(R.id.imageViewProfileList);
        TextView profileName = (TextView) convertView.findViewById(R.id.textViewProfileList);
        int imagePos = (int) item.getImagePos();
        profileName.setText(item.getName());
        profileImg.setImageResource(CheckableImageView.mOriginalIds[imagePos]);

        return convertView;
    }
}

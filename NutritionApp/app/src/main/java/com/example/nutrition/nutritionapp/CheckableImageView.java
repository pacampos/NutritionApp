package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by AnanthNarayanVenkate on 9/11/2016.
 */
public class CheckableImageView extends ImageView implements Checkable {
    private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

    public CheckableImageView(final Context context) {
        super(context);
    }

    @Override
    public int[] onCreateDrawableState(final int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        return drawableState;
    }

    @Override
    public void toggle() {

    }
    public void toggle(ImageView prevImage) {
        if (prevImage != null) {
            prevImage.getDrawable().clearColorFilter();
            prevImage.invalidate();
        }

        setChecked(!mChecked);

    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(final boolean checked) {
        if (mChecked) {
            this.getDrawable().clearColorFilter();
            this.invalidate();
          //  mChecked = checked;
            //return;
        } else{

            this.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
            this.invalidate();
         //   mChecked = checked;
        }
        mChecked = checked;



     Log.d("CREATION", "BOOOP");
        refreshDrawableState();
    }
}

package com.fearnot.snapp.Views;

import android.content.Context;
import android.widget.Checkable;
import android.widget.ImageView;

import com.fearnot.snapp.R;

/**
 * Created by AnanthNarayanVenkate on 9/11/2016.
 */
public class CheckableImageView extends ImageView implements Checkable {
    // references to our images
    public static final Integer[] mOriginalIds = {
            R.drawable.apple, R.drawable.pineapple,
            R.drawable.orange, R.drawable.broccoli,
            R.drawable.carrot, R.drawable.cherries,
            R.drawable.salad, R.drawable.tomato,
            R.drawable.mushrooms, R.drawable.watermelon,
            R.drawable.strawberry, R.drawable.weight,
            R.drawable.jump_rope, R.drawable.strength,
            R.drawable.shoe, R.drawable.bicycle

    };
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private boolean mChecked;
    private Integer[] mThumbIds = {
            R.drawable.apple_checked, R.drawable.pineapple_checked,
            R.drawable.orange_checked, R.drawable.broccoli_checked,
            R.drawable.carrot_checked, R.drawable.cherries_checked,
            R.drawable.salad_checked, R.drawable.tomato_checked,
            R.drawable.mushrooms_checked, R.drawable.watermelon_checked,
            R.drawable.strawberry_checked, R.drawable.weight_checked,
            R.drawable.jump_rope_checked, R.drawable.strength_checked,
            R.drawable.shoe_checked, R.drawable.bicycle_checked

    };

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

    public void toggle(ImageView prevImage, int prevPosition, int position) {
        if (prevImage != null && prevPosition != -1) {
            prevImage.setImageResource(mOriginalIds[prevPosition]);
            prevImage.invalidate();
        }

        setChecked(!mChecked, position);

    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(final boolean checked) {
    }

    public void setChecked(final boolean checked, int position) {
        if (mChecked) {

            this.setImageResource(mOriginalIds[position]);
            this.invalidate();
            //  mChecked = checked;
            //return;
        } else {
            this.setImageResource(mThumbIds[position]);

            //this.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
            this.invalidate();
            //   mChecked = checked;
        }
        mChecked = checked;
        this.invalidate();

        refreshDrawableState();
    }

}

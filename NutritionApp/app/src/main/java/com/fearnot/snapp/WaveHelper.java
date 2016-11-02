package com.fearnot.snapp;

/**
 * Created by vishnuvenkateswaran on 10/7/16.
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.fearnot.snapp.Views.WaveView;

import java.util.ArrayList;
import java.util.List;

public class WaveHelper {
    final static private float MAX_WATER = 64f;
    private float currentWater;
    private WaveView mWaveView;

    private AnimatorSet mAnimatorSet;

    public WaveHelper(WaveView waveView, float currentWater) {
        mWaveView = waveView;
        this.currentWater = currentWater;
        initAnimation();
    }

    public void start() {
        if (currentWater > 0f) {
            mWaveView.setShowWave(true);
            if (mAnimatorSet != null) {
                mAnimatorSet.start();
            }
        }


    }

    public void initAnimation() {

        if (currentWater > 0f) {
            List<Animator> animators = new ArrayList<>();
            // horizontal animation.
            // wave waves infinitely.
            ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waveShiftRatio", 0f, 0f);
            waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
            waveShiftAnim.setDuration(1000);
            waveShiftAnim.setInterpolator(new LinearInterpolator());
            animators.add(waveShiftAnim);

            // amplitude animation.
            // wave grows big then grows small, repeatedly
            ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                    mWaveView, "amplitudeRatio", 0.0001f, 0.05f);
            amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
            amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
            amplitudeAnim.setDuration(5000);
            amplitudeAnim.setInterpolator(new LinearInterpolator());
            animators.add(amplitudeAnim);

            // vertical animation.
            // water level increases from 0 to center of WaveView
            ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waterLevelRatio", currentWater / MAX_WATER, currentWater / MAX_WATER);
            waterLevelAnim.setDuration(0);
            waterLevelAnim.setInterpolator(new DecelerateInterpolator());
            animators.add(waterLevelAnim);

            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(animators);
        }
    }

    public void growWave(float amountFinal) {
        if (currentWater < 64f) {
            List<Animator> animators = new ArrayList<>();

            // horizontal animation.
            // wave waves infinitely.
            ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waveShiftRatio", 0f, 1f);
            waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
            waveShiftAnim.setDuration(1000);
            waveShiftAnim.setInterpolator(new LinearInterpolator());
            animators.add(waveShiftAnim);

            // vertical animation.
            // water level increases from 0 to center of WaveView
            ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waterLevelRatio", currentWater / MAX_WATER, amountFinal / MAX_WATER);
            currentWater = amountFinal;
            waterLevelAnim.setDuration(1200);
            waterLevelAnim.setInterpolator(new DecelerateInterpolator());
            animators.add(waterLevelAnim);

            // amplitude animation.
            // wave grows big then grows small, repeatedly
            ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                    mWaveView, "amplitudeRatio", 0.0001f, 0.05f);
            amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
            amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
            amplitudeAnim.setDuration(5000);
            amplitudeAnim.setInterpolator(new LinearInterpolator());
            animators.add(amplitudeAnim);

            mAnimatorSet = new AnimatorSet();


            mAnimatorSet.playTogether(animators);
        }
    }


    public void cancel() {
        if (mAnimatorSet != null) {
//            mAnimatorSet.cancel();
            mAnimatorSet.end();
        }
    }
}
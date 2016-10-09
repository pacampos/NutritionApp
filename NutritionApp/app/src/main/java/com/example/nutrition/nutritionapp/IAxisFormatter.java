package com.example.nutrition.nutritionapp;

import com.github.mikephil.charting.components.AxisBase;

/**
 * Created by vishnuvenkateswaran on 10/5/16.
 */

public interface IAxisFormatter {

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    String getFormattedValue(float value, AxisBase axis);

    /**
     * Returns the number of decimal digits this formatter uses or -1, if unspecified.
     *
     * @return
     */
    int getDecimalDigits();
}

package com.fanyiran.mygod.utils;

import com.fanyiran.mygod.R;

/**
 * Created by fanqiang on 2019-05-15.
 */
public class TemperatuerColor {
    private static int[] colorResArray = new int[]{
            R.color.temp_color_1,
            R.color.temp_color_2,
            R.color.temp_color_3,
            R.color.temp_color_4,
            R.color.temp_color_5,
            R.color.temp_color_6,
            R.color.temp_color_7,
            R.color.temp_color_8,
            R.color.temp_color_9,
            R.color.temp_color_10,
            R.color.temp_color_11,
            R.color.temp_color_12,
            R.color.temp_color_13,
            R.color.temp_color_14,
            R.color.temp_color_15,
            R.color.temp_color_16,
            R.color.temp_color_17,
            R.color.temp_color_18,
            R.color.temp_color_19,
            R.color.temp_color_20,
            R.color.temp_color_21,
            R.color.temp_color_22,
            R.color.temp_color_23,
            R.color.temp_color_24,
            R.color.temp_color_25,
            R.color.temp_color_26,
    };
    public static int getColorRes(double temperature) {
        return getColorRes(temperature, Constants.TEMP_MAX, Constants.TEMP_MIN);
    }

    public static int getColorRes(double temperature, double maxtemperature, double mintemperature) {
        if(temperature > maxtemperature)
            return colorResArray[0];
        if(temperature < mintemperature)
            return colorResArray[colorResArray.length - 1];
        double ratio = (temperature - mintemperature) / (maxtemperature - mintemperature);
        return colorResArray[(int) (ratio * (colorResArray.length - 1))];
    }
}

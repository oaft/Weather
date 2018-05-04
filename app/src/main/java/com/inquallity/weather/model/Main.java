package com.inquallity.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Olga Aleksandrova  on 01.05.2018.
 */

public class Main {

    @SerializedName("temp")
    private double mTemp;

    @SerializedName("humidity")
    private int mHumidity;

    public double getTemp() {
        return mTemp;
    }

    public int getHumidity() {
        return mHumidity;
    }
}

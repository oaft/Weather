package com.inquallity.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Olga Aleksandrova on 01.05.2018.
 */

public class Wind {

    @SerializedName("speed") private double mSpeed;

    public double getSpeed() {
        return mSpeed;
    }
}

package com.inquallity.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Olga Aleksandrova on 01.05.2018.
 */

public class Weather {

    @SerializedName("description")
    private String mDescription;

    @SerializedName("id")
    private int mID;

    public String getDescription() {
        return mDescription;
    }

    public int getID() {
        return mID;
    }
}

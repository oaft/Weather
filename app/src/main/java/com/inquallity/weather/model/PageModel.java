package com.inquallity.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Olga Aleksandrova on 01.05.2018.
 */

public class PageModel {

    @SerializedName("name") private String mCityName;
    @SerializedName("main") private Main mMain;
    @SerializedName("wind") private Wind mWind;
    @SerializedName("weather") private List<Weather> mWeather;

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public Main getMain() {
        return mMain;
    }

    public Wind getWind() {
        return mWind;
    }

    public List<Weather> getWeather() {
        return mWeather;
    }
}

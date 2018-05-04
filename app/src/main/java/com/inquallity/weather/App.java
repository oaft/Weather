package com.inquallity.weather;

import android.app.Application;

import com.inquallity.weather.api.WeatherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Olga Aleksandrova on 01.05.2018.
 */

public class App extends Application {

    private static WeatherApi sWeatherApi;
    private Retrofit mRetrofit;

    public static WeatherApi getWeatherApi() {
        return sWeatherApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sWeatherApi = mRetrofit.create(WeatherApi.class);
    }
}

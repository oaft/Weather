package com.inquallity.weather.api;

import com.inquallity.weather.model.PageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Olga Aleksandrova on 01.05.2018.
 */

public interface WeatherApi {

    @GET("data/2.5/weather")
    Call<PageModel> getData (@Query("q") String cityName, @Query("APPID") String key);
}

package com.inquallity.weather.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.inquallity.weather.App;
import com.inquallity.weather.R;
import com.inquallity.weather.model.Main;
import com.inquallity.weather.model.PageModel;
import com.inquallity.weather.model.Weather;
import com.inquallity.weather.model.Wind;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Olga Aleksandrova on 01.05.2018.
 */

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "db65325252139963105cfff1e7ea564e";

    @BindView(R.id.tvTemperature) TextView mTemperature;
    @BindView(R.id.tvDescription) TextView mDescription;
    @BindView(R.id.tvHumidity) TextView mHumidity;
    @BindView(R.id.tvWind) TextView mWind;
    @BindView(R.id.ivWeather) ImageView mWeatherIcon;
    @BindView(R.id.llMain) LinearLayout mLinearLayout;
    @BindView(R.id.rlDataSet) RelativeLayout mRelativeLayout;

    private int mWeatherID;
    private int mTempCel;
    private String mWeatherDescription;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchData(query);
        }
    }

    private void searchData(String query) {
        App.getWeatherApi().getData(query, KEY).enqueue(new Callback<PageModel>() {
            @Override
            public void onResponse(Call<PageModel> call, Response<PageModel> response) {
                if (response.body() != null) {
                    showData(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "City does not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PageModel> call, Throwable t) {
                Log.e(MainActivity.class.getSimpleName(), t.getMessage(), t);
                Toast.makeText(MainActivity.this, "smth gone wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showData(PageModel body) {
        mRelativeLayout.setBackgroundResource(R.drawable.rectangle);

        final List<Weather> weatherList = body.getWeather();
        for (int i = 0; i < weatherList.size(); i++) {
            mWeatherID = weatherList.get(i).getID();
            mWeatherDescription = weatherList.get(i).getDescription();
        }
        mWeatherIcon.setImageResource(setIcon(mWeatherID, mTempCel));
        mDescription.setText(mWeatherDescription);

        final Main main = body.getMain();
        final double temp = main.getTemp();
        mTempCel = (int) (temp - 273.15);
        final String stringTemp = String.valueOf(mTempCel);
        mTemperature.setText(String.format("%s %s", stringTemp, getString(R.string.сelsius)));

        final int humidity = main.getHumidity();
        final String stringHumidity = String.valueOf(humidity);
        mHumidity.setText(String.format("%s %s%s", getString(R.string.humidity), stringHumidity, getString(R.string.percent)));

        final Wind wind = body.getWind();
        final double speed = wind.getSpeed();
        final String stringSpeed = String.valueOf(speed);
        mWind.setText(String.format("%s %s%s", getString(R.string.wind), stringSpeed, getString(R.string.m_sec)));
    }

    private int setIcon(int weatherID, double temp) {
        final int resId;
        if (weatherID > 800 && weatherID < 900) {
            resId = R.drawable.cloudiness;
            mLinearLayout.setBackgroundResource(R.drawable.bcg_cloud);
        } else if (weatherID >= 700 && weatherID < 800) {
            resId = R.drawable.fog;
            mLinearLayout.setBackgroundResource(R.drawable.bcg_mist);
        } else if (weatherID >= 300 && weatherID < 600) {
            resId = R.drawable.rain;
            mLinearLayout.setBackgroundResource(R.drawable.bcg_rain);
        } else if (weatherID >= 600 && weatherID < 700) {
            resId = R.drawable.snow;
            mLinearLayout.setBackgroundResource(R.drawable.bcg_snow);
        } else if (weatherID == 800) {
            resId = R.drawable.sun;
            if (temp <= 0) {
                mLinearLayout.setBackgroundResource(R.drawable.bcg_sun_winter);
            } else {
                mLinearLayout.setBackgroundResource(R.drawable.bcg_sun_summer);
            }
        } else if (weatherID >= 200 && weatherID < 300) {
            resId = R.drawable.thunderstorm;
            mLinearLayout.setBackgroundResource(R.drawable.bcg_thundersterm);
        } else {
            resId = R.drawable.cloudiness;
            mLinearLayout.setBackgroundResource(R.drawable.bcg_cloud);
        }
        return resId;
    }
}

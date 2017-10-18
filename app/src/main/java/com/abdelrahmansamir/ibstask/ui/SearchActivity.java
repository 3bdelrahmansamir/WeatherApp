package com.abdelrahmansamir.ibstask.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abdelrahmansamir.ibstask.R;
import com.abdelrahmansamir.ibstask.adapters.CitiesRecyclerView;
import com.abdelrahmansamir.ibstask.citiesData.CityWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_infromations);

        String cityName = getIntent().getExtras().getString("CitySearch");

        (getSupportActionBar()).setElevation(0);
        SharedPreferences jsonCitiesWeather = this.getSharedPreferences("CitiesWeather", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonArrayCitiesWeather = jsonCitiesWeather.getString("JSONCities", null);
        Type type = new TypeToken<ArrayList<CityWeather>>() {
        }.getType();

        ArrayList<CityWeather> citiesWeatherCheck = gson.fromJson(jsonArrayCitiesWeather, type);
        ArrayList<CityWeather> citiesWeather = new ArrayList<>();

        for (int i = 0; i < citiesWeatherCheck.size(); i++) {

            if (citiesWeatherCheck.get(i).getCityName().toLowerCase().contains(cityName)) {
                citiesWeather.add(citiesWeatherCheck.get(i));
            }

        }


        if (citiesWeather.size() != 0) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            CitiesRecyclerView citiesRecyclerView = new CitiesRecyclerView(this, citiesWeather);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.city_recycler_view);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(citiesRecyclerView);

        } else {

            TextView noCityFound = (TextView) findViewById(R.id.no_city_found);
            noCityFound.setVisibility(View.VISIBLE);

        }
    }
}

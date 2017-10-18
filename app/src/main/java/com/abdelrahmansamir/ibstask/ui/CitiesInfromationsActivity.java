package com.abdelrahmansamir.ibstask.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelrahmansamir.ibstask.R;
import com.abdelrahmansamir.ibstask.adapters.CitiesRecyclerView;
import com.abdelrahmansamir.ibstask.citiesData.CityWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CitiesInfromationsActivity extends AppCompatActivity {

    TextView cityName, cityCurrentTemperature, cityMaxTemperature, cityMinTemperature, cityHumidity,
            cityPressure, cityWindSpeed, cityWindDegree, cityWeatherDescription;

    PopupWindow showCityWeatherInfo;
    View layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_infromations);
        (getSupportActionBar()).setElevation(0);
        SharedPreferences jsonCitiesWeather = this.getSharedPreferences("CitiesWeather", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonArrayCitiesWeather = jsonCitiesWeather.getString("JSONCities", null);
        Type type = new TypeToken<ArrayList<CityWeather>>() {
        }.getType();
        ArrayList<CityWeather> citiesWeather = gson.fromJson(jsonArrayCitiesWeather, type);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        CitiesRecyclerView citiesRecyclerView = new CitiesRecyclerView(this, citiesWeather);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.city_recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(citiesRecyclerView);

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main, menu);


        MenuItem item = menu.findItem(R.id.search);

        final SearchView sv = (SearchView) item.getActionView();


        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);


        final TextView textView = (TextView) sv.findViewById(id);


        textView.setHint("Search For City");


        textView.setTextColor(ContextCompat.getColor(this, R.color.white));


        textView.setHintTextColor(ContextCompat.getColor(this, R.color.white));


        textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchWord) {

                sv.onActionViewCollapsed();

                Intent searchActivity = new Intent(CitiesInfromationsActivity.this, SearchActivity.class);
                searchActivity.putExtra("CitySearch", searchWord);
                CitiesInfromationsActivity.this.startActivity(searchActivity);

                textView.setText("");

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}

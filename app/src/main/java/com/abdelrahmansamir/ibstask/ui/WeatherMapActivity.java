package com.abdelrahmansamir.ibstask.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.abdelrahmansamir.ibstask.R;
import com.abdelrahmansamir.ibstask.citiesData.CityWeather;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WeatherMapActivity extends AppCompatActivity {


    MapView mapView;
    GoogleMap googleMap;
    ArrayList<CityWeather> citiesWeather;

    TextView cityName, cityCurrentTemperature, cityMaxTemperature, cityMinTemperature, cityHumidity,
            cityPressure, cityWindSpeed, cityWindDegree, cityWeatherDescription;

    PopupWindow showCityWeatherInfo;
    View layout;

    Marker clickedMarker;
    long cameraMoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_map);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) WeatherMapActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = width - 100;
        layout = inflater.inflate(R.layout.popup_layout,
                (ViewGroup) findViewById(R.id.popup_element));
        showCityWeatherInfo = new PopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        showCityWeatherInfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));

        cityName = (TextView) layout.findViewById(R.id.tv_city_name);
        cityCurrentTemperature = (TextView) layout.findViewById(R.id.tv_city_temperature);
        cityMaxTemperature = (TextView) layout.findViewById(R.id.tv_city_max_temperature);
        cityMinTemperature = (TextView) layout.findViewById(R.id.tv_city_min_temperature);
        cityHumidity = (TextView) layout.findViewById(R.id.tv_city_humidity);
        cityPressure = (TextView) layout.findViewById(R.id.tv_city_pressure);
        cityWindSpeed = (TextView) layout.findViewById(R.id.tv_city_wind_speed);
        cityWindDegree = (TextView) layout.findViewById(R.id.tv_city_wind_degree);
        cityWeatherDescription = (TextView) layout.findViewById(R.id.tv_city_weather_description);

        SharedPreferences jsonCitiesWeather = this.getSharedPreferences("CitiesWeather", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonArrayCitiesWeather = jsonCitiesWeather.getString("JSONCities", null);
        Type type = new TypeToken<ArrayList<CityWeather>>() {
        }.getType();

        citiesWeather = gson.fromJson(jsonArrayCitiesWeather, type);


        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                WeatherMapActivity.this.googleMap = googleMap;
                googleMap.clear();
                LatLng latLng = new LatLng(26.753851, 30.608208);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6));

                for (int i = 0; i < citiesWeather.size(); i++) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(citiesWeather.get(i).getCityLocationLat(), citiesWeather.get(i).getCityLocationLon()))
                            .title(citiesWeather.get(i).getCityName() + " : " + citiesWeather.get(i).getCityCurrentTemperature());
                    googleMap.addMarker(markerOptions);
                }

                googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        cameraMoved++;
                    }
                });
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {

                        for (int i = 0; i < citiesWeather.size(); i++) {

                            if (marker.getTitle().contains(citiesWeather.get(i).getCityName())) {

                                cityName.setText("City : " + citiesWeather.get(i).getCityName());
                                cityCurrentTemperature.setText("Temperature : " + citiesWeather.get(i).getCityCurrentTemperature());
                                cityMaxTemperature.setText("Max Temperature : " + citiesWeather.get(i).getCityMaxTemperature());
                                cityMinTemperature.setText("Min Temperature : " + citiesWeather.get(i).getCityMinTemperature());
                                cityHumidity.setText("Humidity : " + citiesWeather.get(i).getCityHumidity());
                                cityPressure.setText("Pressure : " + citiesWeather.get(i).getCityPressure());
                                cityWindSpeed.setText("Wind Speed : " + citiesWeather.get(i).getCityWindSpeed());
                                cityWindDegree.setText("Wind Degree : " + citiesWeather.get(i).getCityWindDegree());
                                cityWeatherDescription.setText("Description : " + citiesWeather.get(i).getCityWeatherDescription());

                                Thread runMoveCamera = new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {

                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showCityWeatherInfo.showAtLocation(layout, Gravity.CENTER, 0, 0);
                                            }
                                        });

                                    }
                                });


                                if (clickedMarker == null || !clickedMarker.equals(marker) || cameraMoved > 2) {
                                    runMoveCamera.start();
                                    WeatherMapActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(citiesWeather.get(i).getCityLocationLat(), citiesWeather.get(i).getCityLocationLon()), 6));

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {

                                            }
                                            cameraMoved = 0;
                                            clickedMarker = marker;
                                        }
                                    }).start();

                                } else {

                                    showCityWeatherInfo.showAtLocation(layout, Gravity.CENTER, 0, 0);

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(500);
                                            } catch (InterruptedException e) {

                                            }
                                            cameraMoved = 0;
                                        }
                                    }).start();

                                }


                                break;
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

}

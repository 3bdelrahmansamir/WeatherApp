package com.abdelrahmansamir.ibstask.citiesData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;

import com.abdelrahmansamir.ibstask.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Abdelrahman Samir on 15/10/2017.
 */

public class GetCitiesData {

    private ArrayList<CityWeather> citiesWeather;
    private String[] citiesNames;
    private Context dataContext;
    private SharedPreferences citiesWeatherOffline;
    boolean checkData = true;
    int citiesCount;
    int getDataAll;


    public GetCitiesData(Context dataContext) {

        this.dataContext = dataContext;
        citiesNames = dataContext.getResources().getStringArray(R.array.cities_Name);
        citiesCount = citiesNames.length;
        citiesWeather = new ArrayList<>();
        for (int i = 0; i < citiesCount; i++) {
            citiesWeather.add(null);
        }
        citiesWeatherOffline = this.dataContext.getSharedPreferences("CitiesWeather", Context.MODE_PRIVATE);
        getDataFromAPi(citiesNames);

    }

    public void getDataFromAPi(String... cities) {

        final ProgressDialog progressDialogWait = ProgressDialog.show(dataContext, "", "Please Wait");

        for (int i = 0; i < cities.length; i++) {

            final int finalI = i;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + cities[i] + ",EG&mode=json&units=metric&cnt=1&appid=3669fde887b3e20df90df2065298896b", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        CityWeather cityWeather = new CityWeather();
                        cityWeather.setCityName(jsonObject.getJSONObject("city").getString("name"));
                        cityWeather.setCityLocationLon(jsonObject.getJSONObject("city").getJSONObject("coord").getDouble("lon"));
                        cityWeather.setCityLocationLat(jsonObject.getJSONObject("city").getJSONObject("coord").getDouble("lat"));
                        cityWeather.setCityCurrentTemperature(jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("temp").getDouble("day"));
                        cityWeather.setCityMaxTemperature(jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("temp").getDouble("max"));
                        cityWeather.setCityMinTemperature(jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("temp").getDouble("min"));
                        cityWeather.setCityHumidity(jsonObject.getJSONArray("list").getJSONObject(0).getDouble("humidity"));
                        cityWeather.setCityPressure(jsonObject.getJSONArray("list").getJSONObject(0).getDouble("pressure"));
                        cityWeather.setCityWeatherDescription(jsonObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"));
                        cityWeather.setCityWindDegree(jsonObject.getJSONArray("list").getJSONObject(0).getDouble("deg"));
                        cityWeather.setCityWindSpeed(jsonObject.getJSONArray("list").getJSONObject(0).getDouble("speed"));

                        citiesWeather.set(finalI, cityWeather);

                        getDataAll++;
                        if (getDataAll == citiesCount) {
                            progressDialogWait.dismiss();
                            if (!checkData) {
                                showOfflineDialog().show();
                            } else {

                                Gson gson = new Gson();
                                String citiesWeatherJson = gson.toJson(citiesWeather);
                                SharedPreferences.Editor citiesWeatherOfflineEdit = citiesWeatherOffline.edit();
                                citiesWeatherOfflineEdit.putString("JSONCities", citiesWeatherJson);
                                citiesWeatherOfflineEdit.commit();
                                Log.e("CitiesData", citiesWeatherJson);

                            }
                        }
                    } catch (JSONException e) {

                        getDataAll++;
                        checkData = false;
                        if (getDataAll == citiesCount) {
                            progressDialogWait.dismiss();
                            showOfflineDialog().show();
                        }

                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getDataAll++;
                    checkData = false;
                    if (getDataAll == citiesCount) {
                        progressDialogWait.dismiss();
                        showOfflineDialog().show();
                    }
                }
            });

            Volley.newRequestQueue(dataContext).add(stringRequest);
        }

    }


    public SharedPreferences getCitiesWeatherOffline() {
        return citiesWeatherOffline;
    }


    public AlertDialog showOfflineDialog() {

        final AlertDialog offlineDialog = new AlertDialog.Builder(dataContext).create();
        offlineDialog.setMessage("Connection Error ... Try Again ?");
        offlineDialog.setCancelable(false);
        offlineDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                dialogInterface.dismiss();

                getDataAll = 0;
                checkData = true;
                citiesWeather = new ArrayList<>();

                for (int i = 0; i < citiesCount; i++) {
                    citiesWeather.add(null);
                }

                getDataFromAPi(citiesNames);
            }
        });

        offlineDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getCitiesWeatherOffline().getString("JSONCities", "No").equals("No") ? "Close App" : "Continue Offline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (getCitiesWeatherOffline().getString("JSONCities", "No").equals("No")) {
                    ((Activity) dataContext).finish();
                } else {
                    dialogInterface.dismiss();
                }


            }
        });

        offlineDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button positive = offlineDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negative = offlineDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

//                positive.setFocusable(true);
//                positive.setFocusableInTouchMode(true);
//                positive.requestFocus();

                positive.setAllCaps(false);
                negative.setAllCaps(false);
            }
        });

        return offlineDialog;
    }

}
package com.abdelrahmansamir.ibstask.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.abdelrahmansamir.ibstask.R;
import com.abdelrahmansamir.ibstask.citiesData.CityWeather;

import java.util.ArrayList;

/**
 * Created by Abdelrahman Samir on 18/10/2017.
 */

public class CitiesRecyclerView extends RecyclerView.Adapter<CitiesRecyclerView.CitiesAdapterViewHolder> {

    Context mContext;
    ArrayList<CityWeather> citiesWeather;
    TextView cityName, cityCurrentTemperature, cityMaxTemperature, cityMinTemperature, cityHumidity,
            cityPressure, cityWindSpeed, cityWindDegree, cityWeatherDescription;

    PopupWindow showCityWeatherInfo;
    View layout;

    public CitiesRecyclerView(Context mContext, ArrayList<CityWeather> citiesWeather) {

        this.mContext = mContext;
        this.citiesWeather = citiesWeather;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = width - 100;
        layout = inflater.inflate(R.layout.popup_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.popup_element));
        showCityWeatherInfo = new PopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        showCityWeatherInfo.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.popup_background));

        cityName = (TextView) layout.findViewById(R.id.tv_city_name);
        cityCurrentTemperature = (TextView) layout.findViewById(R.id.tv_city_temperature);
        cityMaxTemperature = (TextView) layout.findViewById(R.id.tv_city_max_temperature);
        cityMinTemperature = (TextView) layout.findViewById(R.id.tv_city_min_temperature);
        cityHumidity = (TextView) layout.findViewById(R.id.tv_city_humidity);
        cityPressure = (TextView) layout.findViewById(R.id.tv_city_pressure);
        cityWindSpeed = (TextView) layout.findViewById(R.id.tv_city_wind_speed);
        cityWindDegree = (TextView) layout.findViewById(R.id.tv_city_wind_degree);
        cityWeatherDescription = (TextView) layout.findViewById(R.id.tv_city_weather_description);

    }

    @Override
    public CitiesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cities_adapter, parent, false);
        CitiesAdapterViewHolder citiesAdapterViewHolder = new CitiesAdapterViewHolder(view);
        return citiesAdapterViewHolder;
    }


    @Override
    public void onBindViewHolder(CitiesAdapterViewHolder holder, final int position) {

        holder.cityName.setText("City : " + citiesWeather.get(position).getCityName());
        holder.cityTemperature.setText("Temperature : " + citiesWeather.get(position).getCityCurrentTemperature());
        holder.cityHumidity.setText("Humidity : " + citiesWeather.get(position).getCityHumidity());
        holder.cityWindSpeed.setText("Wind Speed : " + citiesWeather.get(position).getCityWindSpeed());
        holder.cityWindDegree.setText("Wind Degree : " + citiesWeather.get(position).getCityWindDegree());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityName.setText("City : " + citiesWeather.get(position).getCityName());
                cityCurrentTemperature.setText("Temperature : " + citiesWeather.get(position).getCityCurrentTemperature());
                cityMaxTemperature.setText("Max Temperature : " + citiesWeather.get(position).getCityMaxTemperature());
                cityMinTemperature.setText("Min Temperature : " + citiesWeather.get(position).getCityMinTemperature());
                cityHumidity.setText("Humidity : " + citiesWeather.get(position).getCityHumidity());
                cityPressure.setText("Pressure : " + citiesWeather.get(position).getCityPressure());
                cityWindSpeed.setText("Wind Speed : " + citiesWeather.get(position).getCityWindSpeed());
                cityWindDegree.setText("Wind Degree : " + citiesWeather.get(position).getCityWindDegree());
                cityWeatherDescription.setText("Description : " + citiesWeather.get(position).getCityWeatherDescription());

                showCityWeatherInfo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });
    }


    @Override
    public int getItemCount() {
        return citiesWeather.size();
    }


    public class CitiesAdapterViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView cityName;
        TextView cityTemperature;
        TextView cityHumidity;
        TextView cityWindSpeed;
        TextView cityWindDegree;

        public CitiesAdapterViewHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.city_layout);
            cityName = (TextView) itemView.findViewById(R.id.tv_city_name);
            cityTemperature = (TextView) itemView.findViewById(R.id.tv_city_temperature);
            cityHumidity = (TextView) itemView.findViewById(R.id.tv_city_humidity);
            cityWindSpeed = (TextView) itemView.findViewById(R.id.tv_city_wind_degree);
            cityWindDegree = (TextView) itemView.findViewById(R.id.tv_city_wind_speed);


        }
    }


}

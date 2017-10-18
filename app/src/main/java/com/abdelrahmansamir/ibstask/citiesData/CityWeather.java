package com.abdelrahmansamir.ibstask.citiesData;

import java.io.Serializable;

/**
 * Created by Abdelrahman Samir on 15/10/2017.
 */

public class CityWeather implements Serializable {

    private String cityName;
    private double cityLocationLon;
    private double cityLocationLat;
    private double cityCurrentTemperature;
    private double cityMaxTemperature;
    private double cityMinTemperature;
    private double cityHumidity;
    private double cityPressure;
    private double cityWindSpeed;
    private double cityWindDegree;
    private String cityWeatherDescription;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getCityLocationLon() {
        return cityLocationLon;
    }

    public void setCityLocationLon(double cityLocationLon) {
        this.cityLocationLon = cityLocationLon;
    }

    public double getCityLocationLat() {
        return cityLocationLat;
    }

    public void setCityLocationLat(double cityLocationLat) {
        this.cityLocationLat = cityLocationLat;
    }

    public double getCityCurrentTemperature() {
        return cityCurrentTemperature;
    }

    public void setCityCurrentTemperature(double cityCurrentTemperature) {
        this.cityCurrentTemperature = cityCurrentTemperature;
    }

    public double getCityMaxTemperature() {
        return cityMaxTemperature;
    }

    public void setCityMaxTemperature(double cityMaxTemperature) {
        this.cityMaxTemperature = cityMaxTemperature;
    }

    public double getCityMinTemperature() {
        return cityMinTemperature;
    }

    public void setCityMinTemperature(double cityMinTemperature) {
        this.cityMinTemperature = cityMinTemperature;
    }

    public double getCityHumidity() {
        return cityHumidity;
    }

    public void setCityHumidity(double cityHumidity) {
        this.cityHumidity = cityHumidity;
    }

    public double getCityPressure() {
        return cityPressure;
    }

    public void setCityPressure(double cityPressure) {
        this.cityPressure = cityPressure;
    }

    public double getCityWindSpeed() {
        return cityWindSpeed;
    }

    public void setCityWindSpeed(double cityWindSpeed) {
        this.cityWindSpeed = cityWindSpeed;
    }

    public double getCityWindDegree() {
        return cityWindDegree;
    }

    public void setCityWindDegree(double cityWindDegree) {
        this.cityWindDegree = cityWindDegree;
    }

    public String getCityWeatherDescription() {
        return cityWeatherDescription;
    }

    public void setCityWeatherDescription(String cityWeatherDescription) {
        this.cityWeatherDescription = cityWeatherDescription;
    }
}

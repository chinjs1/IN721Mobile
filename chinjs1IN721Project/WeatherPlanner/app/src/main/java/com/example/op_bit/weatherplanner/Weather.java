package com.example.op_bit.weatherplanner;

/**
 * Created by CHINJS1 on 30/05/2017.
 */

public class Weather {

    private String date;
    private double temperature;
    private double humidity;
    private double pressure;
    private double windSpeed;

    @Override
    public String toString() { return date; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}

package com.example.vremenskaprognoza;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastResult {

    public double lat;
    public double lon;
    public String timezone;
    public int timezone_offset;
    public ArrayList<Daily> daily = new ArrayList<Daily>();

}

class Daily
{
    public int dt;
    public int sunrise;
    public int sunset;
    public int moonrise;
    public int moonset;
    public double moon_phase;
    public Temp temp;
    public Feels_like feels_like;
    public int pressure;
    public int humidity;
    public double dew_point;
    public double wind_speed;
    public int wind_deg;
    public double wind_gust;
    public ArrayList<Weather> weather = new ArrayList<Weather>();
    public int clouds;
    public double pop;
    public double rain;
    public double uvi;
    public double snow;
}

class Temp
{
    public float day;
    public float min;
    public float max;
    public float night;
    public float eve;
    public float morn;
}

class Feels_like
{
    public float day;
    public float night;
    public float eve;
    public float morn;
}
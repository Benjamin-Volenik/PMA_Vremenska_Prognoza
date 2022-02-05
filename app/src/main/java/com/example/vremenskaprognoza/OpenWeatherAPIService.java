package com.example.vremenskaprognoza;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherAPIService {

    @GET("data/2.5/weather?appid=22b8bc4d2a0f0002deb829b0f239d551&units=metric&lang=hr")
    Call<OpenWeatherResponse> getCurrentWeatherData(@Query("q") String ImeGrada);


    @GET("data/2.5/onecall?exclude=hourly,minutely&appid=22b8bc4d2a0f0002deb829b0f239d551&units=metric&lang=hr")
    Call<WeatherForecastResult> getForecast(@Query("lat") String lat,@Query("lon") String lon);

}

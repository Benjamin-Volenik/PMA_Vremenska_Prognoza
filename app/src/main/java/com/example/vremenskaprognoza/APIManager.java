package com.example.vremenskaprognoza;

import android.graphics.Path;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    static APIManager instance;
    private OpenWeatherAPIService service;
    private APIManager(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(OpenWeatherAPIService.class);
    }
    public static APIManager getInstance(){
        if (instance == null){
            instance = new APIManager();
        }
        return instance;
    }
    public OpenWeatherAPIService service(){

        return service;
    }
}

package com.example.vremenskaprognoza;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getGeoApiService {

    static getGeoApiService instance;
    private LocationApiService service;
    private getGeoApiService(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("http://ip-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(LocationApiService.class);
    }
    public static getGeoApiService getInstance(){
        if (instance == null){
            instance = new getGeoApiService();
        }
        return instance;
    }
    public LocationApiService service(){

        return service;
    }

}

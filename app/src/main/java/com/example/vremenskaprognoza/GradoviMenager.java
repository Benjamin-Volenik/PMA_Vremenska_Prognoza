package com.example.vremenskaprognoza;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GradoviMenager {

    static GradoviMenager instance;
    private GradoviAPIService service;
    private GradoviMenager(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("https://countriesnow.space/api/v0.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GradoviAPIService.class);
    }
    public static GradoviMenager getInstance(){
        if (instance == null){
            instance = new GradoviMenager();
        }
        return instance;
    }
    public GradoviAPIService service(){

        return service;
    }
}

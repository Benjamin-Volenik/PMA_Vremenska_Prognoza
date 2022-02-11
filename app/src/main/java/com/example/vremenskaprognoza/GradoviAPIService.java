package com.example.vremenskaprognoza;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GradoviAPIService {

    @GET("countries/population/cities")
    Call<GradoviResponse> getCities();

}

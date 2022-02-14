package com.example.vremenskaprognoza;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationApiService {

    @GET("json")
    Call<GeoResponse> getLocation();

}

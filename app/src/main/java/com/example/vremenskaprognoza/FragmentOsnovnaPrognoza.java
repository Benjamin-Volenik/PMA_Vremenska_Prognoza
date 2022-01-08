package com.example.vremenskaprognoza;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentOsnovnaPrognoza extends Fragment {

    TextView txtTest;
    View view;
    OpenWeatherAPIService service = APIManager.getInstance().service();
    public static String Zagreb = "Zagreb";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_osnovna_prognoza, container, false);

        txtTest = view.findViewById(R.id.textView1);
        Call<OpenWeatherResponse> call = service.getCurrentWeatherData(Zagreb);
        call.enqueue(new Callback<OpenWeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<OpenWeatherResponse> call, @NonNull Response<OpenWeatherResponse> response) {
                if (response.code() == 200) {
                    OpenWeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String stringBuilder = "Country: " +
                            weatherResponse.sys.country +
                            "\n" +
                            "Temperature: " +
                            weatherResponse.main.temp +
                            "\n" +
                            "Temperature(Min): " +
                            weatherResponse.main.temp_min +
                            "\n" +
                            "Temperature(Max): " +
                            weatherResponse.main.temp_max +
                            "\n" +
                            "Humidity: " +
                            weatherResponse.main.humidity +
                            "\n" +
                            "Pressure: " +
                            weatherResponse.main.pressure;

                    txtTest.setText(stringBuilder);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherResponse> call, @NonNull Throwable t) {
                txtTest.setText(t.getMessage());
            }
        });
        return view;
    }

}

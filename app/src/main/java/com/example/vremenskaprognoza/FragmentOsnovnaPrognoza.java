package com.example.vremenskaprognoza;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentOsnovnaPrognoza extends Fragment implements LocationListener {

    protected LocationManager locationManager;
    TextView txtLokacija, txtNebo, txtTemp, txtZemlja;
    View view;
    String Lokacija;
    OpenWeatherAPIService service = APIManager.getInstance().service();

    public FragmentOsnovnaPrognoza() {
        // Required empty public constructor
    }

    public static FragmentOsnovnaPrognoza newInstance(String param1, String param2) {
        FragmentOsnovnaPrognoza fragment = new FragmentOsnovnaPrognoza();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        if(getActivity()!= null) {
            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());

            StringBuilder builder = new StringBuilder();
            try {
                List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
                for (int i = 0; i < 1; i++) {
                    String addressStr = address.get(0).getLocality();
                    builder.append(addressStr);
                    builder.append(" ");
                }

                Lokacija = builder.toString();

            } catch (IOException e) {
                // Handle IOException
            } catch (NullPointerException e) {
                // Handle NullPointerException
            }
        }

        Call<OpenWeatherResponse> call = service.getCurrentWeatherData(Lokacija);
        call.enqueue(new Callback<OpenWeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<OpenWeatherResponse> call, @NonNull Response<OpenWeatherResponse> response) {
                if (response.code() == 200) {
                    OpenWeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;


                    txtLokacija.setText(weatherResponse.name);
                    txtZemlja.setText(weatherResponse.sys.country);
                    txtNebo.setText(weatherResponse.weather.get(0).description.toUpperCase());
                    txtTemp.setText(Float.toString(weatherResponse.main.temp) + "°C");
                }
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherResponse> call, @NonNull Throwable t) {
                txtLokacija.setText(t.getMessage());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_osnovna_prognoza, container, false);

        txtLokacija= view.findViewById(R.id.txtLokacija);
        txtNebo = view.findViewById(R.id.txtNebo);
        txtTemp = view.findViewById(R.id.txtTemperatura);
        txtZemlja = view.findViewById(R.id.txtZemlja);

        return view;
    }
}

package com.example.vremenskaprognoza;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.function.IntToLongFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentOsnovnaPrognoza extends Fragment implements LocationListener {

    protected LocationManager locationManager;
    TextView txtLokacija, txtNebo, txtTemp, txtZemlja;
    View view;
    String Lokacija,Nebo,Temperatura,MinTemp,MaxTemp,Zemlja,Ikona;
    OpenWeatherAPIService service = APIManager.getInstance().service();
    FragmentListener mCallback;
    ImageView imageview;
    Bitmap mIcon_val;
    URL newurl;
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

                    Nebo = weatherResponse.weather.get(0).description.toUpperCase();
                    Temperatura = Float.toString((weatherResponse.main.temp));
                    MaxTemp = Float.toString((weatherResponse.main.temp_max));
                    MinTemp = Float.toString((weatherResponse.main.temp_min));
                    Zemlja = weatherResponse.sys.country;
                    Ikona = weatherResponse.weather.get(0).icon;
                    try {
                        newurl = new URL("http://openweathermap.org/img/w/" + Ikona + ".png");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    if(getActivity()!= null) {
                        Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageview);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherResponse> call, @NonNull Throwable t) {
                txtLokacija.setText(t.getMessage());
            }
        });
        mCallback.setLokacija(Lokacija);
        mCallback.setNebo(Nebo);
        mCallback.setTemp(Temperatura);
        mCallback.setMaxTemp(MaxTemp);
        mCallback.setMintemp(MinTemp);
        mCallback.setZemlja(Zemlja);
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
        imageview = view.findViewById(R.id.imgIkona);


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }
}

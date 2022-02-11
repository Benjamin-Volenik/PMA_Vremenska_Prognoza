package com.example.vremenskaprognoza;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.IntToLongFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentOsnovnaPrognoza extends Fragment implements LocationListener {

    protected LocationManager locationManager;
    TextView txtLokacija, txtNebo, txtTemp, txtAzurirano,txtStvarniOsjecaj;
    View view;
    String Lokacija,Nebo,Temperatura,MinTemp,MaxTemp,Zemlja,Ikona;
    OpenWeatherAPIService service = APIManager.getInstance().service();
    FragmentListener mCallback;
    ImageView imageview;
    Bitmap mIcon_val;
    URL newurl;
    Button button;
    String DobijenaLokacija;
    //GradoviAPIService gradoviAPIService = GradoviMenager.getInstance().service();
    //List<String> where = new ArrayList<String>();
    AutoCompleteTextView textView;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    String[] split;

    private String text;

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);

    }
    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        if (getActivity() != null) {
            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());

            StringBuilder builder = new StringBuilder();
            try {
                List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
                for (int i = 0; i < 1; i++) {
                    String addressStr = address.get(0).getLocality();
                    builder.append(addressStr);
                }

                if (DobijenaLokacija == null){
                    Lokacija = builder.toString();
                }
                else
                {
                    Lokacija = DobijenaLokacija;
                }

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


                    txtLokacija.setText(weatherResponse.name + " - " + weatherResponse.sys.country);
                    Date expiry = new Date((long) weatherResponse.dt * 1000L);
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy / HH:mm");
                    String strDate = dateFormat.format(expiry);
                    txtAzurirano.setText("Ažurirano " + strDate + "h");
                    txtNebo.setText(weatherResponse.weather.get(0).description.toUpperCase());
                    txtTemp.setText(Float.toString(weatherResponse.main.temp) + "°C");
                    txtStvarniOsjecaj.setText("Stvarni osjećaj: " + Float.toString(weatherResponse.main.feels_like) + "°C");

                    Nebo = weatherResponse.weather.get(0).description.toUpperCase();
                    Temperatura = Float.toString((weatherResponse.main.temp));
                    MaxTemp = Float.toString((weatherResponse.main.temp_max));
                    MinTemp = Float.toString((weatherResponse.main.temp_min));
                    Zemlja = weatherResponse.sys.country;
                    mCallback.setIzlazakSunca(weatherResponse.sys.sunrise);
                    mCallback.setZalazakSunca(weatherResponse.sys.sunset);
                    mCallback.setVjetar(Float.toString(weatherResponse.wind.speed));
                    mCallback.setPritisak(Integer.toString(weatherResponse.main.pressure));
                    mCallback.setVlaznost(Integer.toString(weatherResponse.main.humidity));
                    if(weatherResponse.rain != null)
                    {
                        mCallback.setKisa(Objects.requireNonNull(weatherResponse.rain.h1));
                    }
                    else
                    {
                        mCallback.setKisa(Float.parseFloat("0"));
                    }
                    Ikona = weatherResponse.weather.get(0).icon;

                    mCallback.setLokacija(Lokacija);
                    mCallback.setNebo(Nebo);
                    mCallback.setTemp(Temperatura);
                    mCallback.setMaxTemp(MaxTemp);
                    mCallback.setMintemp(MinTemp);
                    mCallback.setZemlja(Zemlja);
                    mCallback.setIkona(Ikona);
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_osnovna_prognoza, container, false);

        txtLokacija= view.findViewById(R.id.txtLokacija);
        txtNebo = view.findViewById(R.id.txtNebo);
        txtTemp = view.findViewById(R.id.txtTemperatura);
        txtAzurirano = view.findViewById(R.id.txtAzurirano);
        txtStvarniOsjecaj = view.findViewById(R.id.textStvarniOsjecaj);
        imageview = view.findViewById(R.id.imgIkona);
        button  = view.findViewById(R.id.button2);

       /* Call <GradoviResponse> call = gradoviAPIService.getCities();
        call.enqueue(new Callback<GradoviResponse>() {
            @Override
            public void onResponse(@NonNull Call<GradoviResponse> call, @NonNull Response<GradoviResponse> response) {
                if (response.code() == 200) {
                    GradoviResponse gradoviResponse = response.body();
                    assert gradoviResponse != null;
                    for(int i = 0; i < gradoviResponse.data.size(); i++){
                        where.add(gradoviResponse.data.get(i).city);
                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<GradoviResponse> call, @NonNull Throwable t) {
            }
        });*/

        loadData();
        updateViews();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line , split);
        textView = view.findViewById(R.id.autoCompleteNaizvGrada);
        textView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DobijenaLokacija = textView.getText().toString();
                if(DobijenaLokacija.contains("(")) {
                    DobijenaLokacija = DobijenaLokacija.substring(0,DobijenaLokacija.indexOf("("));
                }
                getInfo();
                saveData();
            }
        });


        return view;
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        text = sharedPreferences.getString(TEXT,"");
        if(!text.contains(textView.getText().toString()))
        {
            editor.putString(TEXT,textView.getText().toString() + "," + text );
            editor.apply();
        }

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT,"");
        split = text.split(",");
    }

    public void updateViews(){

    }

    public void getInfo(){

        if(DobijenaLokacija == null){
            DobijenaLokacija = mCallback.getLokacija();
        }

        Lokacija = DobijenaLokacija;
        Call<OpenWeatherResponse> call = service.getCurrentWeatherData(Lokacija);
        call.enqueue(new Callback<OpenWeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<OpenWeatherResponse> call, @NonNull Response<OpenWeatherResponse> response) {
                if (response.code() == 200) {
                    OpenWeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;


                    txtLokacija.setText(weatherResponse.name + " - " + weatherResponse.sys.country);
                    Date expiry = new Date((long) weatherResponse.dt * 1000L);
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy / HH:mm");
                    String strDate = dateFormat.format(expiry);
                    txtAzurirano.setText("Ažurirano " + strDate + "h");
                    txtNebo.setText(weatherResponse.weather.get(0).description.toUpperCase());
                    txtTemp.setText(Float.toString(weatherResponse.main.temp) + "°C");
                    txtStvarniOsjecaj.setText("Stvarni osjećaj: " + Float.toString(weatherResponse.main.feels_like) + "°C");

                    Nebo = weatherResponse.weather.get(0).description.toUpperCase();
                    Temperatura = Float.toString((weatherResponse.main.temp));
                    MaxTemp = Float.toString((weatherResponse.main.temp_max));
                    MinTemp = Float.toString((weatherResponse.main.temp_min));
                    Zemlja = weatherResponse.sys.country;
                    mCallback.setIzlazakSunca(weatherResponse.sys.sunrise);
                    mCallback.setZalazakSunca(weatherResponse.sys.sunset);
                    mCallback.setVjetar(Float.toString(weatherResponse.wind.speed));
                    mCallback.setPritisak(Integer.toString(weatherResponse.main.pressure));
                    mCallback.setVlaznost(Integer.toString(weatherResponse.main.humidity));
                    if(weatherResponse.rain != null)
                    {
                        mCallback.setKisa(Objects.requireNonNull(weatherResponse.rain.h1));
                    }
                    else
                    {
                        mCallback.setKisa(Float.parseFloat("0"));
                    }
                    Ikona = weatherResponse.weather.get(0).icon;

                    mCallback.setLokacija(weatherResponse.name);
                    mCallback.setNebo(Nebo);
                    mCallback.setTemp(Temperatura);
                    mCallback.setMaxTemp(MaxTemp);
                    mCallback.setMintemp(MinTemp);
                    mCallback.setZemlja(Zemlja);
                    mCallback.setIkona(Ikona);
                    try {
                        newurl = new URL("http://openweathermap.org/img/w/" + Ikona + ".png");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    if(getActivity()!= null) {
                        Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageview);
                    }
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Greška");
                    alertDialog.setMessage("Grad nije pronađen");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "U redu",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<OpenWeatherResponse> call, @NonNull Throwable t) {
                txtLokacija.setText(t.getMessage());
            }
        });

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

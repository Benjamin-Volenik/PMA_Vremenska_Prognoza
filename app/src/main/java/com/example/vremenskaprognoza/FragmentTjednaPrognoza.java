package com.example.vremenskaprognoza;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTjednaPrognoza extends Fragment {

    View view;
    TextView txtpon,txtuto,txtsri,txtcet,txtpet,txtsub,txtned,getTxtpon,getTxtuto,getTxtsri,getTxtcet,getTxtpet,getTxtsub,getTxtned;
    TextView InfoPon,InfoUto,InfoSri,InfoCet,InfoPet,InfoSub,InfoNed;
    FragmentListener mCallback;
    String Lokacija;
    String lat;
    String lon;
    String pon,uto,sri,cet,pet,sub,ned,Ikona1;
    ImageView imgpon,imguto,imgsri,imgcet,imgpet,imgsub,imgned;
    URL newurl;

    OpenWeatherAPIService service = APIManager.getInstance().service();

    public FragmentTjednaPrognoza() {
        // Required empty public constructor
    }

    public static FragmentTjednaPrognoza newInstance(String param1, String param2) {
        FragmentTjednaPrognoza fragment = new FragmentTjednaPrognoza();
        Bundle args = new Bundle();
;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Lokacija = mCallback.getLokacija();
        Locale Drzava = new Locale("", mCallback.getZemlja());
        String country = Drzava.getDisplayCountry();
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> ads = geoCoder.getFromLocationName(Lokacija + "," + country,1);
            lat = String.valueOf(ads.get(0).getLatitude());
            lon = String.valueOf(ads.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Call<WeatherForecastResult> call = service.getForecast(lat,lon);
        call.enqueue(new Callback<WeatherForecastResult>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecastResult> call, @NonNull Response<WeatherForecastResult> response) {
                if (response.code() == 200) {
                    WeatherForecastResult weatherResponse = response.body();
                    assert weatherResponse != null;

                    Date expiry = new Date(weatherResponse.daily.get(1).dt * 1000L);
                    Date expiry2 = new Date(weatherResponse.daily.get(2).dt * 1000L);
                    Date expiry3 = new Date(weatherResponse.daily.get(3).dt * 1000L);
                    Date expiry4 = new Date(weatherResponse.daily.get(4).dt * 1000L);
                    Date expiry5 = new Date(weatherResponse.daily.get(5).dt * 1000L);
                    Date expiry6 = new Date(weatherResponse.daily.get(6).dt * 1000L);
                    Date expiry7 = new Date(weatherResponse.daily.get(7).dt * 1000L);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("hr", "HR"));
                    String dayOfTheWeek = sdf.format(expiry);
                    String dayOfTheWeek2 = sdf.format(expiry2);
                    String dayOfTheWeek3 = sdf.format(expiry3);
                    String dayOfTheWeek4 = sdf.format(expiry4);
                    String dayOfTheWeek5 = sdf.format(expiry5);
                    String dayOfTheWeek6 = sdf.format(expiry6);
                    String dayOfTheWeek7 = sdf.format(expiry7);

                    getTxtpon.setText(dayOfTheWeek.substring(0, 1).toUpperCase() + dayOfTheWeek.substring(1).toLowerCase());
                    getTxtuto.setText(dayOfTheWeek2.substring(0, 1).toUpperCase() + dayOfTheWeek2.substring(1).toLowerCase());
                    getTxtsri.setText(dayOfTheWeek3.substring(0, 1).toUpperCase() + dayOfTheWeek3.substring(1).toLowerCase());
                    getTxtcet.setText(dayOfTheWeek4.substring(0, 1).toUpperCase() + dayOfTheWeek4.substring(1).toLowerCase());
                    getTxtpet.setText(dayOfTheWeek5.substring(0, 1).toUpperCase() + dayOfTheWeek5.substring(1).toLowerCase());
                    getTxtsub.setText(dayOfTheWeek6.substring(0, 1).toUpperCase() + dayOfTheWeek6.substring(1).toLowerCase());
                    getTxtned.setText(dayOfTheWeek7.substring(0, 1).toUpperCase() + dayOfTheWeek7.substring(1).toLowerCase());

                    pon = Float.toString(weatherResponse.daily.get(1).temp.day) + "°C";
                    uto = Float.toString(weatherResponse.daily.get(2).temp.day) + "°C";
                    sri = Float.toString(weatherResponse.daily.get(3).temp.day) + "°C";
                    cet = Float.toString(weatherResponse.daily.get(4).temp.day) + "°C";
                    pet = Float.toString(weatherResponse.daily.get(5).temp.day) + "°C";
                    sub = Float.toString(weatherResponse.daily.get(6).temp.day) + "°C";
                    ned = Float.toString(weatherResponse.daily.get(7).temp.day) + "°C";

                    txtpon.setText(pon);
                    txtuto.setText(uto);
                    txtsri.setText(sri);
                    txtcet.setText(cet);
                    txtpet.setText(pet);
                    txtsub.setText(sub);
                    txtned.setText(ned);

                    InfoPon.setText(weatherResponse.daily.get(1).weather.get(0).description.substring(0, 1).toUpperCase() + weatherResponse.daily.get(1).weather.get(0).description.substring(1).toLowerCase());
                    InfoUto.setText(weatherResponse.daily.get(2).weather.get(0).description.substring(0, 1).toUpperCase() + weatherResponse.daily.get(2).weather.get(0).description.substring(1).toLowerCase());
                    InfoSri.setText(weatherResponse.daily.get(3).weather.get(0).description.substring(0, 1).toUpperCase() + weatherResponse.daily.get(3).weather.get(0).description.substring(1).toLowerCase());
                    InfoCet.setText(weatherResponse.daily.get(4).weather.get(0).description.substring(0, 1).toUpperCase() + weatherResponse.daily.get(4).weather.get(0).description.substring(1).toLowerCase());
                    InfoPet.setText(weatherResponse.daily.get(5).weather.get(0).description.substring(0, 1).toUpperCase() + weatherResponse.daily.get(5).weather.get(0).description.substring(1).toLowerCase());
                    InfoSub.setText(weatherResponse.daily.get(6).weather.get(0).description.substring(0, 1).toUpperCase() + weatherResponse.daily.get(6).weather.get(0).description.substring(1).toLowerCase());
                    InfoNed.setText(weatherResponse.daily.get(7).weather.get(0).description.substring(0, 1).toUpperCase() + weatherResponse.daily.get(7).weather.get(0).description.substring(1).toLowerCase());

                    for(int i = 1; i < weatherResponse.daily.size(); i++) {

                        Ikona1 = weatherResponse.daily.get(i).weather.get(0).icon;
                        try {
                            newurl = new URL("http://openweathermap.org/img/w/" + Ikona1 + ".png");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if(getActivity()!= null) {
                            if(i == 1)
                            {
                                Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgpon);
                            }
                            else if(i == 2)
                            {
                                Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imguto);
                            }
                            else if(i == 3)
                            {
                                Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgsri);
                            }
                            else if(i == 4)
                            {
                                Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgcet);
                            }
                            else if(i == 5)
                            {
                                Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgpet);
                            }
                            else if (i == 6)
                            {
                                Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgsub);
                            }
                            else
                            {
                                Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgned);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecastResult> call, @NonNull Throwable t) {
                txtpon.setText(t.getMessage());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tjedna_prognoza, container, false);

        //temperatura
        txtpon = view.findViewById(R.id.textPon);
        txtuto = view.findViewById(R.id.txtTempUto);
        txtsri = view.findViewById(R.id.txtTempSri);
        txtcet = view.findViewById(R.id.txtTempCet);
        txtpet = view.findViewById(R.id.txtTempPet);
        txtsub = view.findViewById(R.id.txtTempSub);
        txtned = view.findViewById(R.id.txtNed);

        //dan u tjednu
        getTxtpon = view.findViewById(R.id.txtPon);
        getTxtuto = view.findViewById(R.id.txtUtorak);
        getTxtsri = view.findViewById(R.id.txtSrijeda);
        getTxtcet = view.findViewById(R.id.txtCetvratk);
        getTxtpet = view.findViewById(R.id.txtPetak);
        getTxtsub = view.findViewById(R.id.txtSubota);
        getTxtned = view.findViewById(R.id.txtNedjelja);

        InfoPon = view.findViewById(R.id.textViewNebo1);
        InfoUto = view.findViewById(R.id.textViewNebo2);
        InfoSri = view.findViewById(R.id.textViewNebo3);
        InfoCet = view.findViewById(R.id.textViewNebo4);
        InfoPet = view.findViewById(R.id.textViewNebo5);
        InfoSub = view.findViewById(R.id.textViewNebo6);
        InfoNed = view.findViewById(R.id.textViewNebo7);

        imgpon = view.findViewById(R.id.imageViewPon);
        imguto = view.findViewById(R.id.imageViewUto);
        imgsri = view.findViewById(R.id.imageViewSri);
        imgcet = view.findViewById(R.id.imageViewCet);
        imgpet = view.findViewById(R.id.imageViewPet);
        imgsub = view.findViewById(R.id.imageViewSub);
        imgned = view.findViewById(R.id.imageViewNed);




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
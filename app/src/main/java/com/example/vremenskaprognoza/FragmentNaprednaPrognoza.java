package com.example.vremenskaprognoza;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNaprednaPrognoza extends Fragment implements Serializable {

    View view;
    TextView NaprednaLokacija,NaprednaNebo,NaprednaTemp,NaprednaMaxTemp,NaprednaMinTemp,NaprednaIzlazakSunca,NaprednaZalazakSunca,NaprednaVjetar,NaprednaPritisak,NaprednaVlaznost;
    FragmentListener mCallback;
    String Lokacija,Nebo,Temperatura,MinTemp,MaxTemp,Zemlja,IzlazakSunca,ZalazakSunca,Vjetar,Pritisak,Vlaznost,Ikona;
    URL newurl;
    ImageView imgIkona;



    public FragmentNaprednaPrognoza() {
        // Required empty public constructor
    }

    public static FragmentNaprednaPrognoza newInstance(String param1, String param2) {
        FragmentNaprednaPrognoza fragment = new FragmentNaprednaPrognoza();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Lokacija = mCallback.getLokacija();
        Nebo = mCallback.getNebo();
        Temperatura = mCallback.getTemp();
        MinTemp = mCallback.getMinTemp();
        MaxTemp = mCallback.getMaxTemp();
        Zemlja = mCallback.getZemlja();
        Long longIzlazak  = mCallback.getIzlazakSunca();
        Long longZalazak  = mCallback.getZalazakSunca();
        Vjetar = mCallback.getVjetar();
        Pritisak = mCallback.getPritisak();
        Vlaznost = mCallback.getVlaznost();
        Ikona = mCallback.getIkona();
        Date izalzak = new Date(longIzlazak * 1000L);
        Date zalazak = new Date(longZalazak * 1000L);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        IzlazakSunca = dateFormat.format(izalzak);
        ZalazakSunca = dateFormat.format(zalazak);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_napredna_prognoza, container, false);

       NaprednaLokacija = view.findViewById(R.id.txtNaprednaLokacija);
       NaprednaNebo = view.findViewById(R.id.txtNaprednaNebo);
       NaprednaTemp = view.findViewById(R.id.txtNaprednaTemperatura);
       NaprednaMaxTemp = view.findViewById(R.id.txtMaxTemp);
       NaprednaMinTemp = view.findViewById(R.id.txtMinTemp);
       NaprednaIzlazakSunca = view.findViewById(R.id.txtIzlazakSunca);
       NaprednaZalazakSunca = view.findViewById(R.id.txtZalazakSunca);
       NaprednaVjetar = view.findViewById(R.id.txtVjetar);
       NaprednaPritisak = view.findViewById(R.id.txtPritisak);
       NaprednaVlaznost = view.findViewById(R.id.txtVlaznost);
       imgIkona = view.findViewById(R.id.imageViewNaprednaIkona);

        NaprednaLokacija.setText(Lokacija + "- " + Zemlja);
        NaprednaNebo.setText(Nebo);
        NaprednaTemp.setText(Temperatura + "°C");
        NaprednaMaxTemp.setText("Max temp.:" + MaxTemp + "°C");
        NaprednaMinTemp.setText("Min temp.:" + MinTemp + "°C");
        NaprednaIzlazakSunca.setText(IzlazakSunca);
        NaprednaZalazakSunca.setText(ZalazakSunca);
        NaprednaVjetar.setText(Vjetar);
        NaprednaPritisak.setText(Pritisak);
        NaprednaVlaznost.setText(Vlaznost);

        try {
            newurl = new URL("http://openweathermap.org/img/w/" + Ikona + ".png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(getActivity()!= null) {
            Glide.with(getActivity()).load(newurl).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgIkona);
        }




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
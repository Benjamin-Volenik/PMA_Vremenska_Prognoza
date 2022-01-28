package com.example.vremenskaprognoza;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNaprednaPrognoza extends Fragment implements Serializable {

    View view;
    TextView NaprednaLokacija,NaprednaNebo,NaprednaTemp,NaprednaMaxTemp,NaprednaMinTemp;
    FragmentListener mCallback;
    String Lokacija,Nebo,Temperatura,MinTemp,MaxTemp,Zemlja;


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

        NaprednaLokacija.setText(Lokacija + "- " + Zemlja);
        NaprednaNebo.setText(Nebo);
        NaprednaTemp.setText(Temperatura + "°C");
        NaprednaMaxTemp.setText("Max temp.:" + MaxTemp + "°C");
        NaprednaMinTemp.setText("Min temp.:" + MinTemp + "°C");




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
package com.example.vremenskaprognoza;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTjednaPrognoza extends Fragment {

    View view;
    TextView txtpon,txtuto,txtsri,txtcet,txtpet,txtsub,txtned;

    public FragmentTjednaPrognoza() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tjedna_prognoza, container, false);

        txtpon = view.findViewById(R.id.textPon);
        txtuto = view.findViewById(R.id.txtTempUto);
        txtsri = view.findViewById(R.id.txtTempSri);
        txtcet = view.findViewById(R.id.txtTempCet);
        txtpet = view.findViewById(R.id.txtTempPet);
        txtsub = view.findViewById(R.id.txtTempSub);
        txtned = view.findViewById(R.id.txtNed);



        return view;
    }
}
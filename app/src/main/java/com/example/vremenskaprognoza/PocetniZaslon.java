package com.example.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PocetniZaslon extends AppCompatActivity implements FragmentListener {

    private String Lokacija;
    private String Nebo;
    private String Temperatura;
    private String MinTemp;
    private String MaxTemp;
    private String Zemlja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetni_zaslon);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (navHostFragment != null) {

            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupWithNavController(bottomNavigationView,navController);

        }


    }

    @Override
    public void setLokacija(String lokacija) {
        this.Lokacija = lokacija;
    }

    @Override
    public void setNebo(String nebo) {
        this.Nebo = nebo;
    }

    @Override
    public void setTemp(String temp) {
        this.Temperatura = temp;

    }

    @Override
    public void setMaxTemp(String maxtemp) {
        this.MaxTemp = maxtemp;
    }

    @Override
    public void setMintemp(String mintemp) {
        this.MinTemp = mintemp;
    }

    @Override
    public void setZemlja(String zemlja) {
        this.Zemlja = zemlja;
    }

    @Override
    public String getLokacija() {
        return Lokacija;
    }

    @Override
    public String getNebo() {
        return Nebo;
    }

    @Override
    public String getTemp() {
        return Temperatura;
    }

    @Override
    public String getMaxTemp() {
        return MaxTemp;
    }

    @Override
    public String getMinTemp() {
        return MinTemp;
    }

    @Override
    public String getZemlja() {
        return Zemlja;
    }
}
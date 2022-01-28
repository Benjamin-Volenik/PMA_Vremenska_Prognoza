package com.example.vremenskaprognoza;

import java.io.Serializable;

public class Grad implements Serializable {

    public String lokacija;

    public Grad()
    {

    }

    public void setLokacija(String lokacijaa) {
        lokacija = lokacijaa;
    }

    public String getLokacija() {
        return lokacija;
    }
}


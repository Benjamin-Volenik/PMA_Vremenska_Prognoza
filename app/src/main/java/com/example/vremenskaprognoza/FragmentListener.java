package com.example.vremenskaprognoza;

public interface FragmentListener {

    void setLokacija(String lokacija);
    void setNebo(String nebo);
    void setTemp(String temp);
    void setMaxTemp(String maxtemp);
    void setMintemp(String mintemp);
    void setZemlja(String zemlja);


    String getLokacija();
    String getNebo();
    String getTemp();
    String getMaxTemp();
    String getMinTemp();
    String getZemlja();

}

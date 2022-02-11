package com.example.vremenskaprognoza;

public interface FragmentListener {

    void setLokacija(String lokacija);
    void setNebo(String nebo);
    void setTemp(String temp);
    void setMaxTemp(String maxtemp);
    void setMintemp(String mintemp);
    void setZemlja(String zemlja);
    void setIzlazakSunca(Long izlazaksunca);
    void setZalazakSunca(Long zalazakSunca);
    void setVjetar(String vjetar);
    void setPritisak(String pritisak);
    void setVlaznost(String vlaznost);
    void setKisa(Float kisa);
    void setIkona(String ikona);


    String getLokacija();
    String getNebo();
    String getTemp();
    String getMaxTemp();
    String getMinTemp();
    String getZemlja();
    Long getIzlazakSunca();
    Long getZalazakSunca();
    String getVjetar();
    String getPritisak();
    String getVlaznost();
    Float getKisa();
    String getIkona();

}

package com.app.yildizuniapp.models;

public class ListelemeModels {
    String soru,birinci,ikinci,ucuncu,dorduncu,dogrucevap,zorlukseviye;

    public ListelemeModels(String soru, String birinci, String ikinci, String ucuncu, String dorduncu, String dogrucevap, String zorlukseviye) {
        this.soru = soru;
        this.birinci = birinci;
        this.ikinci = ikinci;
        this.ucuncu = ucuncu;
        this.dorduncu = dorduncu;
        this.dogrucevap = dogrucevap;
        this.zorlukseviye = zorlukseviye;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getBirinci() {
        return birinci;
    }

    public void setBirinci(String birinci) {
        this.birinci = birinci;
    }

    public String getIkinci() {
        return ikinci;
    }

    public void setIkinci(String ikinci) {
        this.ikinci = ikinci;
    }

    public String getUcuncu() {
        return ucuncu;
    }

    public void setUcuncu(String ucuncu) {
        this.ucuncu = ucuncu;
    }

    public String getDorduncu() {
        return dorduncu;
    }

    public void setDorduncu(String dorduncu) {
        this.dorduncu = dorduncu;
    }

    public String getDogrucevap() {
        return dogrucevap;
    }

    public void setDogrucevap(String dogrucevap) {
        this.dogrucevap = dogrucevap;
    }

    public String getZorlukseviye() {
        return zorlukseviye;
    }

    public void setZorlukseviye(String zorlukseviye) {
        this.zorlukseviye = zorlukseviye;
    }
}

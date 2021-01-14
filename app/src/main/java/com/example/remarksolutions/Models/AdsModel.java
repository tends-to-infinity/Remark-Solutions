package com.example.remarksolutions.Models;

import java.util.Map;

public class AdsModel {

    Map<String,String> Ads;

    public AdsModel() {
       }

    public AdsModel(Map<String, String> ads) {
        Ads = ads;
    }

    public Map<String, String> getAds() {
        return Ads;
    }

    public void setAds(Map<String, String> ads) {
        Ads = ads;
    }
}

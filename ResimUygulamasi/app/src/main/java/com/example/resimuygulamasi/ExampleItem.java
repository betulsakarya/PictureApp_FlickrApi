package com.example.resimuygulamasi;

public class ExampleItem {

    private int mfarm;
    private String mserver;
    private String mid;
    private String msecret;
    private String mimageUrl;

    public ExampleItem(int farm, String server, String id, String secret) {
        mfarm = farm;
        mserver = server;
        mid = id;
        msecret = secret;

        mimageUrl = "https://farm" + mfarm + ".staticflickr.com/" + mserver + "/" + mid + "_" + msecret + "_b.jpg";
    }

    public int getMfarm() {
        return mfarm;
    }

    public String getMserver() {
        return mserver;
    }

    public String getMid() {
        return mid;
    }

    public String getMsecret() {
        return msecret;
    }

    public String getMimageUrl() {
        return mimageUrl;
    }
}

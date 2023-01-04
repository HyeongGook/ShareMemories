package com.example.sharememories;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

public class ListItem {
    public String title;
    public String day;
    public Double lat;
    public Double lng;
    public String loc;
    public String memo;

    public ListItem(String title, String day, Double lat, Double lng, String loc, String memo) {
        this.title = title;
        this.day = day;
        this.lat = lat;
        this.lng = lng;
        this.loc = loc;
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getLat() { return lat; }

    public void setLat(Double lat) {this.lat = lat; }

    public Double getLng() { return lng; }

    public void setLng(Double lng) {this.lng = lng; }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}


package com.example.sharememories;

import com.google.android.gms.maps.model.LatLng;

public class MarkerItem {
    public LatLng markerLatLng;
    public String content;

    public MarkerItem(LatLng markerLatLng, String content) {
        this.markerLatLng = markerLatLng;
        this.content = content;
    }

    public LatLng getMarkerLatLng() {
        return markerLatLng;
    }

    public void setMarkerLatLng(LatLng markerLatLng) {
        this.markerLatLng = markerLatLng;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }
}

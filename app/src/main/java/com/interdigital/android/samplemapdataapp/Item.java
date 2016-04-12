package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public abstract class Item {

    private String title;
    private LatLng latLng;
    private Marker marker;

    public Item(String title, double latitude, double longitude) {
        this.title = title;
        latLng = new LatLng(latitude, longitude);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }


    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public abstract void addMarker(GoogleMap googleMap);

    public abstract void setMarkerIcon(@MarkerData.MarkerType int markerType);

    public abstract int getPlainResource();

    public abstract int getUpdatingResource();

    public abstract int getUpdatedResource();

    public abstract int getAlternateResource();

    public abstract void update();

    public abstract View getInfoContents(Context context);

}

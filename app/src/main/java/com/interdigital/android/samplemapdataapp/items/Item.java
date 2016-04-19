package com.interdigital.android.samplemapdataapp.items;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.json.PredefinedLocation;

import java.util.HashMap;

public abstract class Item {

    private String title;
    private Marker marker;
    private LatLng latLng;

    public Item(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public abstract void updateLocation(HashMap<String, PredefinedLocation> predefinedLocationMap);

    public abstract void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap);

    public abstract View getInfoContents(Context context);

}

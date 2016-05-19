package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.json.PredefinedLocation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

public abstract class Item {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_VMS, TYPE_CAR_PARK, TYPE_ANPR})
    public @interface Type {
    }

    public static final int TYPE_VMS = 1;
    public static final int TYPE_CAR_PARK = 2;
    public static final int TYPE_ANPR = 3;

    @Type
    private int type;
    private String title;
    private Marker marker;
    private LatLng latLng;

    public Item(){
    }

    public Item(String title) {
        this.title = title;
    }

    @Type
    public int getType() {
        return type;
    }

    public void setType(@Type int type) {
        this.type = type;
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

    public abstract void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap);

    public abstract View getInfoContents(Context context);

    // Return true if we should add this item to the map.
    // Might want to discard elements with missing data from the feeds.
    public abstract boolean shouldAdd();

}

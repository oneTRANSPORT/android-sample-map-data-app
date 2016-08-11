package com.interdigital.android.samplemapdataapp.marker;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BaseMarker {

    private LatLng latLng;
    private MarkerOptions markerOptions;
    private Marker marker;

    public void addToMap(GoogleMap googleMap) {
        marker = googleMap.addMarker(markerOptions);
    }

    public View getInfoContents() {
        return null;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public MarkerOptions getMarkerOptions() {
        // Only create if needed.
        if (markerOptions == null) {
            markerOptions = new MarkerOptions();
        }
        return markerOptions;
    }

    public void setMarkerOptions(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}

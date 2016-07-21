package com.interdigital.android.samplemapdataapp.polyline;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public abstract class BasePolyline {

    private PolylineOptions polylineOptions = new PolylineOptions();
    private Polyline polyline;

    public void addPoints(ArrayList<LatLng> latLngs) {
        for (int i = 0; i < latLngs.size(); i++) {
            polylineOptions.add(latLngs.get(i));
        }
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }

    public void setPolylineOptions(PolylineOptions polylineOptions) {
        this.polylineOptions = polylineOptions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }
}

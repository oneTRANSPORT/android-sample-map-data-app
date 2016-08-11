package com.interdigital.android.samplemapdataapp.layer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.marker.BaseMarker;

import java.util.ArrayList;

public abstract class MarkerBaseLayer extends BaseLayer {

    private ArrayList<BaseMarker> baseMarkers = new ArrayList<>();

    public MarkerBaseLayer(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void initialise() {
        if (baseMarkers.size() > 0) {
            for (BaseMarker baseMarker : baseMarkers) {
                baseMarker.getMarker().remove();
            }
            baseMarkers.clear();
        }
    }

    public View getInfoWindow(Marker marker) {
        return null;
    }

    public View getInfoContents(Marker marker) {
        for (BaseMarker baseMarker : getBaseMarkers()) {
            if (baseMarker.getMarker().equals(marker)) {
                return baseMarker.getInfoContents();
            }
        }
        return null;
    }

    public Intent onInfoWindowClick(Marker marker, Activity activity) {
        // Return an intent if the marker is owned by this layer.
        // The intent should start an activity to graph the marker data.
        return null;
    }

    @Override
    public void addToMap() {
        for (BaseMarker baseMarker : baseMarkers) {
            baseMarker.addToMap(getGoogleMap());
        }
    }

    public void setVisible(boolean visible) {
        for (BaseMarker baseMarker : baseMarkers) {
            baseMarker.getMarker().setVisible(visible);
        }
    }

    public boolean onMarkerClick(Marker marker) {
        for (BaseMarker baseMarker : baseMarkers) {
            if (baseMarker.getMarker().equals(marker)) {
                marker.showInfoWindow();
                return true;
            }
        }
        return false;
    }

    public ArrayList<BaseMarker> getBaseMarkers() {
        return baseMarkers;
    }

    public void setBaseMarkers(ArrayList<BaseMarker> baseMarkers) {
        this.baseMarkers = baseMarkers;
    }

    public BaseMarker getBaseMarker(Marker marker) {
        for (BaseMarker baseMarker : baseMarkers) {
            if (baseMarker.getMarker().equals(marker)) {
                return baseMarker;
            }
        }
        return null;
    }
}

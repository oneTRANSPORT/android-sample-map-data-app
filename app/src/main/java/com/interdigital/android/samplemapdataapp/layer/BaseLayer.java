package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;

public abstract class BaseLayer {

    private Context context;
    private GoogleMap googleMap;

    public BaseLayer(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
    }

    public void loadPolylines() {
    }

    public void initialiseClusterItems() {
    }

    public void loadClusterItems() throws Exception {
    }


    public View getInfoWindow(Marker marker) {
        return null;
    }

    public View getInfoContents(Marker marker) {
        return null;
    }

    public BaseClusterManager getClusterManager() {
        return null;
    }

    public BaseClusterRenderer getClusterRenderer() {
        return null;
    }

    public abstract void addToMap();

    public abstract void setVisible(boolean visible);

    public abstract void onCameraChange(CameraPosition cameraPosition);

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}

package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

public abstract class BaseLayer {

    private Context context;
    private GoogleMap googleMap;

    public BaseLayer(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
    }

    public abstract void initialise();

    public abstract void load() throws Exception;

    public abstract void addToMap();

    public abstract void setVisible(boolean visible);

    public void onCameraChange(CameraPosition cameraPosition){
    }

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

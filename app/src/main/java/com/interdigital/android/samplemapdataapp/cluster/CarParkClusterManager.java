package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

public class CarParkClusterManager extends BaseClusterManager<CarParkClusterItem>{

    public CarParkClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }
}

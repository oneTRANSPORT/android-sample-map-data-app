package com.interdigital.android.samplemapdataapp.cluster.bucks;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.CarParkClusterItem;

public class CarParkClusterManager extends BaseClusterManager<CarParkClusterItem> {

    public CarParkClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }
}

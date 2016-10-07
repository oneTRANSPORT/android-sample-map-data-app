package com.interdigital.android.samplemapdataapp.cluster.northants;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;

public class TrafficTravelTimeClusterManager extends BaseClusterManager<TrafficTravelTimeClusterItem> {

    public TrafficTravelTimeClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}

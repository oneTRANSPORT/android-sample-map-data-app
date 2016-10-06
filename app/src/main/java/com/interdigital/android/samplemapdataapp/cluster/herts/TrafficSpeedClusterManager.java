package com.interdigital.android.samplemapdataapp.cluster.herts;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;

public class TrafficSpeedClusterManager extends BaseClusterManager<TrafficSpeedClusterItem> {

    public TrafficSpeedClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}

package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

public class TrafficQueueClusterManager extends BaseClusterManager<TrafficQueueClusterItem> {

    public TrafficQueueClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}

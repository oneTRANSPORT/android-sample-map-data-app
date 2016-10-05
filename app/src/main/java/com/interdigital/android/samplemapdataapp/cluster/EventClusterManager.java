package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

public class EventClusterManager extends BaseClusterManager<EventClusterItem> {

    public EventClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}

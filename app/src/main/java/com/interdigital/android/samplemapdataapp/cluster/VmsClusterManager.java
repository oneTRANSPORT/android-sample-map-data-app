package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

public class VmsClusterManager extends BaseClusterManager<VmsClusterItem> {

    public VmsClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}
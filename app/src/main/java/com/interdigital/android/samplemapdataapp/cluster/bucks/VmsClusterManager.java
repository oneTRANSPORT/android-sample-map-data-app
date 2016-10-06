package com.interdigital.android.samplemapdataapp.cluster.bucks;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.VmsClusterItem;

public class VmsClusterManager extends BaseClusterManager<VmsClusterItem> {

    public VmsClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}

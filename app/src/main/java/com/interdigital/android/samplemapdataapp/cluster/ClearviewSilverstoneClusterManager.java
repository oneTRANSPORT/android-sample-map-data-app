package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.ClearviewSilverstoneClusterItem;

public class ClearviewSilverstoneClusterManager
        extends BaseClusterManager<ClearviewSilverstoneClusterItem> {

    public ClearviewSilverstoneClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}

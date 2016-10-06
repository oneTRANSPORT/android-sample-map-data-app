package com.interdigital.android.samplemapdataapp.cluster.bucks;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.RoadWorksClusterItem;

public class RoadWorksClusterManager extends BaseClusterManager<RoadWorksClusterItem> {

    public RoadWorksClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

}

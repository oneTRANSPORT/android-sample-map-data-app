package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.TrafficSpeedClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.TrafficSpeedClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.TrafficSpeedClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.trafficspeed.TrafficSpeed;

public class BucksTrafficSpeeds extends ClusterBaseLayer<TrafficSpeedClusterItem> {

    public BucksTrafficSpeeds(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficSpeed[] trafficSpeeds = BucksContentHelper.getLatestTrafficSpeeds(getContext());
        for (TrafficSpeed trafficspeed : trafficSpeeds) {
            TrafficSpeedClusterItem trafficSpeedClusterItem = new TrafficSpeedClusterItem(trafficspeed);
            if (trafficSpeedClusterItem.shouldAdd()) {
                getClusterItems().add(trafficSpeedClusterItem);
            }
        }
    }

    @Override
    protected BaseClusterManager<TrafficSpeedClusterItem> newClusterManager() {
        return new TrafficSpeedClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficSpeedClusterItem> newClusterRenderer() {
        return new TrafficSpeedClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

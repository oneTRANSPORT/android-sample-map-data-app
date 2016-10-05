package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.TrafficTravelTimeClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.TrafficTravelTimeClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.TrafficTravelTimeClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.traffictraveltime.TrafficTravelTime;

public class BucksTrafficTravelTimes extends ClusterBaseLayer<TrafficTravelTimeClusterItem> {

    public BucksTrafficTravelTimes(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficTravelTime[] trafficTravelTimes = BucksContentHelper.getLatestTrafficTravelTimes(getContext());
        for (TrafficTravelTime trafficTravelTime : trafficTravelTimes) {
            TrafficTravelTimeClusterItem trafficTravelTimeClusterItem = new TrafficTravelTimeClusterItem(trafficTravelTime);
            if (trafficTravelTimeClusterItem.shouldAdd()) {
                getClusterItems().add(trafficTravelTimeClusterItem);
            }
        }
    }

    @Override
    protected BaseClusterManager<TrafficTravelTimeClusterItem> newClusterManager() {
        return new TrafficTravelTimeClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficTravelTimeClusterItem> newClusterRenderer() {
        return new TrafficTravelTimeClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

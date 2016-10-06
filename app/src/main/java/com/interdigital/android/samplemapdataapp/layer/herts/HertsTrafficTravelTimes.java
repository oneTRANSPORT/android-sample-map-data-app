package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficTravelTimeClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficTravelTimeClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficTravelTimeClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;
import net.uk.onetransport.android.county.herts.traffictraveltime.TrafficTravelTime;

public class HertsTrafficTravelTimes extends ClusterBaseLayer<TrafficTravelTimeClusterItem> {

    public HertsTrafficTravelTimes(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficTravelTime[] trafficTravelTimes = HertsContentHelper.getLatestTrafficTravelTimes(getContext());
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

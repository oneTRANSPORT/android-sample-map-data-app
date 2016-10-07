package com.interdigital.android.samplemapdataapp.layer.northants;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.northants.TrafficTravelTimeClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.northants.TrafficTravelTimeClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.northants.TrafficTravelTimeClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.northants.provider.NorthantsContentHelper;
import net.uk.onetransport.android.county.northants.traffictraveltime.TrafficTravelTime;

public class NorthantsTrafficTravelTimes extends ClusterBaseLayer<TrafficTravelTimeClusterItem> {

    public NorthantsTrafficTravelTimes(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficTravelTime[] trafficTravelTimes = NorthantsContentHelper.getLatestTrafficTravelTimes(getContext());
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

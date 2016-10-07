package com.interdigital.android.samplemapdataapp.layer.oxon;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficTravelTimeClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficTravelTimeClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficTravelTimeClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.oxon.provider.OxonContentHelper;
import net.uk.onetransport.android.county.oxon.traffictraveltime.TrafficTravelTime;

public class OxonTrafficTravelTimes extends ClusterBaseLayer<TrafficTravelTimeClusterItem> {

    public OxonTrafficTravelTimes(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficTravelTime[] trafficTravelTimes = OxonContentHelper.getLatestTrafficTravelTimes(getContext());
        for (TrafficTravelTime trafficTravelTime : trafficTravelTimes) {
            TrafficTravelTimeClusterItem trafficTravelTimeClusterItem = new TrafficTravelTimeClusterItem(trafficTravelTime);
            if (isInDate(trafficTravelTime.getTime()) && trafficTravelTimeClusterItem.shouldAdd()) {
                getClusterItems().add(trafficTravelTimeClusterItem);
            }
        }
        Log.i("OxonTrafficTravelTimes", "Found " + trafficTravelTimes.length
                + ", discarded " + (trafficTravelTimes.length - getClusterItems().size()));
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

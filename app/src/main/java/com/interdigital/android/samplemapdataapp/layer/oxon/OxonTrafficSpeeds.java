package com.interdigital.android.samplemapdataapp.layer.oxon;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficSpeedClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficSpeedClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficSpeedClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.oxon.provider.OxonContentHelper;
import net.uk.onetransport.android.county.oxon.trafficspeed.TrafficSpeed;

public class OxonTrafficSpeeds extends ClusterBaseLayer<TrafficSpeedClusterItem> {

    public OxonTrafficSpeeds(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficSpeed[] trafficSpeeds = OxonContentHelper.getLatestTrafficSpeeds(getContext());
        for (TrafficSpeed trafficSpeed : trafficSpeeds) {
            TrafficSpeedClusterItem trafficSpeedClusterItem = new TrafficSpeedClusterItem(trafficSpeed);
            if (isInDate(trafficSpeed.getTime()) && trafficSpeedClusterItem.shouldAdd()) {
                getClusterItems().add(trafficSpeedClusterItem);
            }
        }
        Log.i("OxonTrafficSpeeds", "Found " + trafficSpeeds.length
                + ", discarded " + (trafficSpeeds.length - getClusterItems().size()));
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

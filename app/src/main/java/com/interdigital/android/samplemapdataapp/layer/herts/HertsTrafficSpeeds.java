package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficSpeedClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficSpeedClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficSpeedClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;
import net.uk.onetransport.android.county.herts.trafficspeed.TrafficSpeed;

public class HertsTrafficSpeeds extends ClusterBaseLayer<TrafficSpeedClusterItem> {

    public HertsTrafficSpeeds(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficSpeed[] trafficSpeeds = HertsContentHelper.getLatestTrafficSpeeds(getContext());
        int count = 0;
        for (TrafficSpeed trafficspeed : trafficSpeeds) {
            if (count < MAX_ITEMS) {
                TrafficSpeedClusterItem trafficSpeedClusterItem = new TrafficSpeedClusterItem(trafficspeed);
                if (trafficSpeedClusterItem.shouldAdd()) {
                    getClusterItems().add(trafficSpeedClusterItem);
                    count++;
                }
            }
        }
        Log.i("HertsTrafficSpeeds", "Found " + trafficSpeeds.length
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

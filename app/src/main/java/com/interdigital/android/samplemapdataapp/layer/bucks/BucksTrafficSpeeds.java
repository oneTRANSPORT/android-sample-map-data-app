package com.interdigital.android.samplemapdataapp.layer.bucks;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficSpeedClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficSpeedClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficSpeedClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.trafficspeed.TrafficSpeed;

public class BucksTrafficSpeeds extends ClusterBaseLayer<TrafficSpeedClusterItem> {

    public BucksTrafficSpeeds(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficSpeed[] trafficSpeeds = BucksContentHelper.getLatestTrafficSpeeds(getContext());
        int count = 0;
        for (TrafficSpeed trafficSpeed : trafficSpeeds) {
            if (count < MAX_ITEMS) {
                TrafficSpeedClusterItem trafficSpeedClusterItem = new TrafficSpeedClusterItem(trafficSpeed);
                if (isInDate(trafficSpeed.getTime())
                        && trafficSpeedClusterItem.shouldAdd()) {
                    getClusterItems().add(trafficSpeedClusterItem);
                    count++;
                }
            }
        }
        Log.i("BucksTrafficSpeeds", "Found " + trafficSpeeds.length
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
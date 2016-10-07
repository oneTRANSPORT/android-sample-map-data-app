package com.interdigital.android.samplemapdataapp.layer.bucks;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficQueueClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficQueueClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficQueueClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.trafficqueue.TrafficQueue;

public class BucksTrafficQueues extends ClusterBaseLayer<TrafficQueueClusterItem> {

    public BucksTrafficQueues(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficQueue[] trafficQueues = BucksContentHelper.getLatestTrafficQueues(getContext());
        for (TrafficQueue trafficQueue : trafficQueues) {
            TrafficQueueClusterItem trafficQueueClusterItem = new TrafficQueueClusterItem(trafficQueue);
            if (isInDate(trafficQueue.getTime()) && trafficQueueClusterItem.shouldAdd()) {
                getClusterItems().add(trafficQueueClusterItem);
            }
        }
        Log.i("BucksTrafficQueues", "Found " + trafficQueues.length
                + ", discarded " + (trafficQueues.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<TrafficQueueClusterItem> newClusterManager() {
        return new TrafficQueueClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficQueueClusterItem> newClusterRenderer() {
        return new TrafficQueueClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

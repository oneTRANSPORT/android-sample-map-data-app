package com.interdigital.android.samplemapdataapp.layer.oxon;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficQueueClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficQueueClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficQueueClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.oxon.provider.OxonContentHelper;
import net.uk.onetransport.android.county.oxon.trafficqueue.TrafficQueue;

public class OxonTrafficQueues extends ClusterBaseLayer<TrafficQueueClusterItem> {

    public OxonTrafficQueues(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficQueue[] trafficQueues = OxonContentHelper.getLatestTrafficQueues(getContext());
        for (TrafficQueue trafficQueue : trafficQueues) {
            TrafficQueueClusterItem trafficQueueClusterItem = new TrafficQueueClusterItem(trafficQueue);
            if (isInDate(trafficQueue.getTime()) && trafficQueueClusterItem.shouldAdd()) {
                getClusterItems().add(trafficQueueClusterItem);
            }
        }
        Log.i("OxonTrafficQueues", "Found " + trafficQueues.length
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

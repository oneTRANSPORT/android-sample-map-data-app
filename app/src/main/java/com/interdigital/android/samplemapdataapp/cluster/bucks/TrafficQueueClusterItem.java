package com.interdigital.android.samplemapdataapp.cluster.bucks;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.trafficqueue.TrafficQueue;

public class TrafficQueueClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficQueue trafficQueue;

    public TrafficQueueClusterItem(TrafficQueue trafficQueue) {
        this.trafficQueue = trafficQueue;
        position = new LatLng(trafficQueue.getToLatitude(), trafficQueue.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficQueue.getFromLatitude() == 0 && trafficQueue.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficQueue.getToDescriptor())
                && TextUtils.isEmpty(trafficQueue.getFromDescriptor())) {
            return false;
        }
        return trafficQueue.getPresent().equals("Y");
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficQueue getTrafficQueue() {
        return trafficQueue;
    }
}

package com.interdigital.android.samplemapdataapp.cluster.bucks;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.traffictraveltime.TrafficTravelTime;

public class TrafficTravelTimeClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficTravelTime trafficTravelTime;

    public TrafficTravelTimeClusterItem(TrafficTravelTime trafficTravelTime) {
        this.trafficTravelTime = trafficTravelTime;
        position = new LatLng(trafficTravelTime.getToLatitude(), trafficTravelTime.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficTravelTime.getFromLatitude() == 0 && trafficTravelTime.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficTravelTime.getToDescriptor())
                && TextUtils.isEmpty(trafficTravelTime.getFromDescriptor())) {
            return false;
        }
        return trafficTravelTime.getTravelTime() > 0;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficTravelTime getTrafficTravelTime() {
        return trafficTravelTime;
    }
}

package com.interdigital.android.samplemapdataapp.cluster;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.trafficspeed.TrafficSpeed;

public class TrafficSpeedClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficSpeed trafficSpeed;

    public TrafficSpeedClusterItem(TrafficSpeed trafficSpeed) {
        this.trafficSpeed = trafficSpeed;
        position = new LatLng(trafficSpeed.getToLatitude(), trafficSpeed.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficSpeed.getFromLatitude() == 0 && trafficSpeed.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficSpeed.getToDescriptor())
                && TextUtils.isEmpty(trafficSpeed.getFromDescriptor())) {
            return false;
        }
        return trafficSpeed.getAverageVehicleSpeed() > 0;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficSpeed getTrafficSpeed() {
        return trafficSpeed;
    }
}

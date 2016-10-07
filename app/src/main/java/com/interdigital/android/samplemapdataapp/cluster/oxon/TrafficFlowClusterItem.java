package com.interdigital.android.samplemapdataapp.cluster.oxon;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.oxon.trafficflow.TrafficFlow;

public class TrafficFlowClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficFlow trafficFlow;

    public TrafficFlowClusterItem(TrafficFlow trafficFlow) {
        this.trafficFlow = trafficFlow;
        position = new LatLng(trafficFlow.getToLatitude(), trafficFlow.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficFlow.getFromLatitude() == 0 && trafficFlow.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficFlow.getToDescriptor())
                && TextUtils.isEmpty(trafficFlow.getFromDescriptor())) {
            return false;
        }
        return trafficFlow.getVehicleFlow() > 0;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficFlow getTrafficFlow() {
        return trafficFlow;
    }
}

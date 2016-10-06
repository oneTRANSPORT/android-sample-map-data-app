package com.interdigital.android.samplemapdataapp.cluster.herts;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.herts.trafficscoot.TrafficScoot;

public class TrafficScootClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficScoot trafficScoot;

    public TrafficScootClusterItem(TrafficScoot trafficScoot) {
        this.trafficScoot = trafficScoot;
        position = new LatLng(trafficScoot.getToLatitude(), trafficScoot.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficScoot.getFromLatitude() == 0 && trafficScoot.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficScoot.getToDescriptor())
                && TextUtils.isEmpty(trafficScoot.getFromDescriptor())) {
            return false;
        }
        return trafficScoot.getAverageSpeed() > 0;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficScoot getTrafficScoot() {
        return trafficScoot;
    }
}

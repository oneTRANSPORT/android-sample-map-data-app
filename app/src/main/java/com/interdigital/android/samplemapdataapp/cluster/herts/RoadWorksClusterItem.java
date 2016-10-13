package com.interdigital.android.samplemapdataapp.cluster.herts;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.herts.roadworks.Roadworks;

public class RoadworksClusterItem implements ClusterItem {

    private LatLng position;
    private Roadworks roadworks;

    public RoadworksClusterItem(Roadworks roadworks) {
        this.roadworks = roadworks;
        position = new LatLng(roadworks.getLatitude(), roadworks.getLongitude());
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public Roadworks getRoadworks() {
        return roadworks;
    }
}

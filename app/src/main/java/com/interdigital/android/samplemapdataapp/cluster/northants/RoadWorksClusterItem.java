package com.interdigital.android.samplemapdataapp.cluster.northants;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.northants.roadworks.Roadworks;

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

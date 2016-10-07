package com.interdigital.android.samplemapdataapp.cluster.oxon;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.oxon.roadworks.RoadWorks;

public class RoadWorksClusterItem implements ClusterItem {

    private LatLng position;
    private RoadWorks roadWorks;

    public RoadWorksClusterItem(RoadWorks roadWorks) {
        this.roadWorks = roadWorks;
        position = new LatLng(roadWorks.getLatitude(), roadWorks.getLongitude());
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public RoadWorks getRoadWorks() {
        return roadWorks;
    }
}

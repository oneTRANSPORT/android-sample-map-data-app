package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.roadworks.RoadWorks;

public class BucksRoadWorks extends ClusterBaseLayer<RoadWorksClusterItem> {

    public BucksRoadWorks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        RoadWorks[] roadWorkses = BucksContentHelper.getLatestRoadWorks(getContext());
        for (RoadWorks roadWorks : roadWorkses) {
            RoadWorksClusterItem roadWorksClusterItem = new RoadWorksClusterItem(roadWorks);
            getClusterItems().add(roadWorksClusterItem);
        }
    }

    @Override
    protected BaseClusterManager<RoadWorksClusterItem> newClusterManager() {
        return new RoadWorksClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<RoadWorksClusterItem> newClusterRenderer() {
        return new RoadWorksClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

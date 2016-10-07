package com.interdigital.android.samplemapdataapp.layer.northants;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.northants.RoadWorksClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.northants.RoadWorksClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.northants.RoadWorksClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.northants.provider.NorthantsContentHelper;
import net.uk.onetransport.android.county.northants.roadworks.RoadWorks;

public class NorthantsRoadWorks extends ClusterBaseLayer<RoadWorksClusterItem> {

    public NorthantsRoadWorks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        RoadWorks[] roadWorkses = NorthantsContentHelper.getLatestRoadWorks(getContext());
        double[] coords = new double[roadWorkses.length];
        int c = 0;
        for (int i = roadWorkses.length - 1; i >= 0; i--) {
            // Prevent concurrent locations.
            double coord = roadWorkses[i].getLatitude() * 360 + roadWorkses[i].getLongitude();
            boolean found = false;
            for (int j = 0; j < c; j++) {
                if (coords[j] == coord) {
                    found = true;
                }
            }
            if (!found) {
                RoadWorksClusterItem roadWorksClusterItem = new RoadWorksClusterItem(roadWorkses[i]);
                getClusterItems().add(roadWorksClusterItem);
                coords[c++] = coord;
            }
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

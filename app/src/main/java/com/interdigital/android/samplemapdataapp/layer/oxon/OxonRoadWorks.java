package com.interdigital.android.samplemapdataapp.layer.oxon;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.oxon.RoadworksClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.oxon.RoadworksClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.oxon.RoadworksClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.oxon.provider.OxonContentHelper;
import net.uk.onetransport.android.county.oxon.roadworks.Roadworks;

public class OxonRoadworks extends ClusterBaseLayer<RoadworksClusterItem> {

    public OxonRoadworks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        Roadworks[] roadworkses = OxonContentHelper.getLatestRoadworks(getContext());
        double[] coords = new double[roadworkses.length];
        int c = 0;
        for (int i = roadworkses.length - 1; i >= 0; i--) {
            // Prevent concurrent locations.
            double coord = roadworkses[i].getLatitude() * 360 + roadworkses[i].getLongitude();
            boolean found = false;
            for (int j = 0; j < c; j++) {
                if (coords[j] == coord) {
                    found = true;
                }
            }
            if (c < MAX_ITEMS && !found) {
                if (isInDate(roadworkses[i].getStartOfPeriod())
                        || isInDate(roadworkses[i].getEndOfPeriod())
                        || isInDate(roadworkses[i].getOverallStartTime())
                        || isInDate(roadworkses[i].getOverallEndTime())) {
                    RoadworksClusterItem roadworksClusterItem = new RoadworksClusterItem(roadworkses[i]);
                    getClusterItems().add(roadworksClusterItem);
                    coords[c++] = coord;
                }
            }
        }
        Log.i("OxonRoadworks", "Found " + roadworkses.length
                + ", discarded " + (roadworkses.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<RoadworksClusterItem> newClusterManager() {
        return new RoadworksClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<RoadworksClusterItem> newClusterRenderer() {
        return new RoadworksClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

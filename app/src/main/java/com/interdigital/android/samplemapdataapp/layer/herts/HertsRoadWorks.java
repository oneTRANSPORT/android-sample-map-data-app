package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.RoadWorksClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.RoadWorksClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.RoadWorksClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;
import net.uk.onetransport.android.county.herts.roadworks.RoadWorks;

public class HertsRoadWorks extends ClusterBaseLayer<RoadWorksClusterItem> {

    public HertsRoadWorks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        RoadWorks[] roadWorkses = HertsContentHelper.getLatestRoadWorks(getContext());
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
            if (c < MAX_ITEMS && !found) {
                if (isInDate(roadWorkses[i].getStartOfPeriod())
                        || isInDate(roadWorkses[i].getEndOfPeriod())
                        || isInDate(roadWorkses[i].getOverallStartTime())
                        || isInDate(roadWorkses[i].getOverallEndTime())) {
                    RoadWorksClusterItem roadWorksClusterItem = new RoadWorksClusterItem(roadWorkses[i]);
                    getClusterItems().add(roadWorksClusterItem);
                    coords[c++] = coord;
                }
            }
        }
        Log.i("HertsRoadWorks", "Found " + roadWorkses.length
                + ", discarded " + (roadWorkses.length - getClusterItems().size()));
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

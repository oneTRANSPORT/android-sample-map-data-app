package com.interdigital.android.samplemapdataapp.layer.oxon;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficScootClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficScootClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficScootClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.oxon.provider.OxonContentHelper;
import net.uk.onetransport.android.county.oxon.trafficscoot.TrafficScoot;

public class OxonTrafficScoots extends ClusterBaseLayer<TrafficScootClusterItem> {

    public OxonTrafficScoots(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficScoot[] trafficScoots = OxonContentHelper.getLatestTrafficScoots(getContext());
        int count = 0;
        for (TrafficScoot trafficScoot : trafficScoots) {
            if (count < MAX_ITEMS) {
                TrafficScootClusterItem trafficScootClusterItem = new TrafficScootClusterItem(trafficScoot);
                if (isInDate(trafficScoot.getTime()) && trafficScootClusterItem.shouldAdd()) {
                    getClusterItems().add(trafficScootClusterItem);
                    count++;
                }
            }
        }
        Log.i("OxonTrafficScoots", "Found " + trafficScoots.length
                + ", discarded " + (trafficScoots.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<TrafficScootClusterItem> newClusterManager() {
        return new TrafficScootClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficScootClusterItem> newClusterRenderer() {
        return new TrafficScootClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

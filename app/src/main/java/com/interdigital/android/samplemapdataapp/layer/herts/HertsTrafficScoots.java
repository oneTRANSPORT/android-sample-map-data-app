package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficScootClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficScootClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficScootClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;
import net.uk.onetransport.android.county.herts.trafficscoot.TrafficScoot;

public class HertsTrafficScoots extends ClusterBaseLayer<TrafficScootClusterItem> {

    public HertsTrafficScoots(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficScoot[] trafficScoots = HertsContentHelper.getLatestTrafficScoots(getContext());
        for (TrafficScoot trafficscoot : trafficScoots) {
            TrafficScootClusterItem trafficScootClusterItem = new TrafficScootClusterItem(trafficscoot);
            if (trafficScootClusterItem.shouldAdd()) {
                getClusterItems().add(trafficScootClusterItem);
            }
        }
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

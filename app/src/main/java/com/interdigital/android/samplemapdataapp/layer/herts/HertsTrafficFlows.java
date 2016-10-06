package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficFlowClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficFlowClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficFlowClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;
import net.uk.onetransport.android.county.herts.trafficflow.TrafficFlow;

public class HertsTrafficFlows extends ClusterBaseLayer<TrafficFlowClusterItem> {

    public HertsTrafficFlows(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficFlow[] trafficFlows = HertsContentHelper.getLatestTrafficFlows(getContext());
        for (TrafficFlow trafficFlow : trafficFlows) {
            TrafficFlowClusterItem trafficFlowClusterItem = new TrafficFlowClusterItem(trafficFlow);
            if (trafficFlowClusterItem.shouldAdd()) {
                getClusterItems().add(trafficFlowClusterItem);
            }
        }
    }

    @Override
    protected BaseClusterManager<TrafficFlowClusterItem> newClusterManager() {
        return new TrafficFlowClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficFlowClusterItem> newClusterRenderer() {
        return new TrafficFlowClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

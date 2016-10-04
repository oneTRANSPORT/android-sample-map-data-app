package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.TrafficFlowClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.TrafficFlowClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.TrafficFlowClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.trafficflow.TrafficFlow;

public class BucksTrafficFlows extends ClusterBaseLayer<TrafficFlowClusterItem> {

    public BucksTrafficFlows(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        Context context = getContext();
        TrafficFlow[] trafficFlows = BucksContentHelper.getLatestTrafficFlows(context);
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

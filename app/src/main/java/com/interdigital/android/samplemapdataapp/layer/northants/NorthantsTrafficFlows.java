package com.interdigital.android.samplemapdataapp.layer.northants;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.northants.TrafficFlowClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.northants.TrafficFlowClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.northants.TrafficFlowClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.northants.provider.NorthantsContentHelper;
import net.uk.onetransport.android.county.northants.trafficflow.TrafficFlow;

public class NorthantsTrafficFlows extends ClusterBaseLayer<TrafficFlowClusterItem> {

    public NorthantsTrafficFlows(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficFlow[] trafficFlows = NorthantsContentHelper.getLatestTrafficFlows(getContext());
        int count = 0;
        for (TrafficFlow trafficFlow : trafficFlows) {
            if (count < MAX_ITEMS) {
                TrafficFlowClusterItem trafficFlowClusterItem = new TrafficFlowClusterItem(trafficFlow);
                if (trafficFlowClusterItem.shouldAdd()) {
                    getClusterItems().add(trafficFlowClusterItem);
                    count++;
                }
            }
        }
        Log.i("NorthantsTrafficFlows", "Found " + trafficFlows.length
                + ", discarded " + (trafficFlows.length - getClusterItems().size()));
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

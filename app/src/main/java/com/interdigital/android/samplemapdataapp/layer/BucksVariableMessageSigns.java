package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.variablemessagesigns.VariableMessageSign;

public class BucksVariableMessageSigns extends ClusterBaseLayer<VmsClusterItem> {

    public BucksVariableMessageSigns(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        VariableMessageSign[] variableMessageSigns = BucksContentHelper
                .getLatestVariableMessageSigns(getContext());
        for (VariableMessageSign variableMessageSign : variableMessageSigns) {
            VmsClusterItem vmsClusterItem = new VmsClusterItem(variableMessageSign);
            if (vmsClusterItem.shouldAdd()) {
                getClusterItems().add(vmsClusterItem);
            }
        }
    }

    @Override
    protected BaseClusterManager<VmsClusterItem> newClusterManager() {
        return new VmsClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<VmsClusterItem> newClusterRenderer() {
        return new VmsClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

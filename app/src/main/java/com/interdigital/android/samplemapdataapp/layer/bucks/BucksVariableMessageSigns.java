package com.interdigital.android.samplemapdataapp.layer.bucks;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.bucks.VmsClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.bucks.VmsClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.VmsClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

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
        double[] coords = new double[variableMessageSigns.length];
        int c = 0;
        for (int i = variableMessageSigns.length - 1; i >= 0; i--) {
            // Prevent concurrent locations.
            double coord = variableMessageSigns[i].getLatitude() * 360
                    + variableMessageSigns[i].getLongitude();
            boolean found = false;
            for (int j = 0; j < c; j++) {
                if (coords[j] == coord) {
                    found = true;
                }
            }
            if (!found) {
                VmsClusterItem vmsClusterItem = new VmsClusterItem(variableMessageSigns[i]);
                if (vmsClusterItem.shouldAdd()) {
                    getClusterItems().add(vmsClusterItem);
                    coords[c++] = coord;
                }
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

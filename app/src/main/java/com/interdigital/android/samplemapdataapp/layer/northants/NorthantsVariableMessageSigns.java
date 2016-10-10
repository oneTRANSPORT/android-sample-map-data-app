package com.interdigital.android.samplemapdataapp.layer.northants;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.northants.VmsClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.northants.VmsClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.northants.VmsClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.northants.provider.NorthantsContentHelper;
import net.uk.onetransport.android.county.northants.variablemessagesigns.VariableMessageSign;

public class NorthantsVariableMessageSigns extends ClusterBaseLayer<VmsClusterItem> {

    public NorthantsVariableMessageSigns(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        VariableMessageSign[] variableMessageSigns = NorthantsContentHelper
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
            if (c < MAX_ITEMS && !found) {
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

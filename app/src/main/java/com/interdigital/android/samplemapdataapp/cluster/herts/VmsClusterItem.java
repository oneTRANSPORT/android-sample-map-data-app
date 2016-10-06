package com.interdigital.android.samplemapdataapp.cluster.herts;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.herts.variablemessagesigns.VariableMessageSign;

public class VmsClusterItem implements ClusterItem {

    private LatLng position;
    private VariableMessageSign variableMessageSign;

    public VmsClusterItem(VariableMessageSign variableMessageSign) {
        this.variableMessageSign = variableMessageSign;
        position = new LatLng(variableMessageSign.getLatitude(), variableMessageSign.getLongitude());
    }

    public boolean shouldAdd() {
        if (position == null || (position.latitude == 0 && position.longitude == 0)) {
            return false;
        }
        if (variableMessageSign.getLegend() == null
                || variableMessageSign.getLegend().length == 0) {
            return false;
        }
        for (String line : variableMessageSign.getLegend()) {
            if (line.trim().length() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public VariableMessageSign getVariableMessageSign() {
        return variableMessageSign;
    }
}

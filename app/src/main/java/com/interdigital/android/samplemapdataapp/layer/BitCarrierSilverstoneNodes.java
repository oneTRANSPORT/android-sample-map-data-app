package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.marker.BitCarrierSilverstoneNodeMarker;

import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.Node;
import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContentHelper;

public class BitCarrierSilverstoneNodes extends MarkerBaseLayer {

    public BitCarrierSilverstoneNodes(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() {
        Node[] nodes = BcsContentHelper.getNodes(getContext());
        for (Node node : nodes) {
            BitCarrierSilverstoneNodeMarker bsnm = new BitCarrierSilverstoneNodeMarker(
                    getContext(), node);
            getBaseMarkers().add(bsnm);
        }
    }
}

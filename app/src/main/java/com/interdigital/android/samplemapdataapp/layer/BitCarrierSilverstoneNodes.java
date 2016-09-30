package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.marker.BitCarrierSilverstoneNodeMarker;

import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContentHelper;

public class BitCarrierSilverstoneNodes extends MarkerBaseLayer {

    public BitCarrierSilverstoneNodes(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() {
        Cursor cursor = BcsContentHelper.getNodeCursor(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                BitCarrierSilverstoneNodeMarker bsnm = new BitCarrierSilverstoneNodeMarker(
                        getContext(), cursor);
                getBaseMarkers().add(bsnm);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}

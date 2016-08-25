package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.polyline.BitCarrierSketchPolyline;

import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContentHelper;

public class BitCarrierSilverstone extends PolylineBaseLayer {

    public BitCarrierSilverstone(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() {
        Cursor sketchCursor = BcsContentHelper.getSketches(getContext());
        Cursor nodeCursor = BcsContentHelper.getNodes(getContext());
        Cursor travelTimeCursor = BcsContentHelper.getLatestTravelTimes(getContext());
        Cursor vectorCursor = BcsContentHelper.getVectors(getContext());
        if (sketchCursor.moveToFirst()) {
            while (!sketchCursor.isAfterLast()) {
                BitCarrierSketchPolyline bcsp = new BitCarrierSketchPolyline(sketchCursor, nodeCursor,
                        travelTimeCursor, vectorCursor);
                getBasePolylines().add(bcsp);
                sketchCursor.moveToNext();
            }
        }
        sketchCursor.close();
        nodeCursor.close();
        travelTimeCursor.close();
        vectorCursor.close();
    }
}

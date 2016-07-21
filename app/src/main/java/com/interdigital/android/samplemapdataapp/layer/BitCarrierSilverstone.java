package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.interdigital.android.samplemapdataapp.polyline.BitCarrierSketchPolyline;

import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContentHelper;

import java.util.ArrayList;

public class BitCarrierSilverstone extends BaseLayer {

    private ArrayList<BitCarrierSketchPolyline> polylines = new ArrayList<>();

    public BitCarrierSilverstone(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void loadPolylines() {
        Cursor cursor = BcsContentHelper.getSketches(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                BitCarrierSketchPolyline bcsp = new BitCarrierSketchPolyline(cursor);
                polylines.add(bcsp);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    @Override
    public void addToMap() { // UI thread.
        for (BitCarrierSketchPolyline bcsp : polylines) {
            bcsp.setPolyline(getGoogleMap().addPolyline(bcsp.getPolylineOptions()));
        }
    }

    @Override
    public void setVisible(boolean visible) {
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }
}

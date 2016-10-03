package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.polyline.BitCarrierSketchPolyline;

import net.uk.onetransport.android.modules.bitcarriersilverstone.config.sketch.Sketch;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.vector.Vector;
import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContentHelper;

public class BitCarrierSilverstone extends PolylineBaseLayer {

    public BitCarrierSilverstone(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() {
        Context context = getContext();
        Sketch[] sketches = BcsContentHelper.getSketches(context);
        Vector[] vectors = BcsContentHelper.getLatestDataVectors(context);
        for (Sketch sketch : sketches) {
            BitCarrierSketchPolyline bcsp = new BitCarrierSketchPolyline(context, sketch, vectors);
            getBasePolylines().add(bcsp);
        }
    }
}

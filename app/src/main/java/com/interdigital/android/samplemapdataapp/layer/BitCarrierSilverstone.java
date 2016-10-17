/* Copyright 2016 InterDigital Communications, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

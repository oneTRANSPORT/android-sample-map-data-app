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

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
package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficScootClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficScootClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.TrafficScootClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;
import net.uk.onetransport.android.county.herts.trafficscoot.TrafficScoot;

public class HertsTrafficScoots extends ClusterBaseLayer<TrafficScootClusterItem> {

    public HertsTrafficScoots(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficScoot[] trafficScoots = HertsContentHelper.getLatestTrafficScoots(getContext());
        int count = 0;
        for (TrafficScoot trafficscoot : trafficScoots) {
            if (count < MAX_ITEMS) {
                TrafficScootClusterItem trafficScootClusterItem = new TrafficScootClusterItem(trafficscoot);
                if (trafficScootClusterItem.shouldAdd()) {
                    getClusterItems().add(trafficScootClusterItem);
                    count++;
                }
            }
        }
        Log.i("HertsTrafficScoots", "Found " + trafficScoots.length
                + ", discarded " + (trafficScoots.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<TrafficScootClusterItem> newClusterManager() {
        return new TrafficScootClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficScootClusterItem> newClusterRenderer() {
        return new TrafficScootClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

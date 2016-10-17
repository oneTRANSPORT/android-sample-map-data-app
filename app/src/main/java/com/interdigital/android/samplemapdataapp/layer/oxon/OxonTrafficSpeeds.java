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
package com.interdigital.android.samplemapdataapp.layer.oxon;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficSpeedClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficSpeedClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.oxon.TrafficSpeedClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.oxon.provider.OxonContentHelper;
import net.uk.onetransport.android.county.oxon.trafficspeed.TrafficSpeed;

public class OxonTrafficSpeeds extends ClusterBaseLayer<TrafficSpeedClusterItem> {

    public OxonTrafficSpeeds(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficSpeed[] trafficSpeeds = OxonContentHelper.getLatestTrafficSpeeds(getContext());
        int count = 0;
        for (TrafficSpeed trafficSpeed : trafficSpeeds) {
            if (count < MAX_ITEMS) {
                TrafficSpeedClusterItem trafficSpeedClusterItem = new TrafficSpeedClusterItem(trafficSpeed);
                if (isInDate(trafficSpeed.getTime()) && trafficSpeedClusterItem.shouldAdd()) {
                    getClusterItems().add(trafficSpeedClusterItem);
                    count++;
                }
            }
        }
        Log.i("OxonTrafficSpeeds", "Found " + trafficSpeeds.length
                + ", discarded " + (trafficSpeeds.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<TrafficSpeedClusterItem> newClusterManager() {
        return new TrafficSpeedClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficSpeedClusterItem> newClusterRenderer() {
        return new TrafficSpeedClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

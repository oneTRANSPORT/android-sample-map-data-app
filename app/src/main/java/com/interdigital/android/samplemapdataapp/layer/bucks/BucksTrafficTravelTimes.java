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
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficTravelTimeClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficTravelTimeClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficTravelTimeClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
import net.uk.onetransport.android.county.bucks.traffictraveltime.TrafficTravelTime;

public class BucksTrafficTravelTimes extends ClusterBaseLayer<TrafficTravelTimeClusterItem> {

    public BucksTrafficTravelTimes(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficTravelTime[] trafficTravelTimes = BucksContentHelper.getLatestTrafficTravelTimes(getContext());
        int count = 0;
        for (TrafficTravelTime trafficTravelTime : trafficTravelTimes) {
            if (count < MAX_ITEMS) {
                TrafficTravelTimeClusterItem trafficTravelTimeClusterItem = new TrafficTravelTimeClusterItem(trafficTravelTime);
                if (isInDate(trafficTravelTime.getTime())
                        && trafficTravelTimeClusterItem.shouldAdd()) {
                    getClusterItems().add(trafficTravelTimeClusterItem);
                    count++;
                }
            }
        }
        Log.i("BucksTrafficTravelTimes", "Found " + trafficTravelTimes.length
                + ", discarded " + (trafficTravelTimes.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<TrafficTravelTimeClusterItem> newClusterManager() {
        return new TrafficTravelTimeClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<TrafficTravelTimeClusterItem> newClusterRenderer() {
        return new TrafficTravelTimeClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

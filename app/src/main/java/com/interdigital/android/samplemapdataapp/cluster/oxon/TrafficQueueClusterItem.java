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
package com.interdigital.android.samplemapdataapp.cluster.oxon;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.oxon.trafficqueue.TrafficQueue;

public class TrafficQueueClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficQueue trafficQueue;

    public TrafficQueueClusterItem(TrafficQueue trafficQueue) {
        this.trafficQueue = trafficQueue;
        position = new LatLng(trafficQueue.getToLatitude(), trafficQueue.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficQueue.getFromLatitude() == 0 && trafficQueue.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficQueue.getToDescriptor())
                && TextUtils.isEmpty(trafficQueue.getFromDescriptor())) {
            return false;
        }
        return trafficQueue.getPresent().equals("Y");
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficQueue getTrafficQueue() {
        return trafficQueue;
    }
}

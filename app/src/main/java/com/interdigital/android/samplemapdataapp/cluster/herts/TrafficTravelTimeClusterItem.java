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
package com.interdigital.android.samplemapdataapp.cluster.herts;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.herts.traffictraveltime.TrafficTravelTime;

public class TrafficTravelTimeClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficTravelTime trafficTravelTime;

    public TrafficTravelTimeClusterItem(TrafficTravelTime trafficTravelTime) {
        this.trafficTravelTime = trafficTravelTime;
        position = new LatLng(trafficTravelTime.getToLatitude(), trafficTravelTime.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficTravelTime.getFromLatitude() == 0 && trafficTravelTime.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficTravelTime.getToDescriptor())
                && TextUtils.isEmpty(trafficTravelTime.getFromDescriptor())) {
            return false;
        }
        return trafficTravelTime.getTravelTime() > 0;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficTravelTime getTrafficTravelTime() {
        return trafficTravelTime;
    }
}

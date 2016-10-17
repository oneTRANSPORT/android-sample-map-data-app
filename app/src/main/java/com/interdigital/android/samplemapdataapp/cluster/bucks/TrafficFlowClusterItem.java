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
package com.interdigital.android.samplemapdataapp.cluster.bucks;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.trafficflow.TrafficFlow;

public class TrafficFlowClusterItem implements ClusterItem {

    private LatLng position;
    private TrafficFlow trafficFlow;

    public TrafficFlowClusterItem(TrafficFlow trafficFlow) {
        this.trafficFlow = trafficFlow;
        position = new LatLng(trafficFlow.getToLatitude(), trafficFlow.getToLongitude());
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (trafficFlow.getFromLatitude() == 0 && trafficFlow.getFromLongitude() == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(trafficFlow.getToDescriptor())
                && TextUtils.isEmpty(trafficFlow.getFromDescriptor())) {
            return false;
        }
        return trafficFlow.getVehicleFlow() > 0;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public TrafficFlow getTrafficFlow() {
        return trafficFlow;
    }
}

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
package com.interdigital.android.samplemapdataapp.layer.northants;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.northants.EventClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.northants.EventClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.northants.EventClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.northants.events.Event;
import net.uk.onetransport.android.county.northants.provider.NorthantsContentHelper;

public class NorthantsEvents extends ClusterBaseLayer<EventClusterItem> {

    public NorthantsEvents(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        Event[] events = NorthantsContentHelper.getLatestEvents(getContext());
        double[] coords = new double[events.length];
        int c = 0;
        for (int i = events.length - 1; i >= 0; i--) {
            // Prevent concurrent locations.
            double coord = events[i].getLatitude() * 360 + events[i].getLongitude();
            boolean found = false;
            for (int j = 0; j < c; j++) {
                if (coords[j] == coord) {
                    found = true;
                }
            }
            if (c < MAX_ITEMS && !found) {
                if (isInDate(events[i].getStartOfPeriod())
                        || isInDate(events[i].getEndOfPeriod())
                        || isInDate(events[i].getOverallStartTime())
                        || isInDate(events[i].getOverallEndTime())) {
                    EventClusterItem eventClusterItem = new EventClusterItem(events[i]);
                    if (eventClusterItem.shouldAdd()) {
                        getClusterItems().add(eventClusterItem);
                        coords[c++] = coord;
                    }
                }
            }
        }
        Log.i("NorthantsEvents", "Found " + events.length
                + ", discarded " + (events.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<EventClusterItem> newClusterManager() {
        return new EventClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<EventClusterItem> newClusterRenderer() {
        return new EventClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

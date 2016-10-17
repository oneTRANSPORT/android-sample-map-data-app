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
package com.interdigital.android.samplemapdataapp.cluster.northants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;

public class TrafficTravelTimeClusterRenderer extends BaseClusterRenderer<TrafficTravelTimeClusterItem> {

    public TrafficTravelTimeClusterRenderer(Context context, GoogleMap googleMap,
                                            ClusterManager<TrafficTravelTimeClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(TrafficTravelTimeClusterItem trafficTravelTimeClusterItem) {
        return R.drawable.travel_time_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.travel_time_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TrafficTravelTimeClusterItem trafficTravelTimeClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_traffic_travel_time, null, false);
        int travelTime = (int) (double) trafficTravelTimeClusterItem
                .getTrafficTravelTime().getTravelTime();
        String locationReference = trafficTravelTimeClusterItem.getTrafficTravelTime().getToDescriptor();

        ((TextView) view.findViewById(R.id.travel_time_text_view))
                .setText(String.format(context.getString(R.string.travel_time), travelTime / 60, travelTime % 60));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;

public class EventClusterRenderer extends BaseClusterRenderer<EventClusterItem> {

    public EventClusterRenderer(Context context, GoogleMap googleMap,
                                ClusterManager<EventClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(EventClusterItem eventClusterItem) {
        return R.drawable.event_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.event_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        EventClusterItem eventClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_event, null, false);
        String reason = eventClusterItem.getEvent().getDescription().replaceFirst(".*Reason : ", "")
                .replaceFirst(" :.*", "");
        String locationReference = eventClusterItem.getEvent().getDescription().replaceFirst(".*Location : ", "")
                .replaceFirst(" :.*", "");

        ((TextView) view.findViewById(R.id.reason_text_view)).setText(reason);
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }

    @Override
    public float getInfoWindowAnchorY() {
        return 0.2f;
    }
}

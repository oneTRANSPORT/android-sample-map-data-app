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
package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

public class ClearviewSilverstoneClusterRenderer
        extends BaseClusterRenderer<ClearviewSilverstoneClusterItem> {

    public ClearviewSilverstoneClusterRenderer(Context context, GoogleMap googleMap,
                                               ClusterManager<ClearviewSilverstoneClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(ClearviewSilverstoneClusterItem clearviewSilverstoneClusterItem) {
        return R.drawable.carpark_silverstone_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.carpark_silverstone_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        ClearviewSilverstoneClusterItem clearviewSilverstoneClusterItem = getClusterItem(marker);
        String description = clearviewSilverstoneClusterItem.getDevice().getDescription();
        String changed = "Device installation: " + clearviewSilverstoneClusterItem.getDevice()
                .getChanged().trim().replaceFirst(" .*$", "").replaceFirst("^[0-9]+-", "");
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_silverstone_car_park, null, false);
        ((TextView) view.findViewById(R.id.sign_text_view)).setText(description);
        ((TextView) view.findViewById(R.id.changed_text_view)).setText(changed);
        if (clearviewSilverstoneClusterItem.getTrafficItem().getTime() != null) {
            if (clearviewSilverstoneClusterItem.getTrafficItem().getDirection()) {
                ((TextView) view.findViewById(R.id.direction_text_view)).setText("Outgoing");
            } else {
                ((TextView) view.findViewById(R.id.direction_text_view)).setText("Incoming");
            }
            String flowTime = "Traffic flow at " + clearviewSilverstoneClusterItem.getTrafficItem()
                    .getTime().trim().replaceFirst("^[0-9]+-", "").replaceFirst(":[^:]*$", "");
            ((TextView) view.findViewById(R.id.flow_time_text_view)).setText(flowTime);
        } else {
            view.findViewById(R.id.sign_text_view).setVisibility(View.GONE);
            view.findViewById(R.id.direction_text_view).setVisibility(View.GONE);
        }
        return view;
    }

}

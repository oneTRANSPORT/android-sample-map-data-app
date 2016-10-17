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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

public class BaseClusterManager<T extends ClusterItem> extends ClusterManager<T>
        implements ClusterManager.OnClusterClickListener<T> {

    private GoogleMap googleMap;

    public BaseClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
        this.googleMap = googleMap;
        setOnClusterClickListener(this);
    }

    @Override
    public void setRenderer(ClusterRenderer<T> clusterRenderer) {
        super.setRenderer(clusterRenderer);
        getMarkerCollection().setOnInfoWindowAdapter(
                (GoogleMap.InfoWindowAdapter) clusterRenderer);
    }

    @Override
    public boolean onClusterClick(Cluster<T> cluster) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (T item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        final LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        return true;
    }
}

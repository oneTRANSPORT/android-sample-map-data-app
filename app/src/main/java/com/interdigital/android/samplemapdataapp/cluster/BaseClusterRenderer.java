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
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public abstract class BaseClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T>
        implements GoogleMap.InfoWindowAdapter {

    protected Context context;

    private boolean visible = true;
    private ClusterManager<T> clusterManager;

    public BaseClusterRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        this.clusterManager = clusterManager;
    }

    @Override
    protected void onBeforeClusterItemRendered(T clusterItem, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(getIconResource(clusterItem)))
                .visible(visible).anchor(0.5f, getAnchorY()).infoWindowAnchor(0.5f, getInfoWindowAnchorY());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<T> cluster,
                                           MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(getIconClusterResource()))
                .visible(visible);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        for (Marker marker : clusterManager.getMarkerCollection().getMarkers()) {
            marker.setVisible(visible);
        }
        for (Marker marker : clusterManager.getClusterMarkerCollection().getMarkers()) {
            marker.setVisible(visible);
        }
    }

    public abstract int getIconResource(T clusterItem);

    public abstract int getIconClusterResource();

    public float getAnchorY() {
        return 0.5f;
    }

    public float getInfoWindowAnchorY() {
        return 0f;
    }
}

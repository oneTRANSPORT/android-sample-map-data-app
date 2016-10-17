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
package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;

import java.util.ArrayList;

public abstract class ClusterBaseLayer<T extends ClusterItem> extends BaseLayer {

    private BaseClusterManager<T> clusterManager;
    private BaseClusterRenderer<T> clusterRenderer;
    private ArrayList<T> clusterItems = new ArrayList<>();

    public ClusterBaseLayer(Context context, GoogleMap googleMap) {
        super(context, googleMap);
        this.clusterManager = newClusterManager();
        this.clusterRenderer = newClusterRenderer();
    }

    @Override
    public void initialise() {
        if (clusterManager != null) {
            for (Marker marker : clusterManager.getMarkerCollection().getMarkers()) {
                marker.remove();
            }
            for (Marker marker : clusterManager.getClusterMarkerCollection().getMarkers()) {
                marker.remove();
            }
            clusterManager.clearItems();
        }
        clusterManager = newClusterManager();
        clusterRenderer = newClusterRenderer();
    }

    public View getInfoWindow(Marker marker) {
        if (clusterRenderer.getClusterItem(marker) != null) {
            return clusterManager.getMarkerManager().getInfoWindow(marker);
        }
        return null;
    }

    public View getInfoContents(Marker marker) {
        if (clusterRenderer.getClusterItem(marker) != null) {
            return clusterManager.getMarkerManager().getInfoContents(marker);
        }
        return null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (clusterManager != null) {
            clusterManager.onCameraChange(cameraPosition);
        }
    }

    @Override
    public void addToMap() {
        if (clusterItems.size() > 0) {
            clusterManager.addItems(clusterItems);
            clusterManager.setRenderer(clusterRenderer);
        }
    }

    public boolean onMarkerClick(Marker marker) {
        if (clusterManager != null && (clusterRenderer.getClusterItem(marker) != null
                || clusterRenderer.getCluster(marker) != null)) {
            return clusterManager.onMarkerClick(marker);
        }
        return false;
    }

    @Override
    public void setVisible(boolean visible) {
        clusterRenderer.setVisible(visible);
    }

    public BaseClusterManager<T> getClusterManager() {
        return clusterManager;
    }

    public BaseClusterRenderer<T> getClusterRenderer() {
        return clusterRenderer;
    }

    public ArrayList<T> getClusterItems() {
        return clusterItems;
    }

    protected abstract BaseClusterManager<T> newClusterManager();

    protected abstract BaseClusterRenderer<T> newClusterRenderer();
}

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

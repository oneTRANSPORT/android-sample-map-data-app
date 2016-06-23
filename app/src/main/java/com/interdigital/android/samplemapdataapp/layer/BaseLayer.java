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

public abstract class BaseLayer<T extends ClusterItem> {

    private Context context;
    private GoogleMap googleMap;
    private BaseClusterManager<T> clusterManager;
    private BaseClusterRenderer<T> clusterRenderer;
    private ArrayList<T> clusterItems;

    public BaseLayer(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
        this.clusterManager = newClusterManager();
        this.clusterRenderer = newClusterRenderer();
    }

    public void initialiseClusterItems() {
        clusterItems = new ArrayList<>();
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

    public void onCameraChange(CameraPosition cameraPosition) {
        if (clusterManager != null) {
            clusterManager.onCameraChange(cameraPosition);
        }
    }

    public void showNewClusterItems() {
        clusterManager.addItems(clusterItems);
        clusterManager.setRenderer(clusterRenderer);
    }

    public static void initialiseClusterItems(BaseLayer[] layers) {
        for (BaseLayer layer : layers) {
            layer.initialiseClusterItems();
        }
    }

    public abstract void loadClusterItems() throws Exception;

    public void setVisible(boolean visible) {
        clusterRenderer.setVisible(visible);
    }

    public Context getContext() {
        return context;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public BaseClusterManager<T> getClusterManager() {
        return clusterManager;
    }

    public BaseClusterRenderer<T> getClusterRenderer() {
        return clusterRenderer;
    }

    protected abstract BaseClusterManager<T> newClusterManager();

    protected abstract BaseClusterRenderer<T> newClusterRenderer();

    public ArrayList<T> getClusterItems() {
        return clusterItems;
    }
}

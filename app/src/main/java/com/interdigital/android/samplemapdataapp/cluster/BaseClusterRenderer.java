package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                getIconResource(clusterItem));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(visible)
                .anchor(0.5f, getAnchorY());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<T> cluster,
                                           MarkerOptions markerOptions) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                getIconClusterResource());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(visible);
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
}

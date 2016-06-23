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

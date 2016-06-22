package com.interdigital.android.samplemapdataapp;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

public class VmsClusterManager extends ClusterManager<VmsClusterItem>
        implements ClusterManager.OnClusterClickListener<VmsClusterItem> {

    private GoogleMap googleMap;

    public VmsClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
        this.googleMap = googleMap;
        setOnClusterClickListener(this);
    }

    @Override
    public void setRenderer(ClusterRenderer<VmsClusterItem> vmsClusterRenderer) {
        super.setRenderer(vmsClusterRenderer);
        getMarkerCollection().setOnInfoWindowAdapter(
                (GoogleMap.InfoWindowAdapter) vmsClusterRenderer);
    }

    @Override
    public boolean onClusterClick(Cluster<VmsClusterItem> cluster) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        final LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        return true;
    }

}

package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

public class RoadWorksClusterManager extends ClusterManager<RoadWorksClusterItem>
        implements ClusterManager.OnClusterClickListener<RoadWorksClusterItem> {

    private GoogleMap googleMap;

    public RoadWorksClusterManager(Context context, GoogleMap googleMap) {
        super(context, googleMap);
        this.googleMap = googleMap;
        setOnClusterClickListener(this);
    }

    @Override
    public void setRenderer(ClusterRenderer<RoadWorksClusterItem> roadWorksClusterRenderer) {
        super.setRenderer(roadWorksClusterRenderer);
        getMarkerCollection().setOnInfoWindowAdapter(
                (GoogleMap.InfoWindowAdapter) roadWorksClusterRenderer);
    }

    @Override
    public boolean onClusterClick(Cluster<RoadWorksClusterItem> cluster) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        final LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        return true;
    }

}

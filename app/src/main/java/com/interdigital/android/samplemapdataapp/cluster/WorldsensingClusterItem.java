package com.interdigital.android.samplemapdataapp.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class WorldsensingClusterItem implements ClusterItem {

    private LatLng position;
    private boolean full = false;
    private boolean updating = false;
    private String sensorId;

    public WorldsensingClusterItem(LatLng position, String sensorId) {
        this.position = position;
        this.sensorId = sensorId;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    public String getSensorId() {
        return sensorId;
    }
}

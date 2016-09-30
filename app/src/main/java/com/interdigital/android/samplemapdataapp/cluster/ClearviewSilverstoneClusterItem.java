package com.interdigital.android.samplemapdataapp.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.modules.clearviewsilverstone.device.Device;
import net.uk.onetransport.android.modules.clearviewsilverstone.traffic.TrafficItem;

public class ClearviewSilverstoneClusterItem implements ClusterItem {

    private LatLng position;
    private Device device;
    private TrafficItem trafficItem;

    public ClearviewSilverstoneClusterItem(Device device, TrafficItem[] trafficItems) {
        this.device = device;
        for (TrafficItem trafficItem : trafficItems) {
            if (device.getSensorId() == trafficItem.getSensorId()) {
                this.trafficItem = trafficItem;
            }
        }
        position = new LatLng(device.getLatitude(), device.getLongitude());
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public Device getDevice() {
        return device;
    }

    public TrafficItem getTrafficItem() {
        return trafficItem;
    }
}

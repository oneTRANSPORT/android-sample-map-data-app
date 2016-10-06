package com.interdigital.android.samplemapdataapp.cluster.herts;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.herts.carparks.CarPark;

public class CarParkClusterItem implements ClusterItem {

    private CarPark carPark;
    private LatLng position;

    public CarParkClusterItem(CarPark carPark) {
        this.carPark = carPark;
        position = new LatLng(carPark.getLatitude(), carPark.getLongitude());
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public CarPark getCarPark() {
        return carPark;
    }
}

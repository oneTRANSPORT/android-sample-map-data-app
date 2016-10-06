package com.interdigital.android.samplemapdataapp.layer.bucks;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.bucks.CarParkClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.bucks.CarParkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.CarParkClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.bucks.carparks.CarPark;
import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;

public class BucksCarParks extends ClusterBaseLayer<CarParkClusterItem> {

    public BucksCarParks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        CarPark[] carParks = BucksContentHelper.getLatestCarParks(getContext());
        for (CarPark carPark : carParks) {
            CarParkClusterItem carParkClusterItem = new CarParkClusterItem(carPark);
            getClusterItems().add(carParkClusterItem);
        }
    }

    @Override
    protected BaseClusterManager<CarParkClusterItem> newClusterManager() {
        return new CarParkClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<CarParkClusterItem> newClusterRenderer() {
        return new CarParkClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

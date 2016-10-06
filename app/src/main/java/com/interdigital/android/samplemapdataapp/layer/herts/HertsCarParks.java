package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.CarParkClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.CarParkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.CarParkClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.carparks.CarPark;
import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;

public class HertsCarParks extends ClusterBaseLayer<CarParkClusterItem> {

    public HertsCarParks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        CarPark[] carParks = HertsContentHelper.getLatestCarParks(getContext());
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

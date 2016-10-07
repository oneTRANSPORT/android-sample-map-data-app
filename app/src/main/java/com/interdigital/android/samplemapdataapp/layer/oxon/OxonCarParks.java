package com.interdigital.android.samplemapdataapp.layer.oxon;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.oxon.CarParkClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.oxon.CarParkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.oxon.CarParkClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.oxon.carparks.CarPark;
import net.uk.onetransport.android.county.oxon.provider.OxonContentHelper;

public class OxonCarParks extends ClusterBaseLayer<CarParkClusterItem> {

    public OxonCarParks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        CarPark[] carParks = OxonContentHelper.getLatestCarParks(getContext());
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

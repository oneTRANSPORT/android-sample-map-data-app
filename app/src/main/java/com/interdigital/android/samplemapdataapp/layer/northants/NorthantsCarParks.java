package com.interdigital.android.samplemapdataapp.layer.northants;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.northants.CarParkClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.northants.CarParkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.northants.CarParkClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.northants.carparks.CarPark;
import net.uk.onetransport.android.county.northants.provider.NorthantsContentHelper;

public class NorthantsCarParks extends ClusterBaseLayer<CarParkClusterItem> {

    public NorthantsCarParks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        CarPark[] carParks = NorthantsContentHelper.getLatestCarParks(getContext());
        int count = 0;
        for (CarPark carPark : carParks) {
            if (count < MAX_ITEMS) {
                CarParkClusterItem carParkClusterItem = new CarParkClusterItem(carPark);
                getClusterItems().add(carParkClusterItem);
                count++;
            }
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

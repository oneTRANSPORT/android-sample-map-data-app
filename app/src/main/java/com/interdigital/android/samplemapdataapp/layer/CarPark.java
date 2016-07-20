package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.CarParkClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.CarParkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.CarParkClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;

public class CarPark extends ClusterBaseLayer<CarParkClusterItem> {

    public CarPark(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void loadClusterItems() throws Exception {
        Cursor cursor = BucksContentHelper.getCarParks(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CarParkClusterItem carParkClusterItem = new CarParkClusterItem(cursor);
                getClusterItems().add(carParkClusterItem);
                cursor.moveToNext();
            }
        }
        cursor.close();
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

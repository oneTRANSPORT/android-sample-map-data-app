package com.interdigital.android.samplemapdataapp.cluster;

import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.provider.BucksContract;

public class CarParkClusterItem implements ClusterItem {

    private static final String BUCK_PREFIX = "BUCK-";

    private LatLng position;
    private String identity;
    private int totalParkingCapacity;
    private int entranceFull;

    // TODO    Refer to object rather than unpack cursor?
    public CarParkClusterItem(Cursor cursor) {
        identity = cursor.getString(cursor.getColumnIndex(
                BucksContract.BucksCarPark.COLUMN_CAR_PARK_IDENTITY))
                .replace(BUCK_PREFIX, "").replaceAll("_", " ");
        totalParkingCapacity = cursor.getInt(cursor.getColumnIndex(
                BucksContract.BucksCarPark.COLUMN_TOTAL_PARKING_CAPACITY));
        entranceFull = cursor.getInt(cursor.getColumnIndex(
                BucksContract.BucksCarPark.COLUMN_ENTRANCE_FULL));
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.BucksCarPark.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.BucksCarPark.COLUMN_LONGITUDE));
        position = new LatLng(latitude, longitude);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String getIdentity() {
        return identity;
    }

    public int getTotalParkingCapacity() {
        return totalParkingCapacity;
    }

    public int getEntranceFull() {
        return entranceFull;
    }
}

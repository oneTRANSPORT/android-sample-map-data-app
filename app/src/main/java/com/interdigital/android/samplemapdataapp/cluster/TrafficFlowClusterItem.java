package com.interdigital.android.samplemapdataapp.cluster;

import android.database.Cursor;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import static net.uk.onetransport.android.county.bucks.provider.BucksContract.BucksTrafficFlow;

public class TrafficFlowClusterItem implements ClusterItem {

    private LatLng position;
    private int vehicleFlow;
    private String locationReference;
    private LatLng fromLatLng;

    public TrafficFlowClusterItem(Cursor cursor) {
        vehicleFlow = cursor.getInt(cursor.getColumnIndex(
                BucksTrafficFlow.COLUMN_VEHICLE_FLOW));
        locationReference = cursor.getString(cursor.getColumnIndex(
                BucksTrafficFlow.COLUMN_TO_DESCRIPTOR));
        if (TextUtils.isEmpty(locationReference)) {
            locationReference = cursor.getString(cursor.getColumnIndex(
                    BucksTrafficFlow.COLUMN_FROM_DESCRIPTOR));
        }
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksTrafficFlow.COLUMN_TO_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksTrafficFlow.COLUMN_TO_LONGITUDE));
        position = new LatLng(latitude, longitude);
        latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksTrafficFlow.COLUMN_FROM_LATITUDE));
        longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksTrafficFlow.COLUMN_FROM_LONGITUDE));
        fromLatLng = new LatLng(latitude, longitude);
    }

    public boolean shouldAdd() {
        if ((position.latitude == 0 && position.longitude == 0)
                || (fromLatLng.latitude == 0 && fromLatLng.longitude == 0)) {
            return false;
        }
        if (TextUtils.isEmpty(locationReference)) {
            return false;
        }
        return vehicleFlow != 0;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public int getVehicleFlow() {
        return vehicleFlow;
    }

    public String getLocationReference() {
        return locationReference;
    }

    public LatLng getFromLatLng() {
        return fromLatLng;
    }
}

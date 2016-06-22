package com.interdigital.android.samplemapdataapp.cluster;

import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.provider.BucksContract;

public class VmsClusterItem implements ClusterItem {

    private LatLng position;
    private String[] vmsLegends;
    private String locationReference;

    public VmsClusterItem(Cursor cursor) {
        String legendStr = cursor.getString(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_VMS_LEGENDS));
        vmsLegends = legendStr.split("\\|");
        locationReference = cursor.getString(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_DESCRIPTOR));
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_LONGITUDE));
        position = new LatLng(latitude, longitude);
    }

    public boolean shouldAdd() {
        if (position == null || (position.latitude == 0 && position.longitude == 0)) {
            return false;
        }
        if (vmsLegends == null || vmsLegends.length == 0) {
            return false;
        }
        for (String line : vmsLegends) {
            if (line.trim().length() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String[] getVmsLegends() {
        return vmsLegends;
    }

    public String getLocationReference() {
        return locationReference;
    }
}

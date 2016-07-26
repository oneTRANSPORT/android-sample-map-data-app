package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.ClearviewSilverstoneClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.ClearviewSilverstoneClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.ClearviewSilverstoneClusterRenderer;

import net.uk.onetransport.android.modules.clearviewsilverstone.provider.CvsContentHelper;
import net.uk.onetransport.android.modules.clearviewsilverstone.provider.CvsContract;
import net.uk.onetransport.android.modules.clearviewsilverstone.traffic.Traffic;

import java.util.ArrayList;
import java.util.Calendar;

public class ClearviewSilverstone extends ClusterBaseLayer<ClearviewSilverstoneClusterItem> {


    public ClearviewSilverstone(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void loadClusterItems() throws Exception {
        Cursor cursor = CvsContentHelper.getTraffic(getContext());
        SparseArray<ArrayList<Traffic>> trafficArray = new SparseArray<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer sensorId = cursor.getInt(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneTraffic.COLUMN_SENSOR_ID));
                if (trafficArray.get(sensorId) == null) {
                    trafficArray.put(sensorId, new ArrayList<Traffic>());
                }
                Traffic traffic = new Traffic();
                traffic.setTime(cursor.getString(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneTraffic.COLUMN_TIMESTAMP)));
                traffic.setDirection(cursor.getInt(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneTraffic.COLUMN_DIRECTION)) != 0);
                trafficArray.get(sensorId).add(traffic);
                cursor.moveToNext();
            }
        }
        cursor.close();

        SparseArray<SparseIntArray> vehiclesIn = new SparseArray<>();
        SparseArray<SparseIntArray> vehiclesOut = new SparseArray<>();
        cursor = CvsContentHelper.getHistory(getContext());
        if (cursor.moveToFirst()) {
            Calendar calendar = Calendar.getInstance();
            while (!cursor.isAfterLast()) {
                int sensorId = cursor.getInt(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneHistory.COLUMN_SENSOR_ID));
                String timestamp = cursor.getString(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneHistory.COLUMN_TIMESTAMP));
                int vehicles = cursor.getInt(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneHistory.COLUMN_VEHICLES));
                // False == incoming.
                boolean direction = cursor.getInt(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneHistory.COLUMN_DIRECTION)) == 1;
                int year = Integer.parseInt(timestamp.replaceFirst("-.*", ""));
                int month = Integer.parseInt(timestamp.replaceFirst("^[0-9]+-", "").replaceFirst("-.*", ""));
                int day = Integer.parseInt(timestamp.replaceFirst(".*-", "").replaceFirst(" .*", ""));
                int hour = Integer.parseInt(timestamp.replaceFirst(".* ", ""));
                calendar.set(year, month, day, hour, 0);
                long millis = calendar.getTimeInMillis();
                if (direction) {
                    if (vehiclesOut.get(sensorId) == null) {
                        vehiclesOut.put(sensorId, new SparseIntArray());
                    }
                    vehiclesOut.get(sensorId).put((int) (millis / 1000), vehicles);
                } else {
                    if (vehiclesIn.get(sensorId) == null) {
                        vehiclesIn.put(sensorId, new SparseIntArray());
                    }
                    vehiclesIn.get(sensorId).put((int) (millis / 1000), vehicles);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();

        cursor = CvsContentHelper.getDevices(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ClearviewSilverstoneClusterItem csci = new ClearviewSilverstoneClusterItem(cursor,
                        trafficArray, vehiclesIn, vehiclesOut);
                getClusterItems().add(csci);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    @Override
    protected BaseClusterManager<ClearviewSilverstoneClusterItem> newClusterManager() {
        return new ClearviewSilverstoneClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<ClearviewSilverstoneClusterItem> newClusterRenderer() {
        return new ClearviewSilverstoneClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

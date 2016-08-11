package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;

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

public class ClearviewSilverstone extends ClusterBaseLayer<ClearviewSilverstoneClusterItem> {


    public ClearviewSilverstone(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
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

        cursor = CvsContentHelper.getDevices(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ClearviewSilverstoneClusterItem csci = new ClearviewSilverstoneClusterItem(cursor,
                        trafficArray);
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

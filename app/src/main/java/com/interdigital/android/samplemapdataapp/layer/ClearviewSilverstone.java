package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;

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
import java.util.HashMap;

public class ClearviewSilverstone extends ClusterBaseLayer<ClearviewSilverstoneClusterItem> {


    public ClearviewSilverstone(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void loadClusterItems() throws Exception {
        Cursor cursor = CvsContentHelper.getTraffic(getContext());
        HashMap<Integer, ArrayList<Traffic>> trafficMap = new HashMap<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer sensorId = cursor.getInt(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneTraffic.COLUMN_SENSOR_ID));
                if (!trafficMap.containsKey(sensorId)) {
                    trafficMap.put(sensorId, new ArrayList<Traffic>());
                }
                Traffic traffic = new Traffic();
                traffic.setTime(cursor.getString(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneTraffic.COLUMN_TIMESTAMP)));
                traffic.setDirection(cursor.getInt(cursor.getColumnIndex(
                        CvsContract.ClearviewSilverstoneTraffic.COLUMN_DIRECTION)) != 0);
                trafficMap.get(sensorId).add(traffic);
                cursor.moveToNext();
            }
        }
        cursor.close();
        cursor = CvsContentHelper.getDevices(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ClearviewSilverstoneClusterItem csci = new ClearviewSilverstoneClusterItem(cursor, trafficMap);
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

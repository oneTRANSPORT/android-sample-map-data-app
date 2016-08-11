package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;

public class VariableMessageSign extends ClusterBaseLayer<VmsClusterItem> {

    public VariableMessageSign(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        Cursor cursor = BucksContentHelper.getVariableMessageSigns(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                VmsClusterItem vmsClusterItem = new VmsClusterItem(cursor);
                if (vmsClusterItem.shouldAdd()) {
                    getClusterItems().add(vmsClusterItem);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    @Override
    protected BaseClusterManager<VmsClusterItem> newClusterManager() {
        return new VmsClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<VmsClusterItem> newClusterRenderer() {
        return new VmsClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

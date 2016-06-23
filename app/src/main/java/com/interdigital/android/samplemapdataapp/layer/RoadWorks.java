package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterRenderer;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;

public class RoadWorks extends BaseLayer<RoadWorksClusterItem> {

    public RoadWorks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void loadClusterItems() throws Exception {
        Cursor cursor = BucksContentHelper.getRoadWorks(getContext());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                RoadWorksClusterItem roadWorksClusterItem = new RoadWorksClusterItem(cursor);
                getClusterItems().add(roadWorksClusterItem);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    @Override
    protected BaseClusterManager<RoadWorksClusterItem> newClusterManager() {
        return new RoadWorksClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<RoadWorksClusterItem> newClusterRenderer() {
        return new RoadWorksClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

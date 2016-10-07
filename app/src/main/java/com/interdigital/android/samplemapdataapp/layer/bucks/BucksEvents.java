package com.interdigital.android.samplemapdataapp.layer.bucks;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.bucks.EventClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.bucks.EventClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.bucks.EventClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.bucks.events.Event;
import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;

public class BucksEvents extends ClusterBaseLayer<EventClusterItem> {

    public BucksEvents(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        Event[] events = BucksContentHelper.getLatestEvents(getContext());
        double[] coords = new double[events.length];
        int c = 0;
        for (int i = events.length - 1; i >= 0; i--) {
            // Prevent concurrent locations.
            double coord = events[i].getLatitude() * 360 + events[i].getLongitude();
            boolean found = false;
            for (int j = 0; j < c; j++) {
                if (coords[j] == coord) {
                    found = true;
                }
            }
            if (!found) {
                if (isInDate(events[i].getStartOfPeriod())
                        || isInDate(events[i].getEndOfPeriod())
                        || isInDate(events[i].getOverallStartTime())
                        || isInDate(events[i].getOverallEndTime())) {
                    EventClusterItem eventClusterItem = new EventClusterItem(events[i]);
                    if (eventClusterItem.shouldAdd()) {
                        getClusterItems().add(eventClusterItem);
                        coords[c++] = coord;
                    }
                }
            }
        }
        Log.i("BucksEvents", "Found " + events.length
                + ", discarded " + (events.length - getClusterItems().size()));
    }

    @Override
    protected BaseClusterManager<EventClusterItem> newClusterManager() {
        return new EventClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<EventClusterItem> newClusterRenderer() {
        return new EventClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

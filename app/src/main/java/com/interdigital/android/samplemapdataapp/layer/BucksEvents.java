package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.EventClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.EventClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.EventClusterRenderer;

import net.uk.onetransport.android.county.bucks.events.Event;
import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;

public class BucksEvents extends ClusterBaseLayer<EventClusterItem> {

    public BucksEvents(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        Event[] events = BucksContentHelper.getLatestEvents(getContext());
        for (Event event : events) {
            EventClusterItem eventClusterItem = new EventClusterItem(event);
            if (eventClusterItem.shouldAdd()) {
                getClusterItems().add(eventClusterItem);
            }
        }
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

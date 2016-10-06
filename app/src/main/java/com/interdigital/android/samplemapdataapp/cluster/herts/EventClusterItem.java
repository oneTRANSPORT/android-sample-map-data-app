package com.interdigital.android.samplemapdataapp.cluster.herts;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.herts.events.Event;

public class EventClusterItem implements ClusterItem {

    private LatLng position;
    private Event event;

    public EventClusterItem(Event event) {
        this.event = event;
        position = new LatLng(event.getLatitude(), event.getLongitude());
    }

    public boolean shouldAdd() {
        if (position.latitude == 0 && position.longitude == 0) {
            return false;
        }
        if (!event.getValidityStatus().equals("active")) {
            return false;
        }
        return !TextUtils.isEmpty(event.getDescription());
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public Event getEvent() {
        return event;
    }
}

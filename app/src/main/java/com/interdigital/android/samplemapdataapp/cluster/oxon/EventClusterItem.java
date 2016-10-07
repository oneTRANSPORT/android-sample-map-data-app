package com.interdigital.android.samplemapdataapp.cluster.oxon;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.oxon.events.Event;

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
        if (event.getDescription() == null) {
            return false;
        }
        return event.getDescription().matches(".*Location.*Reason.*Status.*");
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public Event getEvent() {
        return event;
    }
}

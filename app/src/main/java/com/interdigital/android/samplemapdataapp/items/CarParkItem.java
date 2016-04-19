package com.interdigital.android.samplemapdataapp.items;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.interdigital.android.samplemapdataapp.MarkerData;
import com.interdigital.android.samplemapdataapp.R;

import java.util.HashMap;

public abstract class CarParkItem extends Item {

    private boolean full = false;

    public CarParkItem(String title) {
        super(title);
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
//        setMarker(googleMap.addMarker(
//                new MarkerOptions()
//                        .title(getTitle())
//                        .position(getLatLng())
//                        .anchor(0.5f, 0.5f)
//                        .icon(BitmapDescriptorFactory.fromResource(getPlainResource()))));
//        markerMap.put(getMarker(), this);
    }

//    @Override
    public void setMarkerIcon(@MarkerData.MarkerType int markerType) {
//        int resource;
//        switch (markerType) {
//            case MarkerData.MARKER_TYPE_UPDATING:
//                resource = getUpdatingResource();
//                break;
//            case MarkerData.MARKER_TYPE_UPDATED:
//                if (full && getAlternateResource() != 0) {
//                    resource = getAlternateResource();
//                } else {
//                    resource = getUpdatedResource();
//                }
//                break;
//            case MarkerData.MARKER_TYPE_PLAIN:
//            default:
//                resource = getPlainResource();
//                break;
//        }
//        getMarker().setIcon(BitmapDescriptorFactory.fromResource(resource));
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}

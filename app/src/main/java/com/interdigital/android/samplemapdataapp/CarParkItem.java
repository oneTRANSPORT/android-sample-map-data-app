package com.interdigital.android.samplemapdataapp;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public abstract class CarParkItem extends Item {

    private boolean full = false;

    public CarParkItem(String title, double latitude, double longitude) {
        super(title, latitude, longitude);
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(getPlainResource()))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public void setMarkerIcon(@MarkerData.MarkerType int markerType) {
        int resource;
        switch (markerType) {
            case MarkerData.MARKER_TYPE_UPDATING:
                resource = getUpdatingResource();
                break;
            case MarkerData.MARKER_TYPE_UPDATED:
                if (full && getAlternateResource() != 0) {
                    resource = getAlternateResource();
                } else {
                    resource = getUpdatedResource();
                }
                break;
            case MarkerData.MARKER_TYPE_PLAIN:
            default:
                resource = getPlainResource();
                break;
        }
        getMarker().setIcon(BitmapDescriptorFactory.fromResource(resource));
    }

    @Override
    public int getPlainResource() {
        return R.drawable.carpark_icon;
    }

    @Override
    public int getUpdatingResource() {
        return R.drawable.carpark_updating_icon;
    }

    @Override
    public int getUpdatedResource() {
        return R.drawable.carpark_updated_icon;
    }

    @Override
    public int getAlternateResource() {
        return R.drawable.carpark_full_icon;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}

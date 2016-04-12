package com.interdigital.android.samplemapdataapp;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarParkItem extends Item {

    public CarParkItem(String title, double latitude, double longitude) {
        super(title, latitude, longitude);
    }

    public void addMarker(GoogleMap googleMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(getPlainResource()))));
    }

    public void setMarkerIcon(@MarkerData.MarkerType int markerType) {
        int resource;
        switch (markerType) {
            case MarkerData.MARKER_TYPE_UPDATING:
                resource = getUpdatingResource();
                break;
            case MarkerData.MARKER_TYPE_UPDATED:
                if (Math.random() < 0.3 && getAlternateResource() != 0) {
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
}

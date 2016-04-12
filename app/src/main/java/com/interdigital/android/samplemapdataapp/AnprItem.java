package com.interdigital.android.samplemapdataapp;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

public class AnprItem extends Item {

    public AnprItem(String title, double latitude, double longitude) {
        super(title, latitude, longitude);
    }

    public void addMarker(GoogleMap googleMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.anpr_icon))));
    }

    public void setMarkerIcon(@MarkerData.MarkerType int markerType) {
        int resource;
        switch (markerType) {
            case MarkerData.MARKER_TYPE_UPDATING:
                resource = getUpdatingResource();
                break;
            case MarkerData.MARKER_TYPE_UPDATED:
                resource = getUpdatedResource();
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
        return R.drawable.anpr_icon;
    }

    @Override
    public int getUpdatingResource() {
        return R.drawable.anpr_updating_icon;
    }

    @Override
    public int getUpdatedResource() {
        return R.drawable.anpr_updated_icon;
    }

    @Override
    public int getAlternateResource() {
        return R.drawable.anpr_icon;
    }
}

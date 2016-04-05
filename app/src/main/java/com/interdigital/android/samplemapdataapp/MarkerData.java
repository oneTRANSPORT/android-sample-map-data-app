package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class MarkerData implements GoogleMap.InfoWindowAdapter {

    @Retention(SOURCE)
    @IntDef({MARKER_TYPE_PLAIN, MARKER_TYPE_UPDATING, MARKER_TYPE_UPDATED})
    public @interface MarkerType {
    }

    public static final int MARKER_TYPE_PLAIN = 1;
    public static final int MARKER_TYPE_UPDATING = 2;
    public static final int MARKER_TYPE_UPDATED = 3;

    private static final String[] CARPARK_NAMES = {"Desborough", "DesboroughRd", "Dovecot",
            "Easton_Street", "Eden", "Exchange_St", "Friars_Square", "Friarscroft", "Hampden_House",
            "Swan", "Upper_Hundreds", "Walton_Street_MSCP"};
    private static final LatLng[] CARPARK_LAT_LNGS = {
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
            new LatLng(51.62821 + Math.random() / 40, -0.7502827 + Math.random() / 40),
    };

    private static final String[] ANPR_NAMES = {"ANPR 1", "ANPR 2", "ANPR 3", "ANPR 4"};
    private static final LatLng[] ANPR_LAT_LNGS = {
            new LatLng(51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
            new LatLng(51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
            new LatLng(51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
            new LatLng(51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20)
    };

    private Marker[] carparkMarkers = new Marker[CARPARK_NAMES.length];
    private Marker[] anprMarkers = new Marker[ANPR_NAMES.length];
    private Context context;

    public MarkerData(Context context) {
        this.context = context;
    }

    public void addMapMarkers(GoogleMap googleMap) {
        for (int i = 0; i < CARPARK_NAMES.length; i++) {
            carparkMarkers[i] = googleMap.addMarker(
                    new MarkerOptions()
                            .position(CARPARK_LAT_LNGS[i])
                            .anchor(0.5f, 0.5f)
                            .title(CARPARK_NAMES[i])
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.carpark_icon)));
        }

        for (int i = 0; i < ANPR_NAMES.length; i++) {
            anprMarkers[i] = googleMap.addMarker(
                    new MarkerOptions()
                            .position(ANPR_LAT_LNGS[i])
                            .anchor(0.5f, 0.5f)
                            .title(ANPR_NAMES[i])
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.anpr_icon)));
        }
    }

    public void pointCamera(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CARPARK_LAT_LNGS[0], 12));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView textView = new TextView(context);
        if (marker.getTitle().contains("ANPR")) {
            textView.setText("ANPR Camera");
            textView.setTextColor(0xffff0000);
        } else {
            textView.setText("High Wycombe car park");
            textView.setTextColor(0xff0000ff);
        }
        return textView;
    }

    public void setMarkerIcons(@MarkerType int markerType) {
        setMarkerIcons(markerType, carparkMarkers, R.drawable.carpark_icon,
                R.drawable.carpark_updating_icon, R.drawable.carpark_icon, R.drawable.carpark_full_icon);
        setMarkerIcons(markerType, anprMarkers, R.drawable.anpr_icon,
                R.drawable.anpr_updating_icon, R.drawable.anpr_icon, 0);
    }

    private void setMarkerIcons(@MarkerType int markerType, Marker[] markers,
                                int resourcePlain, int resourceUpdating, int resourceUpdated,
                                int alternateResource) {
        for (int i = 0; i < markers.length; i++) {
            int resource;
            switch (markerType) {
                case MARKER_TYPE_UPDATING:
                    resource = resourceUpdating;
                    break;
                case MARKER_TYPE_UPDATED:
                    if (Math.random() < 0.3 && alternateResource != 0) {
                        resource = alternateResource;
                    } else {
                        resource = resourceUpdated;
                    } break;
                case MARKER_TYPE_PLAIN:
                default:
                    resource = resourcePlain;
                    break;
            }
            markers[i].setIcon(BitmapDescriptorFactory.fromResource(resource));
        }
    }
}

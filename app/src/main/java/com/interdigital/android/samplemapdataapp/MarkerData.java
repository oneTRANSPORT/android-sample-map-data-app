package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.lang.annotation.Retention;
import java.util.HashMap;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class MarkerData implements GoogleMap.InfoWindowAdapter {

    @Retention(SOURCE)
    @IntDef({MARKER_TYPE_PLAIN, MARKER_TYPE_UPDATING, MARKER_TYPE_UPDATED})
    public @interface MarkerType {
    }

    public static final int MARKER_TYPE_PLAIN = 1;
    public static final int MARKER_TYPE_UPDATING = 2;
    public static final int MARKER_TYPE_UPDATED = 3;

    private Item[] items = {
            new CloudAmberItem("Desborough", 51.63254, -0.759396434),
            new CloudAmberItem("Desborough Road", 51.6324043, -0.759920359),
            new CloudAmberItem("Dovecot", 51.63209, -0.7551319),
            new CloudAmberItem("Easton Street", 51.6288719, -0.746551454),
            new CloudAmberItem("Eden", 51.62928, -0.7544288),
            new DemoUnitItem("Exchange Street", 51.8160477, -0.810069442),
            new DemoUnitItem("Friars Square", 51.81417, -0.813673258),
            new DemoUnitItem("Friarscroft", 51.8149834, -0.8187729),
            // We only need eight.
//            new CloudAmberItem("Hampden House", 51.8173523, -0.8081491),
//            new CloudAmberItem("Swan", 51.62821, -0.7502827),
//            new CloudAmberItem("Upper Hundreds", 51.8174934, -0.809059262),
//            new CloudAmberItem("Walton Street MSCP", 51.8122864, -0.809660852)
            new AnprItem("ANPR1", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
            new AnprItem("ANPR2", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
            new AnprItem("ANPR3", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
            new AnprItem("ANPR4", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20)
    };
    // Needed for quick look-up.
    private HashMap<Marker, Item> markerMap = new HashMap<>();
    private Context context;

    public MarkerData(Context context) {
        this.context = context;
    }

    public void addMapMarkers(GoogleMap googleMap) {
        for (int i = 0; i < items.length; i++) {
            items[i].addMarker(googleMap);
            markerMap.put(items[i].getMarker(), items[i]);
        }
    }

    public void pointCamera(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(items[0].getLatLng(), 12));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return markerMap.get(marker).getInfoContents(context);
//        TextView textView = new TextView(context);
//        if (marker.getTitle().contains("ANPR")) {
//            textView.setText("ANPR Camera");
//            textView.setTextColor(0xffff0000);
//        } else {
//            textView.setText(markerMap.get(marker).getTitle());
//            textView.setTextColor(0xff0000ff);
//        }
//        return textView;
    }

    public void updateAll() {
        for (Item item : items) {
            item.update();
        }
    }
}

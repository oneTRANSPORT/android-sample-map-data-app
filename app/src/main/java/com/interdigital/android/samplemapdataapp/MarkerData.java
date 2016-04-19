package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.items.Item;

import java.lang.annotation.Retention;
import java.util.ArrayList;
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

    private ArrayList<Item> itemList = new ArrayList<>();

    //    private Item[] items = {
//            new CloudAmberItem("Desborough", "BUCK-Desborough", 51.63254, -0.759396434),
//            new CloudAmberItem("Desborough Road", "BUCK-DesboroughRd", 51.6324043, -0.759920359),
//            new CloudAmberItem("Dovecot", "BUCK-Dovecot", 51.63209, -0.7551319),
//            new CloudAmberItem("Easton Street", "BUCK-Easton_Street", 51.6288719, -0.746551454),
//            new CloudAmberItem("Eden", "BUCK-Eden", 51.62928, -0.7544288),
//            new WorldsensingItem(0),
//            new WorldsensingItem(1),
//            new WorldsensingItem(2),
    // We only need eight.
//            new CloudAmberItem("Hampden House", 51.8173523, -0.8081491),
//            new CloudAmberItem("Swan", 51.62821, -0.7502827),
//            new CloudAmberItem("Upper Hundreds", 51.8174934, -0.809059262),
//            new CloudAmberItem("Walton Street MSCP", 51.8122864, -0.809660852)
//            new AnprItem("ANPR1", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
//            new AnprItem("ANPR2", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
//            new AnprItem("ANPR3", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20),
//            new AnprItem("ANPR4", 51.62821 + Math.random() / 20, -0.7502827 + Math.random() / 20)
//    };
    // Needed for quick look-up.
    private HashMap<Marker, Item> markerMap = new HashMap<>();
    private Context context;

    public MarkerData(Context context) {
        this.context = context;
    }

    public void addMapMarkers(GoogleMap googleMap) {
        new LoadMarkerTask(googleMap, itemList, markerMap).execute();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return markerMap.get(marker).getInfoContents(context);
    }

    public void updateAll() {
//        for (Item item : itemList) {
//            item.update();
//        }
    }
}

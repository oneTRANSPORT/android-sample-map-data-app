package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class MarkerData implements GoogleMap.InfoWindowAdapter {

    private ArrayList<Item> itemList = new ArrayList<>();
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

    public void updateAll(){
        // TODO Update Worldsensing stuff.  Should it go here or in WS items?
    }
}

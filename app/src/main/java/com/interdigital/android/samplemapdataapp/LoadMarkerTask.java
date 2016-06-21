package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.json.items.CaCarParkItem;
import com.interdigital.android.samplemapdataapp.json.items.CaRoadWorksItem;
import com.interdigital.android.samplemapdataapp.json.items.CaTrafficFlowItem;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LoadMarkerTask extends AsyncTask<Void, Integer, Void> {

    private GoogleMap googleMap;
    private ArrayList<Item> itemList = new ArrayList<>();
    private HashMap<Marker, Item> markerMap = new HashMap<>();
    private ProgressBar progressBar;
    private boolean moveMap;
    // TODO    Weak reference.
    private MapsActivity mapsActivity;
    private Context context;
    private HashSet<Integer> visibleTypes;
    private VmsClusterManager vmsClusterManager;
    private VmsClusterRenderer vmsClusterRenderer;
    private ArrayList<VmsClusterItem> vmsClusterItems = new ArrayList<>();

    public LoadMarkerTask(GoogleMap googleMap, HashMap<Marker, Item> markerMap,
                          ProgressBar progressBar, boolean moveMap, MapsActivity mapsActivity,
                          HashSet<Integer> visibleTypes, VmsClusterManager vmsClusterManager,
                          VmsClusterRenderer vmsClusterRenderer) {
        this.googleMap = googleMap;
        this.markerMap = markerMap;
        this.progressBar = progressBar;
        this.moveMap = moveMap;
        this.mapsActivity = mapsActivity;
        this.visibleTypes = visibleTypes;
        this.vmsClusterManager = vmsClusterManager;
        this.vmsClusterRenderer = vmsClusterRenderer;
        context = mapsActivity.getApplicationContext();
        Log.i("LoadMarkerTask", "Invoking load markers");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            addWorldSensing();
            loadCaVms();
            loadCaCarParks();
            loadCaTrafficFlow();
            loadCaRoadWorks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        boolean worldsensingPresent = false;
        ArrayList<Marker> deletedEntries = new ArrayList<>();
        // Do not remove Worldsensing items as they may be mid-download.
        for (Map.Entry<Marker, Item> entry : markerMap.entrySet()) {
            if (!(entry.getValue() instanceof WorldsensingItem)) {
                deletedEntries.add(entry.getKey());
            } else {
                worldsensingPresent = true;
            }
        }
        for (Marker marker : deletedEntries) {
            markerMap.remove(marker);
            marker.remove();
        }
        for (Item item : itemList) {
            if (!(item instanceof WorldsensingItem && worldsensingPresent)) {
                item.addMarker(googleMap, markerMap);
                if (!visibleTypes.contains(item.getType())) {
                    item.getMarker().setVisible(false);
                }
            }
        }

        for (VmsClusterItem vmsClusterItem : vmsClusterItems) {
            vmsClusterManager.addItem(vmsClusterItem);  // Must be on UI thread?
        }

        googleMap.setInfoWindowAdapter(vmsClusterManager.getMarkerManager());
        // Move to about the middle of Aylesbury so we can see Worldsensing, ANPR and car park items.
        // Zoom out for VMS.
        if (moveMap) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(51.8128587, -0.8239542), 13));
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void addWorldSensing() {
        if (markerMap.size() == 0) {
            for (int i = 0; i < 6; i++) {
                itemList.add(new WorldsensingItem(i, mapsActivity));
            }
        }
    }

    private void loadCaVms() throws Exception {
        Cursor cursor = BucksContentHelper.getVariableMessageSigns(context);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                VmsClusterItem vmsClusterItem = new VmsClusterItem(cursor);
                if (vmsClusterItem.shouldAdd()) {
                    vmsClusterItems.add(vmsClusterItem);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    private void loadCaCarParks() throws Exception {
        Cursor cursor = BucksContentHelper.getCarParks(context);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CaCarParkItem caCarParkItem = new CaCarParkItem(cursor);
                if (caCarParkItem.shouldAdd()) {
                    itemList.add(caCarParkItem);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    private void loadCaTrafficFlow() throws Exception {
        Cursor cursor = BucksContentHelper.getTrafficFlows(context);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CaTrafficFlowItem caTrafficFlowItem = new CaTrafficFlowItem(cursor);
                if (caTrafficFlowItem.shouldAdd()) {
                    itemList.add(caTrafficFlowItem);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    private void loadCaRoadWorks() throws Exception {
        Cursor cursor = BucksContentHelper.getRoadWorks(context);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CaRoadWorksItem caRoadWorksItem = new CaRoadWorksItem(cursor);
                if (caRoadWorksItem.shouldAdd()) {
                    itemList.add(caRoadWorksItem);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}

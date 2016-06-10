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
import com.interdigital.android.samplemapdataapp.json.items.CaVmsItem;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import net.uk.onetransport.android.county.bucks.locations.SegmentLocation;
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
    private HashMap<String, SegmentLocation> segmentLocationMap = new HashMap<>();
    private boolean moveMap;
    // TODO    Weak reference.
    private MapsActivity mapsActivity;
    private Context context;
    private HashSet<Integer> visibleTypes;

    public LoadMarkerTask(GoogleMap googleMap, HashMap<Marker, Item> markerMap,
                          ProgressBar progressBar, boolean moveMap, MapsActivity mapsActivity,
                          HashSet<Integer> visibleTypes) {
        this.googleMap = googleMap;
        this.markerMap = markerMap;
        this.progressBar = progressBar;
        this.moveMap = moveMap;
        this.mapsActivity = mapsActivity;
        this.visibleTypes = visibleTypes;
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
                CaVmsItem caVmsItem = new CaVmsItem(cursor);
                if (caVmsItem.shouldAdd()) {
                    itemList.add(caVmsItem);
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
        Cursor cursor = BucksContentHelper.getTrafficFlowJoinLocations(context);
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


        // TODO Currently not getting any latitude or longitude coordinates.

//        SegmentLocationArray segmentLocationArray = SegmentLocationArray.getSegmentLocationArray(
//                CseDetails.aeId, CseDetails.baseUrl, CseDetails.userName, CseDetails.password);
//
//        HashMap<String, SegmentLocation> segmentLocationMap = new HashMap<>();
//        for (SegmentLocation segmentLocation : segmentLocationArray.getSegmentLocations()) {
//            segmentLocationMap.put(segmentLocation.getLocationId(), segmentLocation);
//        }
//
//        TrafficFlowArray trafficFlowArray = TrafficFlowArray.getTrafficFlowArray(CseDetails.aeId,
//                CseDetails.baseUrl, CseDetails.userName, CseDetails.password);
//
//        for (TrafficFlow trafficFlow : trafficFlowArray.getTrafficFlows()) {
//            CaTrafficFlowItem caTrafficFlowItem = new CaTrafficFlowItem(trafficFlow,
//                    segmentLocationMap);
//            if (caTrafficFlowItem.shouldAdd()) {
//                itemList.add(caTrafficFlowItem);
//            }
//        }

        // TODO    Hopefully we don't need any of this once the Bucks oneTransport library
        // TODO    is doing it.

//        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
//                CseDetails.baseUrl, "BCCTrafficFlowFeedImport/All",
//                CseDetails.userName, CseDetails.password);
//        String content = contentInstance.getContent();
//        CaTrafficFlowItem[] caTrafficFlowItems = gson.fromJson(content, CaTrafficFlowItem[].class);
//        HashMap<String, CaTrafficFlowItem> flowMap = new HashMap<>();
//        for (CaTrafficFlowItem caTrafficFlowItem : caTrafficFlowItems) {
//            String locationReference = caTrafficFlowItem.getLocationReference();
//            if (!flowMap.containsKey(locationReference)) {
//                flowMap.put(locationReference, caTrafficFlowItem);
//            } else {
//                CaTrafficFlowItem existingCaTrafficFlowItem = flowMap.get(locationReference);
//                if (!TextUtils.isEmpty(caTrafficFlowItem.getAverageVehicleSpeed())) {
//                    existingCaTrafficFlowItem.setAverageVehicleSpeed(caTrafficFlowItem.getAverageVehicleSpeed());
//                }
//                if (!TextUtils.isEmpty(caTrafficFlowItem.getFreeFlowSpeed())) {
//                    existingCaTrafficFlowItem.setFreeFlowSpeed(caTrafficFlowItem.getFreeFlowSpeed());
//                }
//                if (!TextUtils.isEmpty(caTrafficFlowItem.getFreeFlowTravelTime())) {
//                    existingCaTrafficFlowItem.setFreeFlowTravelTime(caTrafficFlowItem.getFreeFlowTravelTime());
//                }
//                if (!TextUtils.isEmpty(caTrafficFlowItem.getTravelTime())) {
//                    existingCaTrafficFlowItem.setTravelTime(caTrafficFlowItem.getTravelTime());
//                }
//                if (!TextUtils.isEmpty(caTrafficFlowItem.getVehicleFlow())) {
//                    existingCaTrafficFlowItem.setVehicleFlow(caTrafficFlowItem.getVehicleFlow());
//                }
//            }
//        }
//        for (String key : flowMap.keySet()) {
//            CaTrafficFlowItem caTrafficFlowItem = flowMap.get(key);
//            caTrafficFlowItem.updateLocation(predefinedLocationMap);
//            if (caTrafficFlowItem.shouldAdd()) {
//                itemList.add(caTrafficFlowItem);
//            }
//        }
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

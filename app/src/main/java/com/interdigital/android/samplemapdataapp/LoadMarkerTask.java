package com.interdigital.android.samplemapdataapp;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.samplemapdataapp.json.PredefinedLocation;
import com.interdigital.android.samplemapdataapp.json.items.CaCarParkItem;
import com.interdigital.android.samplemapdataapp.json.items.CaTrafficFlowItem;
import com.interdigital.android.samplemapdataapp.json.items.CaVmsItem;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadMarkerTask extends AsyncTask<Void, Integer, Void> {

    private GoogleMap googleMap;
    private ArrayList<Item> itemList = new ArrayList<>();
    private HashMap<Marker, Item> markerMap = new HashMap<>();
    private ProgressBar progressBar;
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private HashMap<String, PredefinedLocation> predefinedLocationMap = new HashMap<>();
    private boolean moveMap;

    public LoadMarkerTask(GoogleMap googleMap, HashMap<Marker, Item> markerMap,
                          ProgressBar progressBar, boolean moveMap) {
        this.googleMap = googleMap;
        this.markerMap = markerMap;
        this.progressBar = progressBar;
        this.moveMap = moveMap;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            addWorldSensing();
            loadPredefinedLocations();
            loadCaVms();
            publishProgress(70);
            loadCaCarParks();
            publishProgress(84);
            loadCaTrafficFlow();
            publishProgress(100);
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
        for (Item item : itemList) {
            item.addMarker(googleMap, markerMap);
        }
        // Move to about the middle of Aylesbury so we can see Worldsensing, ANPR and car park items.
        // Zoom out for VMS.
        if (moveMap) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(51.8128587, -0.8239542), 13));
        }
    }

    private void addWorldSensing() {
        for (int i = 0; i < 6; i++) {
            itemList.add(new WorldsensingItem(i));
            publishProgress(14);
        }
    }

    private void loadPredefinedLocations() throws Exception {
        PredefinedLocation[][] predefinedLocations = new PredefinedLocation[3][];
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.baseUrl, "BCCFeedImportPredefinedSectionLocation/All",
                CseDetails.userName, CseDetails.password);
        predefinedLocations[0] = gson.fromJson(contentInstance.getContent(),
                PredefinedLocation[].class);
        publishProgress(28);
        // TODO This feed is currently broken.
        // TODO But we don't think there is much in it that we need.
//        contentInstance = Container.retrieveLatest(CseDetails.aeId,
//                CseDetails.BASE_URL, "BCCFeedImportPredefinedTrLocation/All",
//                CseDetails.USER_NAME, CseDetails.PASSWORD);
//        predefinedLocations[1] = gson.fromJson(contentInstance.getContent(),
//                PredefinedLocation[].class);
//        publishProgress(--);
        contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.baseUrl, "BCCFeedImportPredefinedVmsLocation/All",
                CseDetails.userName, CseDetails.password);
        predefinedLocations[1] = gson.fromJson(contentInstance.getContent(),
                PredefinedLocation[].class);
        publishProgress(42);
        contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.baseUrl, "BCCFeedImportPredefinedLinkLocation/All",
                CseDetails.userName, CseDetails.password);
        predefinedLocations[2] = gson.fromJson(contentInstance.getContent(),
                PredefinedLocation[].class);
        publishProgress(56);
        for (int i = 0; i < predefinedLocations.length; i++) {
            for (int j = 0; j < predefinedLocations[i].length; j++) {
                predefinedLocationMap.put(predefinedLocations[i][j].getLocationId(), predefinedLocations[i][j]);
            }
        }
    }

    private void loadCaVms() throws Exception {
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.baseUrl, "BCCSignSettingFeedImport/All",
                CseDetails.userName, CseDetails.password);
        CaVmsItem[] caVmsItems = gson.fromJson(contentInstance.getContent(), CaVmsItem[].class);
        for (CaVmsItem caVmsItem : caVmsItems) {
            caVmsItem.updateLocation(predefinedLocationMap);
            if (caVmsItem.shouldAdd()) {
                itemList.add(caVmsItem);
            }
        }
    }

    private void loadCaCarParks() throws Exception {
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.baseUrl, "BCCCarPark2FeedImport/All",
                CseDetails.userName, CseDetails.password);
        String content = contentInstance.getContent();
        CaCarParkItem[] caCarParkItems = gson.fromJson(content, CaCarParkItem[].class);
        for (CaCarParkItem caCarParkItem : caCarParkItems) {
            if (caCarParkItem.shouldAdd()) {
                itemList.add(caCarParkItem);
            }
        }
    }

    private void loadCaTrafficFlow() throws Exception {
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.baseUrl, "BCCTrafficFlowFeedImport/All",
                CseDetails.userName, CseDetails.password);
        String content = contentInstance.getContent();
        CaTrafficFlowItem[] caTrafficFlowItems = gson.fromJson(content, CaTrafficFlowItem[].class);
        HashMap<String, CaTrafficFlowItem> flowMap = new HashMap<>();
        for (CaTrafficFlowItem caTrafficFlowItem : caTrafficFlowItems) {
            String locationReference = caTrafficFlowItem.getLocationReference();
            if (!flowMap.containsKey(locationReference)) {
                flowMap.put(locationReference, caTrafficFlowItem);
            } else {
                CaTrafficFlowItem existingCaTrafficFlowItem = flowMap.get(locationReference);
                if (!TextUtils.isEmpty(caTrafficFlowItem.getAverageVehicleSpeed())) {
                    existingCaTrafficFlowItem.setAverageVehicleSpeed(caTrafficFlowItem.getAverageVehicleSpeed());
                }
                if (!TextUtils.isEmpty(caTrafficFlowItem.getFreeFlowSpeed())) {
                    existingCaTrafficFlowItem.setFreeFlowSpeed(caTrafficFlowItem.getFreeFlowSpeed());
                }
                if (!TextUtils.isEmpty(caTrafficFlowItem.getFreeFlowTravelTime())) {
                    existingCaTrafficFlowItem.setFreeFlowTravelTime(caTrafficFlowItem.getFreeFlowTravelTime());
                }
                if (!TextUtils.isEmpty(caTrafficFlowItem.getTravelTime())) {
                    existingCaTrafficFlowItem.setTravelTime(caTrafficFlowItem.getTravelTime());
                }
                if (!TextUtils.isEmpty(caTrafficFlowItem.getVehicleFlow())) {
                    existingCaTrafficFlowItem.setVehicleFlow(caTrafficFlowItem.getVehicleFlow());
                }
            }
        }
        for (String key : flowMap.keySet()) {
            CaTrafficFlowItem caTrafficFlowItem = flowMap.get(key);
            caTrafficFlowItem.updateLocation(predefinedLocationMap);
            if (caTrafficFlowItem.shouldAdd()) {
                itemList.add(caTrafficFlowItem);
            }
        }
    }
}

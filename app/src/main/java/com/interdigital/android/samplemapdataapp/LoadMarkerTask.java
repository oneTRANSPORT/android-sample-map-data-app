package com.interdigital.android.samplemapdataapp;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.samplemapdataapp.items.CaCarParkItem;
import com.interdigital.android.samplemapdataapp.items.CaTrafficFlowItem;
import com.interdigital.android.samplemapdataapp.items.CaVmsItem;
import com.interdigital.android.samplemapdataapp.items.Item;
import com.interdigital.android.samplemapdataapp.json.PredefinedLocation;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadMarkerTask extends AsyncTask<Void, Void, Void> {

    private GoogleMap googleMap;
    private ArrayList<Item> itemList;
    private HashMap<Marker, Item> markerMap = new HashMap<>();
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private HashMap<String, PredefinedLocation> predefinedLocationMap = new HashMap<>();

    public LoadMarkerTask(GoogleMap googleMap, ArrayList<Item> itemList,
                          HashMap<Marker, Item> markerMap) {
        this.googleMap = googleMap;
        this.itemList = itemList;
        this.markerMap = markerMap;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            addWorldSensing();
            loadPredefinedLocations();
            loadCaVms();
            loadCaCarParks();
            loadCaTrafficFlow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        for (Item item : itemList) {
            item.addMarker(googleMap, markerMap);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                itemList.get(0).getMarker().getPosition(), 12));
    }

    private void addWorldSensing() {
        itemList.add(new WorldsensingItem(0));
        itemList.add(new WorldsensingItem(1));
        itemList.add(new WorldsensingItem(2));
    }

    private void loadPredefinedLocations() throws Exception {
        // URL BCCFeedImportPredefinedSectionLocation/All
        // URL BCCFeedImportPredefinedTrLocation/All TODO Broken, needs fix.
        // URL BCCFeedImportPredefinedVmsLocation/All
        // URL BCCFeedImportPredefinedLinkLocation/All
//        String json = "[ { \"locationId\": \"VMS30080112\", \"latitude\": \"51.39322\", \"longitude\": \"-0.5603392\", \"name\": \"M40/8353B M42 southBound\", \"descriptor\": \"M40/8353B M42 southBound\", \"tpegDirection\": \"allDirections\" }, { \"locationId\": \"VMS30080113\", \"latitude\": \"51.39323\", \"longitude\": \"-0.5603393\", \"name\": \"M40/8353B M43 southBound\", \"descriptor\": \"M40/8353B M43 southBound\", \"tpegDirection\": \"allDirections\" }, { \"locationId\": \"VMS30080114\", \"latitude\": \"51.39324\", \"longitude\": \"-0.5603394\", \"name\": \"M40/8353B M44 southBound\", \"descriptor\": \"M40/8353B M44 southBound\", \"tpegDirection\": \"allDirections\" }, { \"locationId\": \"TRBUCK-1210711742\", \"fromLatitude\": \"61.39322\", \"fromLongitude\": \"-0.5703394\", \"toLatitude\": \"71.39322\", \"toLongitude\": \"-1.39322\", \"fromDescriptor\": \"from desc\", \"toDescriptor\": \"to desc\", \"tpegDirection\": \"direction1\" } ]";
        PredefinedLocation[][] predefinedLocations = new PredefinedLocation[3][];
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.BASE_URL, "BCCFeedImportPredefinedSectionLocation/All",
                CseDetails.USER_NAME, CseDetails.PASSWORD);
        predefinedLocations[0] = gson.fromJson(contentInstance.getContent(),
                PredefinedLocation[].class);
//        contentInstance = Container.retrieveLatest(CseDetails.aeId,
//                CseDetails.BASE_URL, "BCCFeedImportPredefinedTrLocation/All",
//                CseDetails.USER_NAME, CseDetails.PASSWORD);
//        predefinedLocations[1] = gson.fromJson(contentInstance.getContent(),
//                PredefinedLocation[].class);
         contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.BASE_URL, "BCCFeedImportPredefinedVmsLocation/All",
                CseDetails.USER_NAME, CseDetails.PASSWORD);
        predefinedLocations[1] = gson.fromJson(contentInstance.getContent(),
                PredefinedLocation[].class);
        contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.BASE_URL, "BCCFeedImportPredefinedLinkLocation/All",
                CseDetails.USER_NAME, CseDetails.PASSWORD);
        predefinedLocations[2] = gson.fromJson(contentInstance.getContent(),
                PredefinedLocation[].class);
        for (int i = 0; i < predefinedLocations.length; i++) {
            for (int j = 0; j < predefinedLocations[i].length; j++) {
                predefinedLocationMap.put(predefinedLocations[i][j].locationId, predefinedLocations[i][j]);
            }
        }
    }

    private void loadCaVms() throws Exception {
        // URL BCCSignSettingFeedImport/All
//        String json = " [ { \"locationReference\": \"VMSBUCK-3\", \"numberOfCharacters\": \"16\", \"numberOfRows\": \"4\", \"vmsLegends\": [\"Major Works 1\",\"Bierton 1 Rd\",\"by prison 1\",\"7-25 Mar\"], \"vmsType\": \"other 1\" }, { \"locationReference\": \"VMSBUCK-4\", \"numberOfCharacters\": \"17\", \"numberOfRows\": \"5\", \"vmsLegends\": [\"Major Works 2\",\"Bierton 2 Rd\",\"by prison 2\",\"8-26 Mar\"], \"vmsType\": \"other 2\" }, { \"locationReference\": \"VMSBUCK-5\", \"numberOfCharacters\": \"18\", \"numberOfRows\": \"6\", \"vmsLegends\": [\"Major Works 3\",\"Bierton 3 Rd\",\"by prison 3\",\"9-27 Mar\"], \"vmsType\": \"other 3\" } ]";
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.BASE_URL, "BCCSignSettingFeedImport/All",
                CseDetails.USER_NAME, CseDetails.PASSWORD);
        CaVmsItem[] caVmsItems = gson.fromJson(contentInstance.getContent(), CaVmsItem[].class);
        for (CaVmsItem caVmsItem : caVmsItems) {
            caVmsItem.updateLocation(predefinedLocationMap);
            if (caVmsItem.shouldAdd()) {
                itemList.add(caVmsItem);
            }
        }
    }

    private void loadCaCarParks() throws Exception {
        // URL BCCCarPark2FeedImport/All
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.BASE_URL, "BCCCarPark2FeedImport/All",
                CseDetails.USER_NAME, CseDetails.PASSWORD);
        String content = contentInstance.getContent();
        CaCarParkItem[] caCarParkItems = gson.fromJson(content, CaCarParkItem[].class);
        for (CaCarParkItem caCarParkItem : caCarParkItems) {
            if (caCarParkItem.shouldAdd()) {
                itemList.add(caCarParkItem);
            }
        }
    }

    private void loadCaTrafficFlow() throws Exception {
        // URL BCCTrafficFlowFeedImport/All
        ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                CseDetails.BASE_URL, "BCCTrafficFlowFeedImport/All",
                CseDetails.USER_NAME, CseDetails.PASSWORD);
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

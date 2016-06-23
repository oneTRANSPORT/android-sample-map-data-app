package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.json.items.Item;
import com.interdigital.android.samplemapdataapp.layer.BaseLayer;

import java.util.ArrayList;

public class LoadMarkerTask extends AsyncTask<Void, Integer, Void> {

    private GoogleMap googleMap;
    private ArrayList<Item> itemList = new ArrayList<>();
    private ProgressBar progressBar;
    private boolean moveMap;
    // TODO    Weak reference.
    private MapsActivity mapsActivity;
    private Context context;
    private BaseLayer[] layers;

    public LoadMarkerTask(GoogleMap googleMap, ProgressBar progressBar,
                          boolean moveMap, MapsActivity mapsActivity, BaseLayer[] layers) {
        this.googleMap = googleMap;
        this.progressBar = progressBar;
        this.moveMap = moveMap;
        this.mapsActivity = mapsActivity;
        this.layers = layers;
        BaseLayer.initialiseClusterItems(layers);
        context = mapsActivity.getApplicationContext();
        Log.i("LoadMarkerTask", "Invoking load markers");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
//            addWorldSensing();
//            loadCaVms();
            layers[0].loadClusterItems();
//            loadCaCarParks();
//            loadCaTrafficFlow();
//            loadCaRoadWorks();
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
        // Has to be on UI thread?
        layers[0].showNewClusterItems();
//        vmsClusterManager.addItems(vmsClusterItems);
//        vmsClusterManager.setRenderer(vmsClusterRenderer);
//        roadWorksClusterManager.addItems(roadWorksClusterItems);
//        roadWorksClusterManager.setRenderer(roadWorksClusterRenderer);
//        carParkClusterManager.addItems(carParkClusterItems);
//        carParkClusterManager.setRenderer(carParkClusterRenderer);
//        trafficFlowClusterManager.addItems(trafficFlowClusterItems);
//        trafficFlowClusterManager.setRenderer(trafficFlowClusterRenderer);
        // Move to about the middle of Aylesbury so we can see Worldsensing, ANPR and car park items.
        // Zoom out for VMS.
        if (moveMap) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(51.8128587, -0.8239542), 13));
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void addWorldSensing() {
        for (int i = 0; i < 6; i++) {
            itemList.add(new WorldsensingItem(i, mapsActivity));
        }
    }

//    private void loadCaVms() throws Exception {
//        Cursor cursor = BucksContentHelper.getVariableMessageSigns(context);
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                VmsClusterItem vmsClusterItem = new VmsClusterItem(cursor);
//                if (vmsClusterItem.shouldAdd()) {
//                    vmsClusterItems.add(vmsClusterItem);
//                }
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//    }

//    private void loadCaCarParks() throws Exception {
//        Cursor cursor = BucksContentHelper.getCarParks(context);
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                CarParkClusterItem carParkClusterItem = new CarParkClusterItem(cursor);
//                carParkClusterItems.add(carParkClusterItem);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//    }

//    private void loadCaTrafficFlow() throws Exception {
//        Cursor cursor = BucksContentHelper.getTrafficFlows(context);
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                TrafficFlowClusterItem trafficFlowClusterItem = new TrafficFlowClusterItem(cursor);
//                if (trafficFlowClusterItem.shouldAdd()) {
//                    trafficFlowClusterItems.add(trafficFlowClusterItem);
//                }
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//    }

//    private void loadCaRoadWorks() throws Exception {
//        Cursor cursor = BucksContentHelper.getRoadWorks(context);
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                RoadWorksClusterItem roadWorksClusterItem = new RoadWorksClusterItem(cursor);
//                roadWorksClusterItems.add(roadWorksClusterItem);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//    }
}

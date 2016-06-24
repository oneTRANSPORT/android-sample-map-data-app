package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.layer.BaseLayer;

public class LoadMarkerTask extends AsyncTask<Void, Integer, Void> {

    private GoogleMap googleMap;
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
            for (BaseLayer layer : layers) {
                layer.loadClusterItems();
            }
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
        for (BaseLayer layer : layers) {
            layer.showNewClusterItems();
        }
        // Move to about the middle of Aylesbury so we can see Worldsensing, ANPR and car park items.
        // Zoom out for VMS.
        if (moveMap) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(51.8128587, -0.8239542), 13));
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

}

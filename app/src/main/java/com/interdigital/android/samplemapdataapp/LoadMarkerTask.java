package com.interdigital.android.samplemapdataapp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.layer.BaseLayer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

public class LoadMarkerTask extends AsyncTask<Void, Integer, Void> {

    private GoogleMap googleMap;
    private ProgressBar progressBar;
    private boolean moveMap;
    private BaseLayer[] baseLayers;

    public LoadMarkerTask(GoogleMap googleMap, ProgressBar progressBar, boolean moveMap,
                          BaseLayer[] baseLayers) {
        this.googleMap = googleMap;
        this.progressBar = progressBar;
        this.moveMap = moveMap;
        this.baseLayers = baseLayers;
        ClusterBaseLayer.initialiseClusterItems(baseLayers);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            for (BaseLayer layer : baseLayers) {
                layer.loadClusterItems();
                layer.loadPolylines();
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
        for (BaseLayer layer : baseLayers) {
            layer.addToMap();
        }
        // Move to about the middle of Aylesbury so we can see Fastprk, ANPR and car park items.
        // Zoom out for VMS.
        if (moveMap) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(51.8128587, -0.8239542), 13));
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}

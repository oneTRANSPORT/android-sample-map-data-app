package com.interdigital.android.samplemapdataapp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.layer.BaseLayer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import java.util.ArrayList;

public class LoadMarkerTask extends AsyncTask<Void, Integer, Void> {

    private GoogleMap googleMap;
    private ProgressBar progressBar;
    private boolean moveMap;
    private ArrayList<ClusterBaseLayer> layers = new ArrayList<>();

    public LoadMarkerTask(GoogleMap googleMap, ProgressBar progressBar, boolean moveMap,
                          BaseLayer[] baseLayers) {
        this.googleMap = googleMap;
        this.progressBar = progressBar;
        this.moveMap = moveMap;
        extractClusterLayers(baseLayers);
        ClusterBaseLayer.initialiseClusterItems(layers);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            for (ClusterBaseLayer layer : layers) {
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
        for (ClusterBaseLayer layer : layers) {
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

    private void extractClusterLayers(BaseLayer[] baseLayers) {
        for (BaseLayer baseLayer : baseLayers) {
            if (baseLayer instanceof ClusterBaseLayer) {
                layers.add((ClusterBaseLayer) baseLayer);
            }
        }
    }
}

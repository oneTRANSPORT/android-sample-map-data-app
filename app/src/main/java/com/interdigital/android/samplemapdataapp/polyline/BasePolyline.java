/* Copyright 2016 InterDigital Communications, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.interdigital.android.samplemapdataapp.polyline;

import android.location.Location;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.interdigital.android.samplemapdataapp.R;

import java.util.ArrayList;

public abstract class BasePolyline {

    private PolylineOptions polylineOptions = new PolylineOptions();
    private Polyline polyline;
    private MarkerOptions centreMarkerOptions = new MarkerOptions();
    private Marker centreMarker;
    private int markerResource = R.drawable.centre_node_grey_icon;

    public void addPoints(ArrayList<LatLng> latLngs) {
        for (int i = 0; i < latLngs.size(); i++) {
            polylineOptions.add(latLngs.get(i));
        }
        updateCentreMarker(latLngs);
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }

    public void setPolylineOptions(PolylineOptions polylineOptions) {
        this.polylineOptions = polylineOptions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public MarkerOptions getCentreMarkerOptions() {
        return centreMarkerOptions;
    }

    public void setCentreMarkerOptions(MarkerOptions centreMarkerOptions) {
        this.centreMarkerOptions = centreMarkerOptions;
    }

    public Marker getCentreMarker() {
        return centreMarker;
    }

    public void setCentreMarker(Marker centreMarker) {
        this.centreMarker = centreMarker;
    }

    public int getMarkerResource() {
        return markerResource;
    }

    public void setMarkerResource(int markerResource) {
        this.markerResource = markerResource;
    }

    public abstract View getInfoWindow();

    public abstract View getInfoContents();

    private void updateCentreMarker(ArrayList<LatLng> latLngs) {
        float length = 0;
        float[] results = new float[1];
        for (int i = 0; i < latLngs.size() - 1; i++) {
            LatLng start = latLngs.get(i);
            LatLng end = latLngs.get(i + 1);
            Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, results);
            length += results[0];
        }
        int i = 0;
        float distance = 0;
        while (distance < length / 2 && i < latLngs.size()) {
            LatLng start = latLngs.get(i);
            LatLng end = latLngs.get(i + 1);
            Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, results);
            distance += results[0];
            i++;
        }
        centreMarkerOptions
                .position(latLngs.get(i))
                .anchor(0.5f, 0.5f)
                .infoWindowAnchor(0.5f, 0.4f)
                .title("Centre marker")
                .icon(BitmapDescriptorFactory.fromResource(markerResource));
    }
}

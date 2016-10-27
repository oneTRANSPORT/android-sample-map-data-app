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
package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.samplemapdataapp.marker.BaseMarker;

import java.util.ArrayList;

public abstract class MarkerBaseLayer extends BaseLayer {

    private ArrayList<BaseMarker> baseMarkers = new ArrayList<>();

    public MarkerBaseLayer(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void initialise() {
        for (int i = 0; i < baseMarkers.size(); i++) {
            BaseMarker baseMarker = baseMarkers.get(i);
            if (baseMarker.getMarker() != null) {
                baseMarker.getMarker().remove();
            }
        }
        baseMarkers.clear();
    }

    public View getInfoWindow(Marker marker) {
        return null;
    }

    public View getInfoContents(Marker marker) {
        for (BaseMarker baseMarker : getBaseMarkers()) {
            if (baseMarker.getMarker().equals(marker)) {
                return baseMarker.getInfoContents();
            }
        }
        return null;
    }

    @Override
    public void addToMap() {
        for (BaseMarker baseMarker : baseMarkers) {
            baseMarker.addToMap(getGoogleMap());
        }
    }

    public void setVisible(boolean visible) {
        for (BaseMarker baseMarker : baseMarkers) {
            baseMarker.getMarker().setVisible(visible);
        }
    }

    public boolean onMarkerClick(Marker marker) {
        for (BaseMarker baseMarker : baseMarkers) {
            if (baseMarker.getMarker().equals(marker)) {
                marker.showInfoWindow();
                return true;
            }
        }
        return false;
    }

    public ArrayList<BaseMarker> getBaseMarkers() {
        return baseMarkers;
    }

    public void setBaseMarkers(ArrayList<BaseMarker> baseMarkers) {
        this.baseMarkers = baseMarkers;
    }

    public BaseMarker getBaseMarker(Marker marker) {
        for (BaseMarker baseMarker : baseMarkers) {
            if (baseMarker.getMarker().equals(marker)) {
                return baseMarker;
            }
        }
        return null;
    }
}

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.interdigital.android.samplemapdataapp.polyline.BasePolyline;

import java.util.ArrayList;

public abstract class PolylineBaseLayer extends BaseLayer {

    private ArrayList<BasePolyline> basePolylines = new ArrayList<>();

    public PolylineBaseLayer(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void initialise() {
        if (basePolylines.size() > 0) {
            for (BasePolyline basePolyline : getBasePolylines()) {
                if (basePolyline.getPolyline() != null) {
                    basePolyline.getPolyline().remove();
                    basePolyline.getCentreMarker().remove();
                }
            }
            basePolylines.clear();
        }
    }

    public void addToMap() {
        for (int i = 0; i < basePolylines.size(); i++) {
            basePolylines.get(i).setPolyline(getGoogleMap().addPolyline(
                    basePolylines.get(i).getPolylineOptions()));
            basePolylines.get(i).setCentreMarker(getGoogleMap().addMarker(
                    basePolylines.get(i).getCentreMarkerOptions()));
        }
    }

    public void setVisible(boolean visible) {
        for (BasePolyline basePolyline : basePolylines) {
            basePolyline.getPolyline().setVisible(visible);
            basePolyline.getCentreMarker().setVisible(visible);
        }
    }

    public boolean onMarkerClick(Marker marker) {
      for (BasePolyline basePolyline : basePolylines) {
            if (basePolyline.getCentreMarker().equals(marker)) {
                basePolyline.getCentreMarker().showInfoWindow();
                return true;
            }
        }
        return false;
    }

    public View getInfoWindow(Marker marker) {
        for (BasePolyline basePolyline : basePolylines) {
            if (basePolyline.getCentreMarker().equals(marker)) {
                return basePolyline.getInfoWindow();
            }
        }
        return null;
    }

    public View getInfoContents(Marker marker) {
        for (BasePolyline basePolyline : basePolylines) {
            if (basePolyline.getCentreMarker().equals(marker)) {
                return basePolyline.getInfoContents();
            }
        }
        return null;
    }

    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }

    public Intent onPolylineClick(Polyline polyline, Activity activity) {
        return null;
    }


    public ArrayList<BasePolyline> getBasePolylines() {
        return basePolylines;
    }

    public void setBasePolylines(ArrayList<BasePolyline> basePolylines) {
        this.basePolylines = basePolylines;
    }

    public BasePolyline getBasePolyline(Polyline polyline) {
        for (BasePolyline basePolyline : basePolylines) {
            if (basePolyline.equals(polyline)) {
                return basePolyline;
            }
        }
        return null;
    }
}

package com.interdigital.android.samplemapdataapp.layer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
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
                }
            }
            basePolylines.clear();
        }
    }

    public void addToMap() {
        for (int i = 0; i < basePolylines.size(); i++) {
            basePolylines.get(i).setPolyline(getGoogleMap().addPolyline(
                    basePolylines.get(i).getPolylineOptions()));
        }
    }

    public void setVisible(boolean visible) {
        for (BasePolyline basePolyline : basePolylines) {
            basePolyline.getPolyline().setVisible(visible);
        }
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

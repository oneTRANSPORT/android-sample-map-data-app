package com.interdigital.android.samplemapdataapp.items;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.samplemapdataapp.R;
import com.interdigital.android.samplemapdataapp.json.PredefinedLocation;

import java.util.HashMap;

public class CaVmsItem extends Item {

    @Expose
    @SerializedName("locationReference")
    private String locationReference;
    @Expose
    @SerializedName("numberOfCharacters")
    private String numberOfCharacters;
    @Expose
    @SerializedName("numberOfRows")
    private String numberOfRows;
    @Expose
    @SerializedName("vmsLegends")
    private String[] vmsLegends;
    @Expose
    @SerializedName("vmsType")
    private String vmsType;

    public CaVmsItem(String title) {
        super(title);
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.vm_sign_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public void updateLocation(HashMap<String, PredefinedLocation> predefinedLocationMap) {
        // TODO We don't yet have the full list of predefined locations.
        if (predefinedLocationMap.containsKey(locationReference)) {
            PredefinedLocation predefinedLocation = predefinedLocationMap.get(locationReference);
            setLatLng(new LatLng(
                    Double.valueOf(predefinedLocation.latitude),
                    Double.valueOf(predefinedLocation.longitude)));
        } else {
            setLatLng(new LatLng(Math.random() - 0.5 + 51.62821, Math.random() - 0.5 + -0.7502827));
        }
    }

    @Override
    public View getInfoContents(Context context) {
        TextView textView = new TextView(context);
        textView.setText(locationReference);
        textView.setTextColor(0xffff0000);
        return textView;
    }
}

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

public class CaCarParkItem extends Item {

    @Expose
    @SerializedName("carParkIdentity")
    private String carParkIdentity;
    @Expose
    @SerializedName("totalParkingCapacity")
    private String totalParkingCapacity;
    @Expose
    @SerializedName("almostFullIncreasing")
    private String almostFullIncreasing;
    @Expose
    @SerializedName("almostFullDecreasing")
    private String almostFullDecreasing;
    @Expose
    @SerializedName("fullDecreasing")
    private String fullDecreasing;
    @Expose
    @SerializedName("fullIncreasing")
    private String fullIncreasing;
    @Expose
    @SerializedName("entranceFull")
    private String entranceFull;
    @Expose
    @SerializedName("radius")
    private String radius;
    @Expose
    @SerializedName("latitude")
    private String latitude;
    @Expose
    @SerializedName("longitude")
    private String longitude;

    public CaCarParkItem(String title) {
        super(title);
    }

    @Override
    public void updateLocation(HashMap<String, PredefinedLocation> predefinedLocationMap) {

    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(new LatLng(
                                Double.valueOf(latitude),
                                Double.valueOf(longitude)))
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.carpark_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public View getInfoContents(Context context) {
        TextView textView = new TextView(context);
        textView.setText(carParkIdentity);
        textView.setTextColor(0xff0080ff);
        return textView;
    }
}

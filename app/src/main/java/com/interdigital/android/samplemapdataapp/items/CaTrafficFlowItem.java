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

public class CaTrafficFlowItem extends Item {

    @Expose
    @SerializedName("locationReference")
    private String locationReference;
    @Expose
    @SerializedName("vehicleFlow")
    private String vehicleFlow;
    @Expose
    @SerializedName("averageVehicleSpeed")
    private String averageVehicleSpeed;
    @Expose
    @SerializedName("travelTime")
    private String travelTime;
    @Expose
    @SerializedName("freeFlowSpeed")
    private String freeFlowSpeed;
    @Expose
    @SerializedName("freeFlowTravelTime")
    private String freeFlowTravelTime;

    public CaTrafficFlowItem(String title) {
        super(title);
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
            setLatLng(new LatLng(Math.random() * 8 - 4 + 51.62821, Math.random() * 8 - 4 + -0.7502827));
        }
    }

    public String getLocationReference() {
        return locationReference;
    }

    public void setLocationReference(String locationReference) {
        this.locationReference = locationReference;
    }

    public String getVehicleFlow() {
        return vehicleFlow;
    }

    public void setVehicleFlow(String vehicleFlow) {
        this.vehicleFlow = vehicleFlow;
    }

    public String getAverageVehicleSpeed() {
        return averageVehicleSpeed;
    }

    public void setAverageVehicleSpeed(String averageVehicleSpeed) {
        this.averageVehicleSpeed = averageVehicleSpeed;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getFreeFlowSpeed() {
        return freeFlowSpeed;
    }

    public void setFreeFlowSpeed(String freeFlowSpeed) {
        this.freeFlowSpeed = freeFlowSpeed;
    }

    public String getFreeFlowTravelTime() {
        return freeFlowTravelTime;
    }

    public void setFreeFlowTravelTime(String freeFlowTravelTime) {
        this.freeFlowTravelTime = freeFlowTravelTime;
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.anpr_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public View getInfoContents(Context context) {
        TextView textView = new TextView(context);
        textView.setText(locationReference);
        textView.setTextColor(0xff000000);
        return textView;
    }

}

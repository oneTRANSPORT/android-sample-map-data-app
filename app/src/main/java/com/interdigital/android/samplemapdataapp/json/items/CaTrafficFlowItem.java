package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

    public CaTrafficFlowItem() {
        setType(TYPE_ANPR);
    }

    @Override
    public boolean shouldAdd() {
        if (getLatLng().latitude == 0 && getLatLng().longitude == 0) {
            return false;
        }
        return !TextUtils.isEmpty(vehicleFlow.trim())
                && !TextUtils.isEmpty(averageVehicleSpeed.trim());
    }

    @Override
    public void updateLocation(HashMap<String, PredefinedLocation> predefinedLocationMap) {
        if (predefinedLocationMap.containsKey(locationReference)) {
            PredefinedLocation predefinedLocation = predefinedLocationMap.get(locationReference);
            setLatLng(new LatLng(
                    Double.valueOf(predefinedLocation.getFromLatitude()),
                    Double.valueOf(predefinedLocation.getFromLongitude())));
            if (!TextUtils.isEmpty(predefinedLocation.getDescriptor())) {
                locationReference = predefinedLocation.getDescriptor();
            } else if (!TextUtils.isEmpty(predefinedLocation.getToDescriptor())) {
                locationReference = predefinedLocation.getToDescriptor();
            } else if (!TextUtils.isEmpty(predefinedLocation.getFromDescriptor())) {
                locationReference = predefinedLocation.getFromDescriptor();
            }
        } else {
            setLatLng(new LatLng(0, 0));
        }
    }

    public String getLocationReference() {
        return locationReference;
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_flow, null, false);
        ((TextView) view.findViewById(R.id.cars_text_view))
                .setText(String.format(context.getString(R.string.cars_per_min), vehicleFlow));
        ((TextView) view.findViewById(R.id.speed_text_view))
                .setText(String.format(context.getString(R.string.kph),
                        averageVehicleSpeed.replaceFirst("\\.[0-9]*", "")));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }

}

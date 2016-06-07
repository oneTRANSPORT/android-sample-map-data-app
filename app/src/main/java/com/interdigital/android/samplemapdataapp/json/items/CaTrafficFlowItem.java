package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.county.bucks.provider.BucksContract;

import java.util.HashMap;

public class CaTrafficFlowItem extends Item {

    private int vehicleFlow;
    private double averageVehicleSpeed;
    private String locationReference;

    public CaTrafficFlowItem(Cursor cursor) {
        setType(TYPE_ANPR);
        vehicleFlow = cursor.getInt(cursor.getColumnIndex(
                BucksContract.TrafficFlowJoinLocation.COLUMN_VEHICLE_FLOW));
        averageVehicleSpeed = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.TrafficFlowJoinLocation.COLUMN_AVERAGE_VEHICLE_SPEED));
        locationReference = cursor.getString(cursor.getColumnIndex(
                BucksContract.TrafficFlowJoinLocation.COLUMN_FROM_DESCRIPTOR));
        if (TextUtils.isEmpty(locationReference)) {
            locationReference = cursor.getString(cursor.getColumnIndex(
                    BucksContract.TrafficFlowJoinLocation.COLUMN_TO_DESCRIPTOR));
        }
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.TrafficFlowJoinLocation.COLUMN_FROM_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.TrafficFlowJoinLocation.COLUMN_FROM_LONGITUDE));
        setLatLng(new LatLng(latitude, longitude));
    }

    @Override
    public boolean shouldAdd() {
        if (getLatLng().latitude == 0 && getLatLng().longitude == 0) {
            return false;
        }
        if (TextUtils.isEmpty(locationReference)) {
            return false;
        }
        return vehicleFlow != 0 || averageVehicleSpeed != 0;
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
                        Math.round(averageVehicleSpeed)));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }

}

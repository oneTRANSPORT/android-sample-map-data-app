package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.database.Cursor;
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

public class CaCarParkItem extends Item {

    private static final String BUCK_PREFIX = "BUCK-";

    private String identity;
    private int totalParkingCapacity;
    private int entranceFull;

    public CaCarParkItem(Cursor cursor) {
        setType(TYPE_CAR_PARK);
        identity = cursor.getString(cursor.getColumnIndex(
                BucksContract.CarPark.COLUMN_CAR_PARK_IDENTITY))
                .replace(BUCK_PREFIX, "").replaceAll("_", " ");
        totalParkingCapacity = cursor.getInt(cursor.getColumnIndex(
                BucksContract.CarPark.COLUMN_TOTAL_PARKING_CAPACITY));
        entranceFull = cursor.getInt(cursor.getColumnIndex(
                BucksContract.CarPark.COLUMN_ENTRANCE_FULL));
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.CarPark.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.CarPark.COLUMN_LONGITUDE));
        setLatLng(new LatLng(latitude, longitude));
    }

    @Override
    public boolean shouldAdd() {
        return true;
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.carpark_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public View getInfoContents(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_car_park, null, false);
        TextView sizeTextView = (TextView) view.findViewById(R.id.size_text_view);
        TextView signTextView = (TextView) view.findViewById(R.id.sign_text_view);
        TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
        sizeTextView.setText(String.valueOf(totalParkingCapacity));
        if (entranceFull == 0) {
            signTextView.setText(R.string.space);
        } else {
            signTextView.setText(R.string.full);
        }
        nameTextView.setText(identity);
        return view;
    }
}

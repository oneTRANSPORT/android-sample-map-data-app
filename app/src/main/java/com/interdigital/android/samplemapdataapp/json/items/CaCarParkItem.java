package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView sizeTextView = new TextView(context);
        sizeTextView.setTextColor(0xff808080);
        sizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        sizeTextView.setGravity(GravityCompat.START);
        sizeTextView.setText(String.valueOf(totalParkingCapacity));
        linearLayout.addView(sizeTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView signTextView = new TextView(context);
        signTextView.setTextColor(0xff000000);
        signTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        signTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        signTextView.setTypeface(Typeface.DEFAULT_BOLD);
        if (entranceFull == 0) {
            signTextView.setText(R.string.space);
        } else {
            signTextView.setText(R.string.full);
        }
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        // TODO Decide what we want in the feed.
        nameTextView.setText(identity);
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(GravityCompat.END);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }
}

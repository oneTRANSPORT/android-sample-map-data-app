package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
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

import net.uk.onetransport.android.county.bucks.carparks.CarPark;

import java.util.HashMap;

public class CaCarParkItem extends Item {

    private static final String ZERO = "0";
    private static final String BUCK_PREFIX = "BUCK-";

    private CarPark carPark;

    public CaCarParkItem(CarPark carPark) {
        setType(TYPE_CAR_PARK);
        this.carPark = carPark;
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
                        .position(new LatLng(
                                carPark.getLatitude(),
                                carPark.getLongitude()))
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
        sizeTextView.setText(carPark.getTotalParkingCapacity());
        linearLayout.addView(sizeTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView signTextView = new TextView(context);
        signTextView.setTextColor(0xff000000);
        signTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        signTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        signTextView.setTypeface(Typeface.DEFAULT_BOLD);
        if (carPark.getEntranceFull().equals(ZERO)) {
            signTextView.setText(R.string.space);
        } else {
            signTextView.setText(R.string.full);
        }
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        // TODO Decide what we want in the feed.
        nameTextView.setText(carPark.getCarParkIdentity().replace(BUCK_PREFIX, "").replaceAll("_", " "));
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(GravityCompat.END);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }
}

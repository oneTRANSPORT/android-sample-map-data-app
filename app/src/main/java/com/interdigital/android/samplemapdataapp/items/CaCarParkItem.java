package com.interdigital.android.samplemapdataapp.items;

import android.content.Context;
import android.graphics.Typeface;
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
    public boolean shouldAdd() {
        return true;
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
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView sizeTextView = new TextView(context);
        sizeTextView.setTextColor(0xff808080);
        sizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        sizeTextView.setGravity(Gravity.LEFT);
        sizeTextView.setText(totalParkingCapacity);
        linearLayout.addView(sizeTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView signTextView = new TextView(context);
        signTextView.setTextColor(0xff000000);
        signTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        signTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        signTextView.setTypeface(Typeface.DEFAULT_BOLD);
        if (entranceFull.equals("0")) {
            signTextView.setText("SPACE");
        } else {
            signTextView.setText("FULL");
        }
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        nameTextView.setText(carParkIdentity.replace("BUCK-", "").replaceAll("_", " "));
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(Gravity.RIGHT);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }
}

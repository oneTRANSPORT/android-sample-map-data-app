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

public class CaVmsItem extends Item {

    private String[] vmsLegends;
    private String locationReference;

    public CaVmsItem(Cursor cursor) {
        setType(TYPE_VMS);
        String legendStr = cursor.getString(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_VMS_LEGENDS));
        vmsLegends = legendStr.split("\\|");
        locationReference = cursor.getString(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_DESCRIPTOR));
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.VariableMessageSign.COLUMN_LONGITUDE));
        setLatLng(new LatLng(latitude, longitude));
    }

    @Override
    public boolean shouldAdd() {
        if (getLatLng() == null || (getLatLng().latitude == 0 && getLatLng().longitude == 0)) {
            return false;
        }
        if (vmsLegends == null || vmsLegends.length == 0) {
            return false;
        }
        for (String line : vmsLegends) {
            if (line.trim().length() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.8f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.vm_sign_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public View getInfoContents(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView signTextView = new TextView(context);
        signTextView.setTextColor(0xff000000);
        signTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        signTextView.setTypeface(Typeface.DEFAULT_BOLD);
        StringBuilder buf = new StringBuilder();
        for (String line : vmsLegends) {
            buf.append(line.trim());
            if (line.trim().length() > 0) {
                buf.append("\n");
            }
        }
        signTextView.setText(buf.toString().trim());
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        nameTextView.setText(locationReference);
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(GravityCompat.END);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }
}

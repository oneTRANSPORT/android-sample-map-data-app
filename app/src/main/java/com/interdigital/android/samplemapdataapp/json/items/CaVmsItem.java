package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
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

import net.uk.onetransport.android.county.bucks.locations.PredefinedVmsLocation;
import net.uk.onetransport.android.county.bucks.variablemessagesigns.VariableMessageSign;

import java.util.HashMap;

public class CaVmsItem extends Item {

    private VariableMessageSign variableMessageSign;

    public CaVmsItem(VariableMessageSign variableMessageSign,
                     HashMap<String, PredefinedVmsLocation> vmsLocationMap) {
        setType(TYPE_VMS);
        this.variableMessageSign = variableMessageSign;
        updateLocation(vmsLocationMap);
    }

    @Override
    public boolean shouldAdd() {
        if (variableMessageSign.getVmsLegends() == null
                || variableMessageSign.getVmsLegends().length == 0) {
            return false;
        }
        if (getLatLng() == null || (getLatLng().latitude == 0 && getLatLng().longitude == 0)) {
            return false;
        }
        for (String line : variableMessageSign.getVmsLegends()) {
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

    public void updateLocation(HashMap<String, PredefinedVmsLocation> vmsLocationMap) {
        String locationReference = variableMessageSign.getLocationReference();
        if (vmsLocationMap.containsKey(locationReference)) {
            PredefinedVmsLocation predefinedVmsLocation = vmsLocationMap.get(locationReference);
            setLatLng(new LatLng(
                    predefinedVmsLocation.getLatitude(),
                    predefinedVmsLocation.getLongitude()));
            if (!TextUtils.isEmpty(predefinedVmsLocation.getDescriptor())) {
                predefinedVmsLocation.setLocationId(predefinedVmsLocation.getDescriptor());
            }
        } else {
            setLatLng(new LatLng(0, 0));
        }
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
        for (String line : variableMessageSign.getVmsLegends()) {
            buf.append(line.trim());
            if (line.trim().length() > 0) {
                buf.append("\n");
            }
        }
        signTextView.setText(buf.toString().trim());
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        nameTextView.setText(variableMessageSign.getLocationReference());
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(GravityCompat.END);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }
}

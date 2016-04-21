package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.samplemapdataapp.json.PredefinedLocation;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import org.json.JSONObject;

import java.util.HashMap;

public class WorldsensingItem extends Item implements DougalCallback {

    private static final String TAG = "WorldsensingItem";
    private static final String APP_NAME = "Worldsensing";
    private static final String[] CONTAINERS_STATIC = {
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/555b11dbcb9b3277b782e708",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/555b15b0cb9b3277b782ec0d",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/555b12b4cb9b3277b782e821"
    };
    private static final String[] CONTAINERS_UPDATING = {
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/555b11dbcb9b3277b782e708",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/555b15b0cb9b3277b782ec0d",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/555b12b4cb9b3277b782e821"
    };
    private int offset;
    private GoogleMap googleMap;
    private HashMap<Marker, Item> markerMap;
    private LatLng latLng;
    private boolean full = false;
    private boolean updating = false;

    public WorldsensingItem(int offset) {
        super("Worldsensing " + String.valueOf(offset));
        this.offset = offset;
        loadPosition();
    }

    @Override
    public boolean shouldAdd() {
        return true;
    }

    @Override
    public void updateLocation(HashMap<String, PredefinedLocation> predefinedLocationMap) {
        // WS does not need this.  TODO Refactor?
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        this.googleMap = googleMap;
        this.markerMap = markerMap;
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(latLng)
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.worldsensing_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public void getResponse(Resource resource, Throwable throwable) {
        if (resource == null) {
            Log.e(TAG, "Worldsensing content instance not retrieved.");
            full = false;
            if (Math.random() > 0.7) {
                full = true;
            }
        } else {
            try {
                String jsonContent = ((ContentInstance) resource).getContent();
                JSONObject jsonObject = new JSONObject(jsonContent);
                full = jsonObject.optBoolean("occupied");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (full) {
            getMarker().setIcon(BitmapDescriptorFactory.fromResource(R.drawable.worldsensing_full));
        } else {
            getMarker().setIcon(BitmapDescriptorFactory.fromResource(R.drawable.worldsensing_updated));
        }
        updating = false;
    }

    @Override
    public View getInfoContents(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView signTextView = new TextView(context);
        signTextView.setTextColor(0xff000000);
        signTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        signTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        signTextView.setTypeface(Typeface.DEFAULT_BOLD);
        if (!full) {
            signTextView.setText("SPACE");
        } else {
            signTextView.setText("FULL");
        }
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        nameTextView.setText("Worldsensing");
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(Gravity.RIGHT);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }

    public void update() {
        if (!updating) {
            updating = true;
            getMarker().setIcon(BitmapDescriptorFactory.fromResource(R.drawable.worldsensing_updating));
            Container.retrieveLatestAsync(CseDetails.aeId, CseDetails.BASE_URL,
                    APP_NAME + CONTAINERS_UPDATING[offset],
                    CseDetails.USER_NAME, CseDetails.PASSWORD, this);
        }
    }

    private void loadPosition() {
        try {
            // TODO Waiting for David to put these on CSE-02.
            ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                    CseDetails.METHOD + CseDetails.HOST,
                    CseDetails.CSE_NAME + "/" + APP_NAME + CONTAINERS_STATIC[offset],
                    CseDetails.USER_NAME, CseDetails.PASSWORD);
            String jsonContent = contentInstance.getContent();
            JSONObject jsonObject = new JSONObject(jsonContent);
            if (jsonObject.optJSONObject("position") != null) {
                double lat = jsonObject.getJSONObject("position").getDouble("lat");
                double lon = jsonObject.getJSONObject("position").getDouble("lon");
                latLng = new LatLng(lat, lon);
            }
        } catch (Exception e) {
            // Generate a nearby location if no response from the CSE.
            switch (offset) {
                case 0:
                    latLng = new LatLng(51.807744, -0.811782);
                    break;
                case 1:
                    latLng = new LatLng(51.811396, -0.814565);
                    break;
                case 2:
                    latLng = new LatLng(51.814096, -0.802537);
                    break;
            }
        }
    }

}

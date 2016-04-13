package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class WorldsensingItem extends CarParkItem implements DougalCallback {

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

    public WorldsensingItem(int offset) {
        super("Worldsensing " + String.valueOf(offset), 0, 0);
        this.offset = offset;
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        this.googleMap = googleMap;
        this.markerMap = markerMap;
        Container.retrieveLatestAsync(CseDetails.aeId, CseDetails.METHOD + CseDetails.HOST,
                CseDetails.CSE_NAME + "/" + APP_NAME + CONTAINERS_STATIC[offset],
                CseDetails.USER_NAME, CseDetails.PASSWORD, this);
    }


    @Override
    public void getResponse(Resource resource, Throwable throwable) {
        if (resource == null) {
            Log.e(TAG, "Worldsensing container not retrieved.");
            return;
        }
        String jsonContent = ((ContentInstance) resource).getContent();
        try {
            JSONObject jsonObject = new JSONObject(jsonContent);
            if (jsonObject.optJSONObject("position") != null) {
                double lat = jsonObject.getJSONObject("position").getDouble("lat");
                double lon = jsonObject.getJSONObject("position").getDouble("lon");
                setLatLng(new LatLng(lat, lon));
                super.addMarker(googleMap, markerMap);
            } else {
                setFull(jsonObject.optBoolean("occupied"));
                super.setMarkerIcon(MarkerData.MARKER_TYPE_UPDATED);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            super.setMarkerIcon(MarkerData.MARKER_TYPE_PLAIN);
        }
    }

    @Override
    public int getPlainResource() {
        return R.drawable.worldsensing_icon;
    }

    @Override
    public int getUpdatingResource() {
        return R.drawable.worldsensing_updating;
    }

    @Override
    public int getUpdatedResource() {
        return R.drawable.worldsensing_updated;
    }

    @Override
    public int getAlternateResource() {
        return R.drawable.worldsensing_full;
    }

    @Override
    public View getInfoContents(Context context) {
        TextView textView = new TextView(context);
        textView.setText(getTitle());
        textView.setTextColor(0xff008000);
        return textView;
    }

    @Override
    public void update() {
        setMarkerIcon(MarkerData.MARKER_TYPE_UPDATING);
        Container.retrieveLatestAsync(CseDetails.aeId, CseDetails.METHOD + CseDetails.HOST,
                CseDetails.CSE_NAME + "/" + APP_NAME + CONTAINERS_UPDATING[offset],
                CseDetails.USER_NAME, CseDetails.PASSWORD, this);
    }
}

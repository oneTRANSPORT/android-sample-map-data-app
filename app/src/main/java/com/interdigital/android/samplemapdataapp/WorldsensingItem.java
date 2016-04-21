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
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.samplemapdataapp.items.Item;
import com.interdigital.android.samplemapdataapp.json.PredefinedLocation;

import org.json.JSONException;
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

    public void setMarkerIcon(@MarkerData.MarkerType int markerType) {
        int resource;
        switch (markerType) {
            case MarkerData.MARKER_TYPE_UPDATING:
                resource = R.drawable.worldsensing_updating;
                break;
            case MarkerData.MARKER_TYPE_UPDATED:
                if (full) {
                    resource = R.drawable.worldsensing_full;
                } else {
                    resource = R.drawable.worldsensing_updated;
                }
                break;
            case MarkerData.MARKER_TYPE_PLAIN:
            default:
                resource = R.drawable.worldsensing_icon;
                break;
        }
        getMarker().setIcon(BitmapDescriptorFactory.fromResource(resource));
    }

    @Override
    public void getResponse(Resource resource, Throwable throwable) {
        if (resource == null) {
            Log.e(TAG, "Worldsensing content instance not retrieved.");
            return;
        }
        String jsonContent = ((ContentInstance) resource).getContent();
        try {
            JSONObject jsonObject = new JSONObject(jsonContent);
//            setFull(jsonObject.optBoolean("occupied"));
//            super.setMarkerIcon(MarkerData.MARKER_TYPE_UPDATED);
        } catch (JSONException e) {
            e.printStackTrace();
//            super.setMarkerIcon(MarkerData.MARKER_TYPE_PLAIN);
        }
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
        if (full) {
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
//       TODO Put back in when CIs exist on server.
//        setMarkerIcon(MarkerData.MARKER_TYPE_UPDATING);
//        Container.retrieveLatestAsync(CseDetails.aeId, CseDetails.METHOD + CseDetails.HOST,
//                CseDetails.CSE_NAME + "/" + APP_NAME + CONTAINERS_UPDATING[offset],
//                CseDetails.USER_NAME, CseDetails.PASSWORD, this);
    }

    private void loadPosition() {
//        try {
        // TODO These are on CSE-01 at the moment.
//            ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
//                    CseDetails.METHOD + CseDetails.HOST,
//                    CseDetails.CSE_NAME + "/" + APP_NAME + CONTAINERS_STATIC[offset],
//                    CseDetails.USER_NAME, CseDetails.PASSWORD);
//            String jsonContent = contentInstance.getContent();
//            JSONObject jsonObject = new JSONObject(jsonContent);
//            if (jsonObject.optJSONObject("position") != null) {
//                double lat = jsonObject.getJSONObject("position").getDouble("lat");
//                double lon = jsonObject.getJSONObject("position").getDouble("lon");
//                latLng = new LatLng(lat, lon);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // TODO Remove this.
        latLng = new LatLng(Math.random() - 0.5 + 51.62821, Math.random() - 0.5 + -0.7502827);
    }
}

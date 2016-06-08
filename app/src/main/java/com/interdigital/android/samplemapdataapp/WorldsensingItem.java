package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
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
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class WorldsensingItem extends Item implements DougalCallback {

    private static final String TAG = "WorldsensingItem";
    private static final String APP_NAME = "Worldsensing";
    private static final String[] CONTAINERS_STATIC = {
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/s555b11dbcb9b3277b782e708",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/s555b15b0cb9b3277b782ec0d",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/s555b12b4cb9b3277b782e821",
            "/FastPrk/v1.0/InterdigitalDemo/InterDigital/Sensor/s572148d51170d00d9c6fa697",
            "/FastPrk/v1.0/InterdigitalDemo/InterDigital/Sensor/s572148d71170e2d79da0df12",
            "/FastPrk/v1.0/InterdigitalDemo/InterDigital/Sensor/s572212491170e2d79da0fc9f"
    };
    private static final String[] CONTAINERS_UPDATING = {
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/s555b11dbcb9b3277b782e708",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/s555b15b0cb9b3277b782ec0d",
            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/s555b12b4cb9b3277b782e821",
            "/FastPrk/v1.0/InterdigitalDemo/InterDigital/SensorOccupation/s572148d51170d00d9c6fa697",
            "/FastPrk/v1.0/InterdigitalDemo/InterDigital/SensorOccupation/s572148d71170e2d79da0df12",
            "/FastPrk/v1.0/InterdigitalDemo/InterDigital/SensorOccupation/s572212491170e2d79da0fc9f"
    };
    private int offset;
    private LatLng latLng;
    private boolean full = false;
    private boolean updating = false;
    private WeakReference<MapsActivity> weakMapsActivity;

    public WorldsensingItem(int offset, MapsActivity mapsActivity) {
        super("Worldsensing " + String.valueOf(offset));
        this.offset = offset;
        weakMapsActivity = new WeakReference<>(mapsActivity);
        setType(TYPE_CAR_PARK);
        loadPosition();
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
                        .position(latLng)
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(getMarkerIcon()))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public void getResponse(Resource resource, Throwable throwable) {
        if (resource == null) {
            Log.e(TAG, "Worldsensing content instance not retrieved.");
            full = Math.random() > 0.7;
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
            getMarker().setIcon(BitmapDescriptorFactory.fromResource(getMarkerIconFull()));
        } else {
            getMarker().setIcon(BitmapDescriptorFactory.fromResource(getMarkerIconUpdated()));
        }
        updating = false;
        MapsActivity mapsActivity = weakMapsActivity.get();
        if (mapsActivity != null) {
            mapsActivity.updateCompleted();
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
        if (!full) {
            signTextView.setText(R.string.space);
        } else {
            signTextView.setText(R.string.full);
        }
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        nameTextView.setText(R.string.worldsensing);
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(GravityCompat.END);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }

    public void update() {
        if (!updating) {
            updating = true;
            getMarker().setIcon(BitmapDescriptorFactory.fromResource(getMarkerIconUpdating()));
            Container.retrieveLatestAsync(CseDetails.aeId, CseDetails.baseUrl,
                    APP_NAME + CONTAINERS_UPDATING[offset],
                    CseDetails.userName, CseDetails.password, this);
        }
    }

    private void loadPosition() {
        try {
            ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                    CseDetails.METHOD + CseDetails.hostName,
                    CseDetails.cseName + "/" + APP_NAME + CONTAINERS_STATIC[offset],
                    CseDetails.userName, CseDetails.password);
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
                // UK demo.
                case 0:
                    latLng = new LatLng(51.807744, -0.811782);
                    break;
                case 1:
                    latLng = new LatLng(51.811396, -0.814565);
                    break;
                case 2:
                    latLng = new LatLng(51.814096, -0.802537);
                    break;
                // US demo.
                case 3:
                    latLng = new LatLng(51.807744, -0.841782);
                    break;
                case 4:
                    latLng = new LatLng(51.811396, -0.844565);
                    break;
                case 5:
                    latLng = new LatLng(51.814096, -0.832537);
                    break;
            }
        }
    }

    private int getMarkerIcon() {
        if (offset < 3) {
            return R.drawable.worldsensing_icon;
        }
        return R.drawable.worldsensing_icon_us;
    }

    private int getMarkerIconUpdating() {
        if (offset < 3) {
            return R.drawable.worldsensing_updating;
        }
        return R.drawable.worldsensing_updating_us;
    }

    private int getMarkerIconUpdated() {
        if (offset < 3) {
            return R.drawable.worldsensing_updated;
        }
        return R.drawable.worldsensing_updated_us;
    }

    private int getMarkerIconFull() {
        if (offset < 3) {
            return R.drawable.worldsensing_full;
        }
        return R.drawable.worldsensing_full_us;
    }

}

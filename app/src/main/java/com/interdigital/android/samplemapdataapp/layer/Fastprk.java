/* Copyright 2016 InterDigital Communications, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.samplemapdataapp.CseDetails;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.FastprkClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.FastprkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.FastprkClusterRenderer;

import org.json.JSONObject;

public class Fastprk extends ClusterBaseLayer<FastprkClusterItem>
        implements Handler.Callback, DougalCallback {

    private static final String APP_NAME = "Worldsensing";
    private static final int MSG_SET_PLEASE_UPDATE = 1;
    private static final String[] SENSOR_IDS = {
            "572148d51170d00d9c6fa697",
            "572148d71170e2d79da0df12",
            "572212491170e2d79da0fc9f"};
    private static final String LOCATION_PREFIX = "/FastPrk/v1.0/InterdigitalDemo/InterDigital/Sensor/s";
    // TODO   These are currently not available.
    //            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/s555b11dbcb9b3277b782e708",
//            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/s555b15b0cb9b3277b782ec0d",
//            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/s555b12b4cb9b3277b782e821",
    private static final String OCCUPATION_PREFIX =
            "/FastPrk/v1.0/InterdigitalDemo/InterDigital/SensorOccupation/s";
    // TODO   These are currently not available.
//            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/s555b11dbcb9b3277b782e708",
//            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/s555b15b0cb9b3277b782ec0d",
//            "/FastPrk/v1.0/Owner1/Fastprk-Demo-London/SensorOccupation/s555b12b4cb9b3277b782e821",

    private Handler handler = new Handler(this);
    private boolean addedToMap = false;

    public Fastprk(Context context, GoogleMap googleMap) {
        super(context, googleMap);
        startUpdateTimer();
    }

    @Override
    public void initialise() {
        // We don't want to re-initialise the Fastprk sensors as they might be mid-download.
        if (!addedToMap) {
            super.initialise();
        }
    }

    @Override
    public void load() throws Exception {
        if (!addedToMap) {
            for (int i = 0; i < SENSOR_IDS.length; i++) {
                getClusterItems().add(new FastprkClusterItem(loadPosition(i), SENSOR_IDS[i]));
            }
        }
    }

    @Override
    public void addToMap() {
        if (!addedToMap) {
            addedToMap = true;
            super.addToMap();
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_SET_PLEASE_UPDATE:
                updateAll();
                break;
        }
        return false;
    }

    public void startUpdateTimer() {
        if (!handler.hasMessages(MSG_SET_PLEASE_UPDATE)) {
            handler.sendEmptyMessageDelayed(MSG_SET_PLEASE_UPDATE, 15000L);
        }
    }

    public void stopUpdateTimer() {
        handler.removeMessages(MSG_SET_PLEASE_UPDATE);
    }

    @Override
    public void getResponse(Resource resource, Throwable throwable) {
        if (resource == null) {
            Log.e("Fastprk", "Fastprk content instance not retrieved.");
        } else {
            Log.e("Fastprk", "Fastprk content instance OK.");
            try {
                String jsonContent = ((ContentInstance) resource).getContent();
                JSONObject jsonObject = new JSONObject(jsonContent);
                boolean full = jsonObject.optBoolean("occupied", false);
                String sensorId = jsonObject.optString("sensorId");
                FastprkClusterItem item = findClusterItem(sensorId);
                item.setUpdating(false);
                item.setFull(full);
                Marker marker = getClusterRenderer().getMarker(item);
                marker.setIcon(BitmapDescriptorFactory.fromResource(getClusterRenderer()
                        .getIconResource(item)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        startUpdateTimer();
    }

    @Override
    protected BaseClusterManager<FastprkClusterItem> newClusterManager() {
        return new FastprkClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<FastprkClusterItem> newClusterRenderer() {
        return new FastprkClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }

    private LatLng loadPosition(int i) {
        try {
            ContentInstance contentInstance = Container.retrieveLatest(CseDetails.aeId,
                    CseDetails.METHOD + CseDetails.hostName,
                    CseDetails.cseName + "/" + APP_NAME + LOCATION_PREFIX + SENSOR_IDS[i],
                    CseDetails.token);
            String jsonContent = contentInstance.getContent();
            JSONObject jsonObject = new JSONObject(jsonContent);
            if (jsonObject.optJSONObject("position") != null) {
                double lat = jsonObject.getJSONObject("position").getDouble("lat");
                double lon = jsonObject.getJSONObject("position").getDouble("lon");
                return new LatLng(lat, lon);
            }
        } catch (Exception e) {
            // Generate a nearby location if no response from the CSE.
            switch (i) {
                // UK demo.
                case 0:
                    return new LatLng(51.807744, -0.811782);
                case 1:
                    return new LatLng(51.811396, -0.814565);
                case 2:
                    return new LatLng(51.814096, -0.802537);
                // US demo.
                case 3:
                    return new LatLng(51.807744, -0.841782);
                case 4:
                    return new LatLng(51.811396, -0.844565);
                case 5:
                    return new LatLng(51.814096, -0.832537);
            }
        }
        return null;
    }

    private void updateAll() {
        for (int i = 0; i < getClusterItems().size(); i++) {
            Container.retrieveLatestAsync(CseDetails.aeId, CseDetails.baseUrl,
                    APP_NAME + OCCUPATION_PREFIX + SENSOR_IDS[i],
                    CseDetails.token, this);
            FastprkClusterItem item = findClusterItem(SENSOR_IDS[i]);
            item.setUpdating(true);
            Marker marker = getClusterRenderer().getMarker(item);
            marker.setIcon(BitmapDescriptorFactory.fromResource(getClusterRenderer()
                    .getIconResource(item)));
        }
    }

    private FastprkClusterItem findClusterItem(String sensorId) {
        for (FastprkClusterItem item : getClusterItems()) {
            if (item.getSensorId().equals(sensorId)) {
                return item;
            }
        }
        return null;
    }
}

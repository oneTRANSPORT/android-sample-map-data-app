package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter, Handler.Callback,
        DougalCallback {

    private static final String TAG = "MapsActivity";
    private static final int MSG_SET_PLEASE_UPDATE = 1;

    private Context context;
    private SupportMapFragment mapFragment;
    // Needed for quick look-up.
    private HashMap<Marker, Item> markerMap = new HashMap<>();
    private Handler handler = new Handler(this);
    private String installationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        maybeCreateInstallationId();
        maybeCreateAe();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setIndoorEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setInfoWindowAdapter(this);
        new LoadMarkerTask(googleMap, markerMap, (ProgressBar) findViewById(R.id.progress_bar))
                .execute();
        handler.sendEmptyMessageDelayed(MSG_SET_PLEASE_UPDATE, 15000L);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_SET_PLEASE_UPDATE:
                updateAll();
                handler.sendEmptyMessageDelayed(MSG_SET_PLEASE_UPDATE, 15000L);
                break;
        }
        return false;
    }

    @Override
    public void getResponse(Resource resource, Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof DougalException) {
                int statusCode = ((DougalException) throwable).getCode();
                if (statusCode != Types.STATUS_CODE_CONFLICT) {
                    Log.e(TAG, "Error creating application entity, status code " + statusCode);
                }
            }
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return markerMap.get(marker).getInfoContents(context);
    }

    @Override
    protected void onPause() {
        handler.removeMessages(MSG_SET_PLEASE_UPDATE);
        super.onPause();
        finish();
    }

    private void maybeCreateInstallationId() {
        installationId = Storage.getInstallationId(context);
        if (installationId == null) {
            installationId = UUID.randomUUID().toString();
            Storage.putInstallationId(context, installationId);
        }
    }

    private void maybeCreateAe() {
        CseDetails.aeId = "C-samplemapdataapp-" + installationId;
        CseDetails.appName = "SampleMapDataApp-" + installationId;
        String applicationId = "App-id-" + installationId;
        ApplicationEntity applicationEntity = new ApplicationEntity(CseDetails.aeId,
                CseDetails.appName, applicationId);
        applicationEntity.createAsync(CseDetails.METHOD + CseDetails.HOST, CseDetails.CSE_NAME,
                CseDetails.USER_NAME, CseDetails.PASSWORD, this);
    }

    private void updateAll() {
        for (Map.Entry<Marker, Item> entry : markerMap.entrySet()) {
            Item item = entry.getValue();
            if (item instanceof WorldsensingItem) {
                ((WorldsensingItem) item).update();
            }
        }
    }
}


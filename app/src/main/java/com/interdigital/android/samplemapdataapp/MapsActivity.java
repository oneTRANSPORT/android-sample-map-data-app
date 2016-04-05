package com.interdigital.android.samplemapdataapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, Handler.Callback {

    private static final int MSG_SET_UPDATING = 1;
    private static final int MSG_SET_UPDATED = 2;

    private GoogleMap googleMap;
    private MarkerData markerData;
    private Handler handler = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setIndoorEnabled(false);
        markerData = new MarkerData();
        markerData.addMapMarkers(googleMap);
        markerData.pointCamera(googleMap);
        handler.sendEmptyMessageDelayed(MSG_SET_UPDATING, 10000L);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_SET_UPDATING:
                markerData.setMarkerIcons(MarkerData.MARKER_TYPE_UPDATING);
                handler.sendEmptyMessageDelayed(MSG_SET_UPDATED, 5000L);
                break;
            case MSG_SET_UPDATED:
                markerData.setMarkerIcons(MarkerData.MARKER_TYPE_UPDATED);
                handler.sendEmptyMessageDelayed(MSG_SET_UPDATING, 10000L);
                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        handler.removeMessages(MSG_SET_UPDATING);
        handler.removeMessages(MSG_SET_UPDATED);
        super.onPause();
    }
}


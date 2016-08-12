package com.interdigital.android.samplemapdataapp;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.lang.ref.WeakReference;

public class ItemObserver extends ContentObserver {

    private static int observed = 0;
    private WeakReference<MapsActivity> weakMapsActivity;

    public ItemObserver(Handler handler, MapsActivity mapsActivity) {
        super(handler);
        this.weakMapsActivity = new WeakReference<>(mapsActivity);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        observed++;
        if (observed == 3) { // Alternative might be to synchronise loader, but deadlock?
            observed = 0;
            updateMap();
        }
    }

    private void updateMap() {
        final MapsActivity mapsActivity = weakMapsActivity.get();
        if (mapsActivity != null) {
            mapsActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mapsActivity.loadMarkers(false);
                }
            });
        }
    }
}

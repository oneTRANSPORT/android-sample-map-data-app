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

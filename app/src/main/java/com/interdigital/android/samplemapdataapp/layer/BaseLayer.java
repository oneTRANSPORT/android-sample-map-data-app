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
import android.text.TextUtils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseLayer {

    // Maximum number of items in a single layer.
    protected static final int MAX_ITEMS = 10;

    // Map items older than this will not be shown.
    private static final long AGE_MILLIS = 5000 * 24 * 60 * 60 * 1000L;

    private static SimpleDateFormat simpleDateFormat;

    private Context context;
    private GoogleMap googleMap;

    public BaseLayer(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
    }

    public abstract void initialise();

    public abstract void load() throws Exception;

    public abstract void addToMap();

    public abstract void setVisible(boolean visible);

    public void onCameraChange(CameraPosition cameraPosition) {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    protected boolean isInDate(String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return false;
        }
        if (simpleDateFormat == null) {
//        2015-12-07T19:17:24+00:00
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.UK);
        }
        try {
            Date date = simpleDateFormat.parse(dateString);
            long nowMillis = System.currentTimeMillis();
            long dateMillis = date.getTime();
            // Test for 9999-12-31 end dates.
            if (dateMillis > nowMillis) {
                return false;
            }
            if (nowMillis - dateMillis < AGE_MILLIS) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}

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

    private static final long AGE_MILLIS = 5 * 24 * 60 * 60 * 1000L;

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
        if (TextUtils.isEmpty(dateString)){
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

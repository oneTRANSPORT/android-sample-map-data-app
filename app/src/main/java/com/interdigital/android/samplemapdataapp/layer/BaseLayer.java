package com.interdigital.android.samplemapdataapp.layer;

import com.google.android.gms.maps.model.CameraPosition;

public abstract class BaseLayer {

    public abstract void setVisible(boolean visible);

    public abstract void onCameraChange(CameraPosition cameraPosition) ;
}

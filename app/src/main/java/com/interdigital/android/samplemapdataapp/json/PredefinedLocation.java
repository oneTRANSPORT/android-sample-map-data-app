package com.interdigital.android.samplemapdataapp.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PredefinedLocation {

    @Expose
    @SerializedName("locationId")
    private String locationId;
    @Expose
    @SerializedName("latitude")
    private String latitude;
    @Expose
    @SerializedName("longitude")
    private String longitude;
    @Expose
    @SerializedName("fromLatitude")
    private String fromLatitude;
    @Expose
    @SerializedName("fromLongitude")
    private String fromLongitude;
    @Expose
    @SerializedName("toLatitude")
    private String toLatitude;
    @Expose
    @SerializedName("toLongitude")
    private String toLongitude;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("descriptor")
    private String descriptor;
    @Expose
    @SerializedName("fromDescriptor")
    private String fromDescriptor;
    @Expose
    @SerializedName("toDescriptor")
    private String toDescriptor;
    @Expose
    @SerializedName("tpegDirection")
    private String tpegDirection;

    public String getLocationId() {
        return locationId;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getFromLatitude() {
        return fromLatitude;
    }

    public String getFromLongitude() {
        return fromLongitude;
    }

    public String getToLatitude() {
        return toLatitude;
    }

    public String getToLongitude() {
        return toLongitude;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getFromDescriptor() {
        return fromDescriptor;
    }

    public String getToDescriptor() {
        return toDescriptor;
    }
}

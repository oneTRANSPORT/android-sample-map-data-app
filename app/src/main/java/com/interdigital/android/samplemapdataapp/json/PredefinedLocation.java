package com.interdigital.android.samplemapdataapp.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PredefinedLocation {

    @Expose
    @SerializedName("locationId")
    public String locationId;
    @Expose
    @SerializedName("latitude")
    public String latitude;
    @Expose
    @SerializedName("longitude")
    public String longitude;
    @Expose
    @SerializedName("fromLatitude")
    public String fromLatitude;
    @Expose
    @SerializedName("fromLongitude")
    public String fromLongitude;
    @Expose
    @SerializedName("toLatitude")
    public String toLatitude;
    @Expose
    @SerializedName("toLongitude")
    public String toLongitude;
    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("descriptor")
    public String descriptor;
    @Expose
    @SerializedName("fromDescriptor")
    public String fromDescriptor;
    @Expose
    @SerializedName("toDescriptor")
    public String toDescriptor;
    @Expose
    @SerializedName("tpegDirection")
    public String tpegDirection;
}

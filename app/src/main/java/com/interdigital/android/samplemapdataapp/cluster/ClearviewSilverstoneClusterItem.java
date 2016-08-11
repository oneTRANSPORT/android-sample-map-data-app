package com.interdigital.android.samplemapdataapp.cluster;

import android.database.Cursor;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.modules.clearviewsilverstone.provider.CvsContract;
import net.uk.onetransport.android.modules.clearviewsilverstone.traffic.Traffic;

import java.util.ArrayList;

public class ClearviewSilverstoneClusterItem implements ClusterItem {

    private Integer sensorId;
    private String title;
    private String description;
    private String type;
    private String changed;
    private Double latitude;
    private Double longitude;
    private LatLng position;
    private ArrayList<Traffic> trafficList;
    private int entering = 0;
    private int leaving = 0;
    private String flowTime = null;

    public ClearviewSilverstoneClusterItem(Cursor cursor,
                                           SparseArray<ArrayList<Traffic>> trafficArray) {
        sensorId = cursor.getInt(cursor.getColumnIndex(
                CvsContract.ClearviewSilverstoneDevice.COLUMN_SENSOR_ID));
        title = cursor.getString(cursor.getColumnIndex(
                CvsContract.ClearviewSilverstoneDevice.COLUMN_TITLE));
        description = cursor.getString(cursor.getColumnIndex(
                CvsContract.ClearviewSilverstoneDevice.COLUMN_DESCRIPTION));
        type = cursor.getString(cursor.getColumnIndex(
                CvsContract.ClearviewSilverstoneDevice.COLUMN_TYPE));
        changed = cursor.getString(cursor.getColumnIndex(
                CvsContract.ClearviewSilverstoneDevice.COLUMN_CHANGED));
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                CvsContract.ClearviewSilverstoneDevice.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                CvsContract.ClearviewSilverstoneDevice.COLUMN_LONGITUDE));
        position = new LatLng(latitude, longitude);
        trafficList = trafficArray.get(sensorId);
        if (trafficList != null) {
            for (Traffic traffic : trafficList) {
                if (traffic.getDirection()) {
                    leaving++;
                } else {
                    entering++;
                }
                flowTime = traffic.getTime();
            }
        }
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Traffic> getTrafficList() {
        return trafficList;
    }

    public void setTrafficList(ArrayList<Traffic> trafficList) {
        this.trafficList = trafficList;
    }

    public int getEntering() {
        return entering;
    }

    public void setEntering(int entering) {
        this.entering = entering;
    }

    public int getLeaving() {
        return leaving;
    }

    public void setLeaving(int leaving) {
        this.leaving = leaving;
    }

    public String getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(String flowTime) {
        this.flowTime = flowTime;
    }
}

package com.interdigital.android.samplemapdataapp.cluster;

import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.provider.BucksContract;

public class RoadWorksClusterItem implements ClusterItem {

    private LatLng position;
    private String comment;
    private String effectOnRoadLayout;
    private String roadMaintenanceType;
    private String impactOnTraffic;
    private String type;
    private String status;
    private String overallStartTime;
    private String overallEndTime;
    private String periods;
    private String locationDescription;
    private String guid;

    public RoadWorksClusterItem(Cursor cursor) {
        comment = cursor.getString(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_COMMENT));
        effectOnRoadLayout = cursor.getString(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_EFFECT_ON_ROAD_LAYOUT));
        roadMaintenanceType = cursor.getString(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_ROAD_MAINTENANCE_TYPE));
        impactOnTraffic = cursor.getString(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_IMPACT_ON_TRAFFIC));
        type = cursor.getString(cursor.getColumnIndex(BucksContract.RoadWorks.COLUMN_TYPE));
        status = cursor.getString(cursor.getColumnIndex(BucksContract.RoadWorks.COLUMN_STATUS));
        overallStartTime = cursor.getString(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_OVERALL_START_TIME));
        overallEndTime = cursor.getString(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_OVERALL_END_TIME));
        periods = cursor.getString(cursor.getColumnIndex(BucksContract.RoadWorks.COLUMN_PERIODS));
        locationDescription = cursor.getString(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_LOCATION_DESCRIPTION));
        guid = cursor.getString(cursor.getColumnIndex(BucksContract.RoadWorks.COLUMN_ID));

        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_LONGITUDE));
        position = new LatLng(latitude, longitude);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String getComment() {
        return comment;
    }

    public String getEffectOnRoadLayout() {
        return effectOnRoadLayout;
    }

    public String getRoadMaintenanceType() {
        return roadMaintenanceType;
    }

    public String getImpactOnTraffic() {
        return impactOnTraffic;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getOverallStartTime() {
        return overallStartTime;
    }

    public String getOverallEndTime() {
        return overallEndTime;
    }

    public String getPeriods() {
        return periods;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public String getGuid() {
        return guid;
    }
}

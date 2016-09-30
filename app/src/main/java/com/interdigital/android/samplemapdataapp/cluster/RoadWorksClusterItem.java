package com.interdigital.android.samplemapdataapp.cluster;

import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import net.uk.onetransport.android.county.bucks.provider.BucksContract;

import static net.uk.onetransport.android.county.bucks.provider.BucksContract.BucksRoadWorks;

public class RoadWorksClusterItem implements ClusterItem {

    private LatLng position;
    private String comment;
    private String effectOnRoadLayout;
    private String roadMaintenanceType;
    private String impactOnTraffic;
    private String status;
    private String overallStartTime;
    private String overallEndTime;
    private String startOfPeriod;
    private String endOfPeriod;
    private String guid;

    public RoadWorksClusterItem(Cursor cursor) {
        comment = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_COMMENT));
        effectOnRoadLayout = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_EFFECT_ON_ROAD_LAYOUT));
        roadMaintenanceType = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_ROAD_MAINTENANCE_TYPE));
        impactOnTraffic = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_IMPACT_ON_TRAFFIC));
        status = cursor.getString(cursor.getColumnIndex(BucksRoadWorks.COLUMN_VALIDITY_STATUS));
        overallStartTime = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_OVERALL_START_TIME));
        overallEndTime = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_OVERALL_END_TIME));
        startOfPeriod = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_START_OF_PERIOD));
        endOfPeriod = cursor.getString(cursor.getColumnIndex(
                BucksRoadWorks.COLUMN_END_OF_PERIOD));
        guid = cursor.getString(cursor.getColumnIndex(BucksContract.BucksRoadWorks.COLUMN_ID));

        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.BucksRoadWorks.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.BucksRoadWorks.COLUMN_LONGITUDE));
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

    public String getStatus() {
        return status;
    }

    public String getOverallStartTime() {
        return overallStartTime;
    }

    public String getOverallEndTime() {
        return overallEndTime;
    }

    public String getStartOfPeriod() {
        return startOfPeriod;
    }

    public String getEndOfPeriod() {
        return endOfPeriod;
    }

    public String getGuid() {
        return guid;
    }
}

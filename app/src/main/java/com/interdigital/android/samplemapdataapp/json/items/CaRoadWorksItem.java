package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.county.bucks.provider.BucksContract;

import java.util.HashMap;

public class CaRoadWorksItem extends Item {

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

    public CaRoadWorksItem(Cursor cursor) {
        setType(TYPE_ROAD_WORKS);
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

        guid = cursor.getString(cursor.getColumnIndex(BucksContract.RoadWorks.COLUMN_ID));

        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BucksContract.RoadWorks.COLUMN_LONGITUDE));
        setLatLng(new LatLng(latitude, longitude));
        Log.i("CaRoadWorksItem", "guid, pos = " + guid + " " + latitude + " " + longitude);
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.roadworks_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public View getInfoContents(Context context) {
        TextView textView = new TextView(context);
        textView.setTextColor(0xff000000);
        textView.setText(guid + " " + comment);
        return textView;
    }

    @Override
    public boolean shouldAdd() {
        return true;
    }
}

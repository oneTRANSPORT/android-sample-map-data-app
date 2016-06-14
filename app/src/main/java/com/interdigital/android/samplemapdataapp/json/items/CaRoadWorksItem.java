package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
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
        if (!comment.contains(": Event Location :") && !comment.contains(": Location :")) {
            // Unformatted text, very difficult to do anything else here.
            TextView textView = new TextView(context);
            textView.setTextColor(0xff000000);
            textView.setText(comment);
            return textView;
        } else {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.pop_up_road_works, null, false);
            TextView reasonTextView = (TextView) view.findViewById(R.id.reason_text_view);
            TextView closuresTextView = (TextView) view.findViewById(R.id.closures_text_view);
            TextView locationTextView = (TextView) view.findViewById(R.id.location_text_view);
            String reason = "";
            String location = "";
            String closures = "";
            if (comment.contains(": Event Location :")) {
                reason = comment.replaceFirst("^.*: Event Reason :", "").replaceFirst(":.*$", "").trim();
                location = comment.replaceFirst("^.*: Event Location :", "").replaceFirst(":.*$", "").trim();
                if (location.isEmpty()) {
                    locationTextView.setVisibility(View.GONE);
                }
                closures = comment.replaceFirst("^.*: Lane Closures :", "").replaceFirst(":.*$", "").trim();
                if (closures.startsWith("TYPE") || closures.trim().isEmpty()) {
                    closuresTextView.setVisibility(View.GONE);
                }
            }
            if (comment.contains(": Location :")) {
                reason = comment.replaceFirst("^.*: Reason :", "").replaceFirst(":.*$", "").trim();
                location = comment.replaceFirst("^.*: Location :", "").replaceFirst(":.*$", "").trim();
                if (location.isEmpty()) {
                    locationTextView.setVisibility(View.GONE);
                }
                closures = comment.replaceFirst("^.*: Lane Closures :", "").replaceFirst(":.*$", "").trim();
                if (closures.startsWith("TYPE") || closures.trim().isEmpty()) {
                    closuresTextView.setVisibility(View.GONE);
                }
            }
            location = location.replaceFirst("^The ", "");
            reasonTextView.setText(reason);
            closuresTextView.setText(closures);
            locationTextView.setText(location);
            return view;
        }
    }

    @Override
    public boolean shouldAdd() {
        return true;
    }
}

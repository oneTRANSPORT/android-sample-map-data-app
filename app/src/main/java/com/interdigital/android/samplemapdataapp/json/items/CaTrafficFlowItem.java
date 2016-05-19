package com.interdigital.android.samplemapdataapp.json.items;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.county.bucks.locations.SegmentLocation;
import net.uk.onetransport.android.county.bucks.trafficflow.TrafficFlow;

import java.util.HashMap;

public class CaTrafficFlowItem extends Item {

    private TrafficFlow trafficFlow;

    public CaTrafficFlowItem(TrafficFlow trafficFlow,
                             HashMap<String, SegmentLocation> segmentLocationMap) {
        setType(TYPE_ANPR);
        this.trafficFlow = trafficFlow;
        updateLocation(segmentLocationMap);
    }

    @Override
    public boolean shouldAdd() {
        if (getLatLng().latitude == 0 && getLatLng().longitude == 0) {
            return false;
        }
        return trafficFlow.getVehicleFlow() != 0 || trafficFlow.getAverageVehicleSpeed() != 0;
    }

    public void updateLocation(HashMap<String, SegmentLocation> segmentLocationMap) {
        if (segmentLocationMap.containsKey(trafficFlow.getLocationReference())) {
            SegmentLocation segmentLocation = segmentLocationMap.get(trafficFlow.getLocationReference());
            setLatLng(new LatLng(
                    segmentLocation.getFromLatitude(),
                    segmentLocation.getFromLongitude()));
            if (!TextUtils.isEmpty(segmentLocation.getToDescriptor())) {
                trafficFlow.setLocationReference(segmentLocation.getToDescriptor());
            } else if (!TextUtils.isEmpty(segmentLocation.getFromDescriptor())) {
                trafficFlow.setLocationReference(segmentLocation.getFromDescriptor());
            }
        } else {
            setLatLng(new LatLng(0, 0));
        }
    }

    public TrafficFlow getTrafficFlow() {
        return trafficFlow;
    }

    @Override
    public void addMarker(GoogleMap googleMap, HashMap<Marker, Item> markerMap) {
        setMarker(googleMap.addMarker(
                new MarkerOptions()
                        .title(getTitle())
                        .position(getLatLng())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.anpr_icon))));
        markerMap.put(getMarker(), this);
    }

    @Override
    public View getInfoContents(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_flow, null, false);
        ((TextView) view.findViewById(R.id.cars_text_view))
                .setText(String.format(context.getString(R.string.cars_per_min), trafficFlow.getVehicleFlow()));
        ((TextView) view.findViewById(R.id.speed_text_view))
                .setText(String.format(context.getString(R.string.kph),
                        Math.round(trafficFlow.getAverageVehicleSpeed())));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(trafficFlow.getLocationReference());
        return view;
    }

}

package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

public class TrafficTravelTimeClusterRenderer extends BaseClusterRenderer<TrafficTravelTimeClusterItem> {

    public TrafficTravelTimeClusterRenderer(Context context, GoogleMap googleMap,
                                            ClusterManager<TrafficTravelTimeClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(TrafficTravelTimeClusterItem trafficTravelTimeClusterItem) {
        return R.drawable.travel_time_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.travel_time_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TrafficTravelTimeClusterItem trafficTravelTimeClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_traffic_travel_time, null, false);
        int travelTime = (int) (double) trafficTravelTimeClusterItem
                .getTrafficTravelTime().getTravelTime();
        String locationReference = trafficTravelTimeClusterItem.getTrafficTravelTime().getToDescriptor();

        ((TextView) view.findViewById(R.id.travel_time_text_view))
                .setText(String.format(context.getString(R.string.travel_time), travelTime / 60, travelTime % 60));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

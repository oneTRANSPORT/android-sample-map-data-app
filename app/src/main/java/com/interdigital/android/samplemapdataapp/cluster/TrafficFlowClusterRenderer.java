package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

public class TrafficFlowClusterRenderer extends BaseClusterRenderer<TrafficFlowClusterItem> {

    public TrafficFlowClusterRenderer(Context context, GoogleMap googleMap,
                                      ClusterManager<TrafficFlowClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(TrafficFlowClusterItem trafficFlowClusterItem) {
        return R.drawable.anpr_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.anpr_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TrafficFlowClusterItem trafficFlowClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_traffic_flow, null, false);
        int vehicleFlow = (int) (double) trafficFlowClusterItem.getTrafficFlow().getVehicleFlow();
        String locationReference = trafficFlowClusterItem.getTrafficFlow().getToDescriptor();

        ((TextView) view.findViewById(R.id.cars_text_view))
                .setText(String.format(context.getString(R.string.cars_per_min), vehicleFlow));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

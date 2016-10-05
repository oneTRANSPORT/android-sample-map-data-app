package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

public class TrafficSpeedClusterRenderer extends BaseClusterRenderer<TrafficSpeedClusterItem> {

    public TrafficSpeedClusterRenderer(Context context, GoogleMap googleMap,
                                       ClusterManager<TrafficSpeedClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(TrafficSpeedClusterItem trafficSpeedClusterItem) {
        return R.drawable.speed_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.speed_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TrafficSpeedClusterItem trafficSpeedClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_traffic_speed, null, false);
        int averageSpeed = (int) (double) trafficSpeedClusterItem.getTrafficSpeed().getAverageVehicleSpeed();
        String locationReference = trafficSpeedClusterItem.getTrafficSpeed().getToDescriptor();

        ((TextView) view.findViewById(R.id.average_speed_text_view))
                .setText(String.format(context.getString(R.string.average_speed), averageSpeed));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

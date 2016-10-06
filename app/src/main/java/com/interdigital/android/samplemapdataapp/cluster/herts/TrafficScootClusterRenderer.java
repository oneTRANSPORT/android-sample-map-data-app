package com.interdigital.android.samplemapdataapp.cluster.herts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;

public class TrafficScootClusterRenderer extends BaseClusterRenderer<TrafficScootClusterItem> {

    public TrafficScootClusterRenderer(Context context, GoogleMap googleMap,
                                       ClusterManager<TrafficScootClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(TrafficScootClusterItem trafficScootClusterItem) {
        return R.drawable.scoot_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.scoot_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TrafficScootClusterItem trafficScootClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_traffic_scoot, null, false);
        int averageSpeed = (int) (double) trafficScootClusterItem.getTrafficScoot().getAverageSpeed();
        String locationReference = trafficScootClusterItem.getTrafficScoot().getToDescriptor();

        ((TextView) view.findViewById(R.id.average_speed_text_view))
                .setText(String.format(context.getString(R.string.average_speed), averageSpeed));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

package com.interdigital.android.samplemapdataapp.cluster.bucks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.bucks.TrafficQueueClusterItem;

public class TrafficQueueClusterRenderer extends BaseClusterRenderer<TrafficQueueClusterItem> {

    public TrafficQueueClusterRenderer(Context context, GoogleMap googleMap,
                                       ClusterManager<TrafficQueueClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(TrafficQueueClusterItem trafficQueueClusterItem) {
        return R.drawable.queue_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.queue_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TrafficQueueClusterItem trafficQueueClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_traffic_queue, null, false);
        int severity = (int) (double) trafficQueueClusterItem.getTrafficQueue().getSeverity();
        String locationReference = trafficQueueClusterItem.getTrafficQueue().getToDescriptor();

        ((TextView) view.findViewById(R.id.cars_text_view))
                .setText(String.format(context.getString(R.string.queue_severity), severity));
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

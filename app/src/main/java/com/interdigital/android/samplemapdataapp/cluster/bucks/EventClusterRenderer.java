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

public class EventClusterRenderer extends BaseClusterRenderer<EventClusterItem> {

    public EventClusterRenderer(Context context, GoogleMap googleMap,
                                ClusterManager<EventClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(EventClusterItem eventClusterItem) {
        return R.drawable.event_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.event_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        EventClusterItem eventClusterItem = getClusterItem(marker);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_event, null, false);
        String reason = eventClusterItem.getEvent().getDescription().replaceFirst(".*Reason : ", "")
                .replaceFirst(" :.*", "");
        String locationReference = eventClusterItem.getEvent().getDescription().replaceFirst(".*Location : ", "")
                .replaceFirst(" :.*", "");

        ((TextView) view.findViewById(R.id.reason_text_view)).setText(reason);
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

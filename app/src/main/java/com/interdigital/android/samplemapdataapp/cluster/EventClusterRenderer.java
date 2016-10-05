package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

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
        String description = eventClusterItem.getEvent().getDescription();
        String locationReference = eventClusterItem.getEvent().getDescription();

        ((TextView) view.findViewById(R.id.description_text_view)).setText(description);
        ((TextView) view.findViewById(R.id.location_text_view)).setText(locationReference);
        return view;
    }
}

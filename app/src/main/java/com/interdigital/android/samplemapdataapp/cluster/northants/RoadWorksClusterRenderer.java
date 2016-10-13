package com.interdigital.android.samplemapdataapp.cluster.northants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;

public class RoadworksClusterRenderer extends BaseClusterRenderer<RoadworksClusterItem> {

    public RoadworksClusterRenderer(Context context, GoogleMap googleMap,
                                    ClusterManager<RoadworksClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(RoadworksClusterItem roadworksClusterItem) {
        return R.drawable.roadworks_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.roadworks_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        RoadworksClusterItem roadworksClusterItem = getClusterItem(marker);
        String comment = roadworksClusterItem.getRoadworks().getComment();
        if (!comment.contains(": Event Location :") && !comment.contains(": Location :")) {
            // Unformatted text, very difficult to do anything else here.
            TextView textView = new TextView(context);
            textView.setTextColor(0xff000000);
            textView.setText(comment);
            return textView;
        } else {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.pop_up_roadworks, null, false);
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

}

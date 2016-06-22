package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.interdigital.android.samplemapdataapp.R;

public class RoadWorksClusterRenderer extends DefaultClusterRenderer<RoadWorksClusterItem>
        implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private boolean visible = true;

    public RoadWorksClusterRenderer(Context context, GoogleMap googleMap,
                                    ClusterManager<RoadWorksClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
        this.context = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(RoadWorksClusterItem roadWorksClusterItem,
                                               MarkerOptions markerOptions) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.roadworks_icon);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(visible);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<RoadWorksClusterItem> cluster,
                                           MarkerOptions markerOptions) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.roadworks_cluster_icon);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(visible);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        RoadWorksClusterItem roadWorksClusterItem = getClusterItem(marker);
        String comment = roadWorksClusterItem.getComment();
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

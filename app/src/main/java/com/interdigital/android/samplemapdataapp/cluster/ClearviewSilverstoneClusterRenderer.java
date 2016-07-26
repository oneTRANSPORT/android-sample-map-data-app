package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;
import com.interdigital.android.samplemapdataapp.view.MicroGraphView;

public class ClearviewSilverstoneClusterRenderer
        extends BaseClusterRenderer<ClearviewSilverstoneClusterItem> {

    public ClearviewSilverstoneClusterRenderer(Context context, GoogleMap googleMap,
                                               ClusterManager<ClearviewSilverstoneClusterItem> clusterManager) {
        super(context, googleMap, clusterManager);
    }

    @Override
    public int getIconResource(ClearviewSilverstoneClusterItem clearviewSilverstoneClusterItem) {
        return R.drawable.carpark_silverstone_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.carpark_silverstone_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        ClearviewSilverstoneClusterItem clearviewSilverstoneClusterItem = getClusterItem(marker);
        String description = clearviewSilverstoneClusterItem.getDescription();
        String changed = "Device installation: " + clearviewSilverstoneClusterItem.getChanged().trim()
                .replaceFirst(" .*$", "");
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_silverstone_car_park, null, false);
        ((TextView) view.findViewById(R.id.sign_text_view)).setText(description);
        ((TextView) view.findViewById(R.id.changed_text_view)).setText(changed);
        if (clearviewSilverstoneClusterItem.getFlowTime() != null) {
            String entering = "Entering: " + String.valueOf(clearviewSilverstoneClusterItem.getEntering());
            String leaving = "Leaving: " + String.valueOf(clearviewSilverstoneClusterItem.getLeaving());
            String flowTime = "Traffic flow at " + clearviewSilverstoneClusterItem.getFlowTime().trim()
                    .replaceFirst("^.* ", "").replaceFirst(":[^:]*$", "");
            ((TextView) view.findViewById(R.id.flow_time_text_view)).setText(flowTime);
            ((TextView) view.findViewById(R.id.entering_text_view)).setText(entering);
            ((TextView) view.findViewById(R.id.leaving_text_view)).setText(leaving);
        } else {
            view.findViewById(R.id.sign_text_view).setVisibility(View.GONE);
            view.findViewById(R.id.entering_text_view).setVisibility(View.GONE);
            view.findViewById(R.id.leaving_text_view).setVisibility(View.GONE);
        }
        ((MicroGraphView)view.findViewById(R.id.entering_graph_view))
                .setValues(clearviewSilverstoneClusterItem.getVehiclesIn());
        ((MicroGraphView)view.findViewById(R.id.leaving_graph_view))
                .setValues(clearviewSilverstoneClusterItem.getVehiclesOut());
        return view;
    }

}

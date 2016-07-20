package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

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
        TextView signTextView = (TextView) view.findViewById(R.id.sign_text_view);
        TextView changedTextView = (TextView) view.findViewById(R.id.changed_text_view);
        signTextView.setText(description);
        changedTextView.setText(changed);
        return view;
    }

}

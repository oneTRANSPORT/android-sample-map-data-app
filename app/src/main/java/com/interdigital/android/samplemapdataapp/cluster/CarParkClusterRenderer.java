package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

public class CarParkClusterRenderer extends BaseClusterRenderer<CarParkClusterItem> {

    public CarParkClusterRenderer(Context context, GoogleMap map,
                                  ClusterManager<CarParkClusterItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    public int getIconResource(CarParkClusterItem carParkClusterItem) {
        if (carParkClusterItem.getEntranceFull() == 0) {
            return R.drawable.carpark_icon;
        }
        return R.drawable.carpark_full_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.carpark_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        CarParkClusterItem carParkClusterItem = getClusterItem(marker);
        int totalParkingCapacity = carParkClusterItem.getTotalParkingCapacity();
        int entranceFull = carParkClusterItem.getEntranceFull();
        String identity = carParkClusterItem.getIdentity();

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_car_park, null, false);
        TextView sizeTextView = (TextView) view.findViewById(R.id.size_text_view);
        TextView signTextView = (TextView) view.findViewById(R.id.sign_text_view);
        TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
        sizeTextView.setText(String.valueOf(totalParkingCapacity));
        if (entranceFull == 0) {
            signTextView.setText(R.string.space);
        } else {
            signTextView.setText(R.string.full);
        }
        nameTextView.setText(identity);
        return view;
    }
}

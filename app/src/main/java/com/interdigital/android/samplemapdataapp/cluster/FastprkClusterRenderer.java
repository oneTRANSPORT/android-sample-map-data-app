package com.interdigital.android.samplemapdataapp.cluster;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.interdigital.android.samplemapdataapp.R;

public class FastprkClusterRenderer extends BaseClusterRenderer<FastprkClusterItem> {

    public FastprkClusterRenderer(Context context, GoogleMap map,
                                  ClusterManager<FastprkClusterItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    public int getIconResource(FastprkClusterItem fastprkClusterItem) {
        if (fastprkClusterItem.isUpdating()) {
            return R.drawable.worldsensing_updating;
        }
        if (fastprkClusterItem.isFull()) {
            return R.drawable.worldsensing_full_icon;
        }
        return R.drawable.worldsensing_icon;
    }

    @Override
    public int getIconClusterResource() {
        return R.drawable.carpark_cluster_icon;
    }

    @Override
    public View getInfoContents(Marker marker) {
        FastprkClusterItem fastprkClusterItem = getClusterItem(marker);
        boolean full = fastprkClusterItem.isFull();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView signTextView = new TextView(context);
        signTextView.setTextColor(0xff000000);
        signTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        signTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        signTextView.setTypeface(Typeface.DEFAULT_BOLD);
        if (!full) {
            signTextView.setText(R.string.space);
        } else {
            signTextView.setText(R.string.full);
        }
        linearLayout.addView(signTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView nameTextView = new TextView(context);
        nameTextView.setText(R.string.worldsensing);
        nameTextView.setTextColor(0xff808080);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        nameTextView.setGravity(GravityCompat.END);
        linearLayout.addView(nameTextView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }
}

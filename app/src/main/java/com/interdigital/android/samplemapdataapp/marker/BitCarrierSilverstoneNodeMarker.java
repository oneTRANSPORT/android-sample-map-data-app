package com.interdigital.android.samplemapdataapp.marker;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.Node;

public class BitCarrierSilverstoneNodeMarker extends BaseMarker {

    private Context context;
    private Node node;

    public BitCarrierSilverstoneNodeMarker(Context context, Node node) {
        this.context = context;
        this.node = node;
        node.setCustomerName(node.getCustomerName().replaceFirst("^[^-]+-", "").trim());
        setLatLng(new LatLng(node.getLatitude(), node.getLongitude()));
        getMarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.node_icon))
                .anchor(0.5f, 0.5f)
                .infoWindowAnchor(0.5f, 0.37f)
                .position(getLatLng());
    }

    @Override
    public View getInfoContents() {
        TextView textView = new TextView(context);
        textView.setText(node.getCustomerName());
        textView.setTextColor(0xff000000);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        return textView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}

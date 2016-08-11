package com.interdigital.android.samplemapdataapp.marker;

import android.content.Context;
import android.database.Cursor;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContract;

public class BitCarrierSilverstoneNodeMarker extends BaseMarker {

    private Context context;
    private Integer nodeId;
    private Integer customerId;
    private String customerName;

    public BitCarrierSilverstoneNodeMarker(Context context, Cursor cursor) {
        this.context = context;
        nodeId = cursor.getInt(cursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneNode.COLUMN_NODE_ID));
        customerId = cursor.getInt(cursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneNode.COLUMN_CUSTOMER_ID));
        customerName = cursor.getString(cursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneNode.COLUMN_CUSTOMER_NAME))
                .replaceFirst("^[^-]+-", "").trim();
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneNode.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneNode.COLUMN_LONGITUDE));
        setLatLng(new LatLng(latitude, longitude));
        getMarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.node_icon))
                .anchor(0.5f, 0.5f)
                .infoWindowAnchor(0.5f, 0.37f)
                .position(getLatLng());
    }

    @Override
    public View getInfoContents() {
        TextView textView = new TextView(context);
        textView.setText(customerName);
        textView.setTextColor(0xff000000);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        return textView;

//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.pop_up_silverstone_car_park, null, false);
//        ((TextView) view.findViewById(R.id.sign_text_view)).setText(description);
//        ((TextView) view.findViewById(R.id.changed_text_view)).setText(changedStr);
//        if (flowTime != null) {
//            String enteringStr = "Entering: " + String.valueOf(entering);
//            String leavingStr = "Leaving: " + String.valueOf(leaving);
//            String flowTimeStr = "Traffic flow at " + flowTime.trim().replaceFirst("^[0-9]+-", "")
//                    .replaceFirst(":[^:]*$", "");
//            ((TextView) view.findViewById(R.id.flow_time_text_view)).setText(flowTimeStr);
//            ((TextView) view.findViewById(R.id.entering_text_view)).setText(enteringStr);
//            ((TextView) view.findViewById(R.id.leaving_text_view)).setText(leavingStr);
//        } else {
//            view.findViewById(R.id.sign_text_view).setVisibility(View.GONE);
//            view.findViewById(R.id.entering_text_view).setVisibility(View.GONE);
//            view.findViewById(R.id.leaving_text_view).setVisibility(View.GONE);
//        }
//        String samples = "(" + String.valueOf(vehiclesIn.size())
//                + "|"
//                + String.valueOf(vehiclesOut.size()) + ")";
//        ((TextView) view.findViewById(R.id.samples_text_view)).setText(samples);
//        ((MicroGraphView) view.findViewById(R.id.entering_graph_view)).setValues(vehiclesIn);
//        ((MicroGraphView) view.findViewById(R.id.leaving_graph_view)).setValues(vehiclesOut);
//        return view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}

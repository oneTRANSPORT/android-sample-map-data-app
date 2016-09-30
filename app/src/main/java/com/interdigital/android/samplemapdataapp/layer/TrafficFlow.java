//package com.interdigital.android.samplemapdataapp.layer;
//
//import android.content.Context;
//import android.database.Cursor;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
//import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
//import com.interdigital.android.samplemapdataapp.cluster.TrafficFlowClusterItem;
//import com.interdigital.android.samplemapdataapp.cluster.TrafficFlowClusterManager;
//import com.interdigital.android.samplemapdataapp.cluster.TrafficFlowClusterRenderer;
//
//import net.uk.onetransport.android.county.bucks.provider.BucksContentHelper;
//
//public class TrafficFlow extends ClusterBaseLayer<TrafficFlowClusterItem> {
//
//    public TrafficFlow(Context context, GoogleMap googleMap) {
//        super(context, googleMap);
//    }
//
//    @Override
//    public void load() throws Exception {
//        Cursor cursor = BucksContentHelper.getTrafficFlows(getContext());
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                TrafficFlowClusterItem trafficFlowClusterItem = new TrafficFlowClusterItem(cursor);
//                if (trafficFlowClusterItem.shouldAdd()) {
//                    getClusterItems().add(trafficFlowClusterItem);
//                }
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//    }
//
//    @Override
//    protected BaseClusterManager<TrafficFlowClusterItem> newClusterManager() {
//        return new TrafficFlowClusterManager(getContext(), getGoogleMap());
//    }
//
//    @Override
//    protected BaseClusterRenderer<TrafficFlowClusterItem> newClusterRenderer() {
//        return new TrafficFlowClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
//    }
//}

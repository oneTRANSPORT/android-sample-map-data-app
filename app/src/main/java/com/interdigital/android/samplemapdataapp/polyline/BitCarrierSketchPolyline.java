package com.interdigital.android.samplemapdataapp.polyline;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContract;

import java.util.ArrayList;

public class BitCarrierSketchPolyline extends BasePolyline {

//    BcsContract.BitCarrierSilverstoneNode.COLUMN_NODE_ID;                                 1159
//    BcsContract.BitCarrierSilverstoneNode.COLUMN_CUSTOMER_ID                       2
//    BcsContract.BitCarrierSilverstoneNode.COLUMN_LATITUDE                                 52.177441
//    BcsContract.BitCarrierSilverstoneNode.COLUMN_LONGITUDE                            -0.955571
//
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_DATE                              2016-07-06
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_HOUR                             9
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_FROM_LOCATION      2 (customer_id)
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_TO_LOCATION            15, "15 via 4"
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_SCORE                           100.0
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_SPEED                           29.6062581428571
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_ELAPSED                      1576.57142857143
//    BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_TREND                         -36.4552836428571
//
//        BcsContract.BitCarrierSilverstoneVector.COLUMN_FROM                                1168
//        BcsContract.BitCarrierSilverstoneVector.COLUMN_TO                                      1170
//        BcsContract.BitCarrierSilverstoneVector.COLUMN_DISTANCE                       8974
//        BcsContract.BitCarrierSilverstoneVector.COLUMN_SKETCH_ID                    38

    // TODO    Find SID from sketch cursor.
    // TODO    Get from, to and distance from Vector.
    // TODO    Get customer IDs for from and to in Node.
    // TODO    Get everything from travel times for given customer from and to.

    private String levelOfService;
    private int colour = 0xff808080;
    private int sketchId;
    private int from = 0;
    private int to = 0;
    private int distance = 0;
    private int customerIdFrom = 0;
    private int customerIdTo = 0;

    public BitCarrierSketchPolyline(Cursor sketchCursor, Cursor nodeCursor, Cursor travelTimeCursor,
                                    Cursor vectorCursor) {
        getLevelOfService(sketchCursor);
        createColour();
        extractCoordinates(sketchCursor);
        getPolylineOptions().zIndex(-1);
//        getSketchId(sketchCursor);
//        extractFromVector(vectorCursor);
//        extractFromNode(nodeCursor);
//        extractFromTravelTime(travelTimeCursor);
    }

    private void extractCoordinates(Cursor sketchCursor) {
        String[] coordinates = sketchCursor.getString(sketchCursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneSketch.COLUMN_COORDINATES)).trim().split(" +");
        ArrayList<LatLng> points = new ArrayList<>();
        for (String s : coordinates) {
            points.add(new LatLng(
                    Double.parseDouble(s.replaceFirst("^.*,", "")), // Latitude is second parameter here.
                    Double.parseDouble(s.replaceFirst(",.*$", ""))));
        }
        addPoints(points);
    }

    private void getLevelOfService(Cursor sketchCursor) {
        levelOfService = sketchCursor.getString(sketchCursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneSketch.COLUMN_LEVEL_OF_SERVICE));
    }

    private void createColour() {
        setMarkerResource(R.drawable.centre_node_grey_icon);
        if (!TextUtils.isEmpty(levelOfService)) {
            if (levelOfService.equalsIgnoreCase("green")) {
                colour = 0xff00ff00;
                setMarkerResource(R.drawable.centre_node_green_icon);
            }
            if (levelOfService.equalsIgnoreCase("yellow")) {
                colour = 0xffffff00;
                setMarkerResource(R.drawable.centre_node_yellow_icon);
            }
            if (levelOfService.equalsIgnoreCase("red")) {
                colour = 0xffff0000;
                setMarkerResource(R.drawable.centre_node_red_icon);
            }
        }
        getPolylineOptions().color(colour);
    }

    private void getSketchId(Cursor sketchCursor) {
        sketchId = sketchCursor.getInt(sketchCursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneSketch.COLUMN_SKETCH_ID));
//        Log.i("BCSP", "-------------------------------------\nSketch id = " + sketchId);
    }

    private void extractFromVector(Cursor cursor) {
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int currentSketchId = cursor.getInt(cursor.getColumnIndex(
                        BcsContract.BitCarrierSilverstoneVector.COLUMN_SKETCH_ID));
                if (currentSketchId == sketchId) {
                    from = cursor.getInt(cursor.getColumnIndex(
                            BcsContract.BitCarrierSilverstoneVector.COLUMN_FROM));
                    to = cursor.getInt(cursor.getColumnIndex(
                            BcsContract.BitCarrierSilverstoneVector.COLUMN_TO));
                    distance = cursor.getInt(cursor.getColumnIndex(
                            BcsContract.BitCarrierSilverstoneVector.COLUMN_DISTANCE));
                    Log.i("BCSP", "From, to = " + from + ", " + to);
                    return;
                }
                cursor.moveToNext();
            }
        }
    }

    private void extractFromNode(Cursor cursor) {
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int currentNodeId = cursor.getInt(cursor.getColumnIndex(
                        BcsContract.BitCarrierSilverstoneNode.COLUMN_NODE_ID));
                int currentCustomerId = cursor.getInt(cursor.getColumnIndex(
                        BcsContract.BitCarrierSilverstoneNode.COLUMN_CUSTOMER_ID));
                if (currentNodeId == from) {
                    customerIdFrom = currentCustomerId;
                    Log.i("BCSP", "Customer from = " + customerIdFrom);
                } else if (currentNodeId == to) {
                    customerIdTo = currentCustomerId;
                    Log.i("BCSP", "Customer to = " + customerIdTo);
                }
                cursor.moveToNext();
            }
        }
    }

    private void extractFromTravelTime(Cursor cursor) {
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                try {
                    int currentFrom = cursor.getInt(cursor.getColumnIndex(
                            BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_FROM_LOCATION));
                    int currentTo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                            BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_TO_LOCATION))
                            .replaceFirst(" .*", ""));
                    if (currentFrom == customerIdFrom && currentTo == customerIdTo) {
                        Log.i("BCSP", "Current customer from, to = " + customerIdFrom + ", " + customerIdTo);

                    }
                } catch (NumberFormatException exception) {
                    // Bogus data, skip this field.
                }
                cursor.moveToNext();
            }
        }
    }
}

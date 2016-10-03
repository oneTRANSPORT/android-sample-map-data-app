package com.interdigital.android.samplemapdataapp.polyline;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.Node;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.sketch.Position;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.sketch.Sketch;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummary;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.vector.Vector;

import java.util.ArrayList;

public class BitCarrierSketchPolyline extends BasePolyline {

    // TODO    Find SID from sketch cursor.
    // TODO    Get from, to and distance from Vector.
    // TODO    Get customer IDs for from and to in Node.
    // TODO    Get everything from travel times for given customer from and to.

    private Context context;
    private String levelOfService;
    private int colour = 0xff808080;
    private int sketchId;
    private int from = 0;
    private int to = 0;
    private int distance = 0;
    private int customerIdFrom = 0;
    private int customerIdTo = 0;
    private Double elapsed;
    private Double speed;

    public BitCarrierSketchPolyline(Context context, Sketch sketch, Node[] nodes,
                                    TravelSummary[] travelSummaries, Vector[] vectors) {
        this.context = context;
        sketchId = sketch.getSketchId();
        Vector vector = getVectorFromId(vectors, sketch);
        levelOfService = vector.getLevelOfService();
        createColour();
        extractCoordinates(sketch);
        getPolylineOptions().zIndex(-1);

        elapsed = vector.getAverageDetails().getPublish().getElapsed();
        speed = vector.getAverageDetails().getPublish().getSpeed();

//        extractFromVector(vectorCursor);
//        extractFromNode(nodeCursor);
//        extractFromTravelTime(travelTimeCursor);
    }

    @Override
    public View getInfoWindow() {
        return null;
    }

    @Override
    public View getInfoContents() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_up_bit_carrier_traffic, null, false);
        if (elapsed > 0) {
            ((TextView) view.findViewById(R.id.elapsed_text_view)).setText("Elapsed time: " + elapsed);
        } else {
            ((TextView) view.findViewById(R.id.elapsed_text_view)).setText("Elapsed time unknown");
        }
        if (speed != null && speed > 0) {
            ((TextView) view.findViewById(R.id.speed_text_view)).setText("Speed: " + speed);
        } else {
            ((TextView) view.findViewById(R.id.speed_text_view)).setText("Speed unknown");
        }
        return view;
    }

    private Vector getVectorFromId(Vector[] vectors, Sketch sketch) {
        for (Vector vector : vectors) {
            if (vector.getVectorId().equals(sketch.getVectorId())) {
                return vector;
            }
        }
        return null;
    }

    private void extractCoordinates(Sketch sketch) {
        Position[] positions = sketch.getPositions();
        ArrayList<LatLng> points = new ArrayList<>();
        for (Position position : positions) {
            points.add(new LatLng(position.getLatitude(), position.getLongitude()));
        }
        addPoints(points);
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

//    private void extractFromVector(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                int currentSketchId = cursor.getInt(cursor.getColumnIndex(
//                        BcsContract.BitCarrierSilverstoneVector.COLUMN_SKETCH_ID));
//                if (currentSketchId == sketchId) {
//                    from = cursor.getInt(cursor.getColumnIndex(
//                            BcsContract.BitCarrierSilverstoneVector.COLUMN_FROM));
//                    to = cursor.getInt(cursor.getColumnIndex(
//                            BcsContract.BitCarrierSilverstoneVector.COLUMN_TO));
//                    distance = cursor.getInt(cursor.getColumnIndex(
//                            BcsContract.BitCarrierSilverstoneVector.COLUMN_DISTANCE));
//                    Log.i("BCSP", "From, to = " + from + ", " + to);
//                    return;
//                }
//                cursor.moveToNext();
//            }
//        }
//    }
//
//    private void extractFromNode(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                int currentNodeId = cursor.getInt(cursor.getColumnIndex(
//                        BcsContract.BitCarrierSilverstoneNode.COLUMN_NODE_ID));
//                int currentCustomerId = cursor.getInt(cursor.getColumnIndex(
//                        BcsContract.BitCarrierSilverstoneNode.COLUMN_CUSTOMER_ID));
//                if (currentNodeId == from) {
//                    customerIdFrom = currentCustomerId;
//                    Log.i("BCSP", "Customer from = " + customerIdFrom);
//                } else if (currentNodeId == to) {
//                    customerIdTo = currentCustomerId;
//                    Log.i("BCSP", "Customer to = " + customerIdTo);
//                }
//                cursor.moveToNext();
//            }
//        }
//    }
//
//    private void extractFromTravelTime(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                try {
//                    int currentFrom = cursor.getInt(cursor.getColumnIndex(
//                            BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_FROM_LOCATION));
//                    int currentTo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(
//                            BcsContract.BitCarrierSilverstoneTravelTime.COLUMN_TO_LOCATION))
//                            .replaceFirst(" .*", ""));
//                    if (currentFrom == customerIdFrom && currentTo == customerIdTo) {
//                        Log.i("BCSP", "Current customer from, to = " + customerIdFrom + ", " + customerIdTo);
//
//                    }
//                } catch (NumberFormatException exception) {
//                    // Bogus data, skip this field.
//                }
//                cursor.moveToNext();
//            }
//        }
//    }
}

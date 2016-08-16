package com.interdigital.android.samplemapdataapp.polyline;

import android.database.Cursor;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsContract;

import java.util.ArrayList;

public class BitCarrierSketchPolyline extends BasePolyline {

    public BitCarrierSketchPolyline(Cursor cursor) {
        String levelOfService = cursor.getString(cursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneSketch.COLUMN_LEVEL_OF_SERVICE));
        int colour = 0xff808080;
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
        String[] coordinates = cursor.getString(cursor.getColumnIndex(
                BcsContract.BitCarrierSilverstoneSketch.COLUMN_COORDINATES)).trim().split(" +");
        ArrayList<LatLng> points = new ArrayList<>();
        for (String s : coordinates) {
            points.add(new LatLng(
                    Double.parseDouble(s.replaceFirst("^.*,", "")), // Latitude is second parameter here.
                    Double.parseDouble(s.replaceFirst(",.*$", ""))));
        }
        addPoints(points);
        getPolylineOptions().zIndex(-1);
    }
}

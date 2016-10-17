/* Copyright 2016 InterDigital Communications, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.interdigital.android.samplemapdataapp.polyline;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.interdigital.android.samplemapdataapp.R;

import net.uk.onetransport.android.modules.bitcarriersilverstone.config.sketch.Position;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.sketch.Sketch;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.vector.Vector;

import java.util.ArrayList;

public class BitCarrierSketchPolyline extends BasePolyline {

    private Context context;
    private String levelOfService;
    private int colour = 0xff808080;
    private Double elapsed;
    private Double speed;

    public BitCarrierSketchPolyline(Context context, Sketch sketch, Vector[] vectors) {
        this.context = context;
        Vector vector = getVectorFromId(vectors, sketch);
        levelOfService = vector.getLevelOfService();
        createColour();
        extractCoordinates(sketch);
        getPolylineOptions().zIndex(-1);

        elapsed = vector.getAverageDetails().getPublish().getElapsed();
        speed = vector.getAverageDetails().getPublish().getSpeed();
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
}

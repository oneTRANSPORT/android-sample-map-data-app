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
package com.interdigital.android.samplemapdataapp.layer.herts;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.herts.CarParkClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.herts.CarParkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.herts.CarParkClusterRenderer;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;

import net.uk.onetransport.android.county.herts.carparks.CarPark;
import net.uk.onetransport.android.county.herts.provider.HertsContentHelper;

public class HertsCarParks extends ClusterBaseLayer<CarParkClusterItem> {

    public HertsCarParks(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        CarPark[] carParks = HertsContentHelper.getLatestCarParks(getContext());
        int count = 0;
        for (CarPark carPark : carParks) {
            if (count < MAX_ITEMS) {
                CarParkClusterItem carParkClusterItem = new CarParkClusterItem(carPark);
                getClusterItems().add(carParkClusterItem);
                count++;
            }
        }
    }

    @Override
    protected BaseClusterManager<CarParkClusterItem> newClusterManager() {
        return new CarParkClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<CarParkClusterItem> newClusterRenderer() {
        return new CarParkClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

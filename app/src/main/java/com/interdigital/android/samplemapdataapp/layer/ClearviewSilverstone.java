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
package com.interdigital.android.samplemapdataapp.layer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.BaseClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.ClearviewSilverstoneClusterItem;
import com.interdigital.android.samplemapdataapp.cluster.ClearviewSilverstoneClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.ClearviewSilverstoneClusterRenderer;

import net.uk.onetransport.android.modules.clearviewsilverstone.device.Device;
import net.uk.onetransport.android.modules.clearviewsilverstone.provider.CvsContentHelper;
import net.uk.onetransport.android.modules.clearviewsilverstone.traffic.TrafficItem;

public class ClearviewSilverstone extends ClusterBaseLayer<ClearviewSilverstoneClusterItem> {


    public ClearviewSilverstone(Context context, GoogleMap googleMap) {
        super(context, googleMap);
    }

    @Override
    public void load() throws Exception {
        TrafficItem[] trafficItems = CvsContentHelper.getLatestTrafficItems(getContext());
        Device[] devices = CvsContentHelper.getLatestDevices(getContext());
        for (Device device : devices) {
            ClearviewSilverstoneClusterItem csci = new ClearviewSilverstoneClusterItem(device,
                    trafficItems);
            getClusterItems().add(csci);
        }
    }

    @Override
    protected BaseClusterManager<ClearviewSilverstoneClusterItem> newClusterManager() {
        return new ClearviewSilverstoneClusterManager(getContext(), getGoogleMap());
    }

    @Override
    protected BaseClusterRenderer<ClearviewSilverstoneClusterItem> newClusterRenderer() {
        return new ClearviewSilverstoneClusterRenderer(getContext(), getGoogleMap(), getClusterManager());
    }
}

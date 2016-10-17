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
package com.interdigital.android.samplemapdataapp;

import android.content.Context;

public class CseDetails {

    public static final String METHOD = "https://";
    public static String hostName;
    public static String cseName;
    public static String baseUrl;
    public static String token;
    public static String aeId;
    public static String appName;

    public static void initialiseFromPrefs(Context context) {
        hostName = Storage.getHostName(context);
        cseName = Storage.getCseName(context);
        token = Storage.getToken(context);
        makeBaseUrl();
    }

    public static void makeBaseUrl() {
        baseUrl = METHOD + hostName + "/" + cseName + "/";
    }
}

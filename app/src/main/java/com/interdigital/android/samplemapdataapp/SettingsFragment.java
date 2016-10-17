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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(Storage.FILE_NAME);
        addPreferencesFromResource(R.xml.pref_server);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        onSharedPreferenceChanged(sharedPreferences, Storage.KEY_HOST_NAME);
        onSharedPreferenceChanged(sharedPreferences, Storage.KEY_CSE_NAME);
        onSharedPreferenceChanged(sharedPreferences, Storage.KEY_AE_ID);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case Storage.KEY_HOST_NAME:
                Preference hostNamePref = findPreference(key);
                hostNamePref.setSummary(sharedPreferences.getString(key, ""));
                break;
            case Storage.KEY_CSE_NAME:
                Preference cseNamePref = findPreference(key);
                cseNamePref.setSummary(sharedPreferences.getString(key, ""));
                break;
            case Storage.KEY_AE_ID:
                Preference userNamePref = findPreference(key);
                userNamePref.setSummary(sharedPreferences.getString(key, ""));
                break;
        }
        CseDetails.initialiseFromPrefs(getActivity().getApplicationContext());
    }
}

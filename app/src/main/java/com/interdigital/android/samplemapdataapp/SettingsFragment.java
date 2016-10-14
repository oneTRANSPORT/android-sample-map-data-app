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

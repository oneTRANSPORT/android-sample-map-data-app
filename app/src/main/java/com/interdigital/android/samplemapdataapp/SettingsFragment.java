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
        onSharedPreferenceChanged(sharedPreferences, "pref_host_name");
        onSharedPreferenceChanged(sharedPreferences, "pref_cse_name");
        onSharedPreferenceChanged(sharedPreferences, "pref_user_name");
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_host_name")) {
            Preference hostNamePref = findPreference(key);
            hostNamePref.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals("pref_cse_name")) {
            Preference cseNamePref = findPreference(key);
            cseNamePref.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals("pref_user_name")) {
            Preference userNamePref = findPreference(key);
            userNamePref.setSummary(sharedPreferences.getString(key, ""));
        }
        CseDetails.initialiseFromPrefs(getActivity().getApplicationContext());
    }
}

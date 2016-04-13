package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.preference.PreferenceManager;

public class Storage {

    public static final String KEY_INSTALLATION_ID = "installation-id";

    public static String getInstallationId(Context context) {
        return getString(context, KEY_INSTALLATION_ID);
    }

    public static void putInstallationId(Context context, String installationId) {
        putString(context, KEY_INSTALLATION_ID, installationId);
    }

    private static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, null);
    }

    private static void putString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(key, value).commit();
    }
}

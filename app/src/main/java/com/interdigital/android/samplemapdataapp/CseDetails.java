package com.interdigital.android.samplemapdataapp;

import android.content.Context;

public class CseDetails {

    public static final String METHOD = "https://";
    public static String hostName;
    public static String cseName;
    public static String baseUrl;
    public static String userName;
    public static String password;
    public static String aeId;
    public static String appName;

    public static void initialiseFromPrefs(Context context) {
        hostName = Storage.getHostName(context);
        cseName = Storage.getCseName(context);
        userName = Storage.getUserName(context);
        password = Storage.getPassword(context);
        makeBaseUrl();
    }

    public static void makeBaseUrl() {
        baseUrl = METHOD + hostName + "/" + cseName + "/";
    }
}

package com.interdigital.android.samplemapdataapp;

import android.app.Application;

import net.uk.onetransport.android.county.bucks.provider.BucksProvider;

public class SmdApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BucksProvider.initialise(getApplicationContext());
    }
}

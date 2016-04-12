package com.interdigital.android.samplemapdataapp;

public class DemoUnitItem extends CarParkItem {

    public DemoUnitItem(String title, double latitude, double longitude) {
        super(title, latitude, longitude);
    }

    @Override
    public int getPlainResource() {
        return R.drawable.worldsensing_icon;
    }

    @Override
    public int getUpdatingResource() {
        return R.drawable.worldsensing_updating;
    }

    @Override
    public int getUpdatedResource() {
        return R.drawable.worldsensing_updated;
    }

    @Override
    public int getAlternateResource() {
        return R.drawable.worldsensing_full;
    }
}

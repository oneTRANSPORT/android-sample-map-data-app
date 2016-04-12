package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class CloudAmberItem extends CarParkItem {

    public CloudAmberItem(String title, double latitude, double longitude) {
        super(title, latitude, longitude);
    }

    @Override
    public View getInfoContents(Context context) {
        TextView textView = new TextView(context);
        textView.setText(getTitle());
        textView.setTextColor(0xff0080ff);
        return textView;
    }

    @Override
    public void update() {
        // TODO    Set the icon to updating.
        // TODO    Start the network connection.
        // TODO    When complete, update the icon.
    }

}

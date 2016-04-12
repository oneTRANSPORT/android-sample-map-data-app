package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

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

    @Override
    public View getInfoContents(Context context) {
        TextView textView = new TextView(context);
        textView.setText(getTitle());
        textView.setTextColor(0xff008000);
        return textView;
    }

    @Override
    public void update() {
// TODO This is the call to David's CI.
//        wget -S --no-check-certificate --user=pthomas --password=EKFYGUCC
//                --header="X-M2M-RI: xyz"
//                --header="X-M2M-Origin: S_Feed_Import"
//                --header="Accept: application/json"
//        https://cse-01.onetransport.uk.net/ONETCSE01/Worldsensing/FastPrk/v1.0/Owner1/Fastprk-Demo-London/Sensor/555b11dbcb9b3277b782e708/la
// TODO And this is what comes back.
//        {
//            "m2m:cin":{
//            "cnf":"application/json",
//                    "con":"{ \"sensorId\": \"555b11dbcb9b3277b782e708\", \"deviceId\": 71621, \"spotId\": \"\", \"position\": { \"sensorLocationId\": \"555b11dbcb9b3277b782e708\", \"deviceId\": 71621, \"lon\": -1.0148315588854797, \"lat\": 52.07316820270525, \"positioned\": true, \"type\": \"position\" }, \"type\": \"sensor\" }",
//                    "cs":270,
//                    "ct":"20160331T162648",
//                    "et":"20160404T034648",
//                    "lt":"20160331T162648",
//                    "pi":"cnt_20160331T162632_10715",
//                    "ri":"cin_20160331T162648_10719",
//                    "rn":"cin_20160331T162648_10720DSIDAWAYreq23_2142744352_nm",
//                    "st":1,
//                    "ty":4
//        }
//        }
    }
}

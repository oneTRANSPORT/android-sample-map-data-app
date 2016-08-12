package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.samplemapdataapp.layer.BaseLayer;
import com.interdigital.android.samplemapdataapp.layer.BitCarrierSilverstone;
import com.interdigital.android.samplemapdataapp.layer.BitCarrierSilverstoneNodes;
import com.interdigital.android.samplemapdataapp.layer.CarPark;
import com.interdigital.android.samplemapdataapp.layer.ClearviewSilverstone;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;
import com.interdigital.android.samplemapdataapp.layer.Fastprk;
import com.interdigital.android.samplemapdataapp.layer.MarkerBaseLayer;
import com.interdigital.android.samplemapdataapp.layer.RoadWorks;
import com.interdigital.android.samplemapdataapp.layer.TrafficFlow;
import com.interdigital.android.samplemapdataapp.layer.VariableMessageSign;

import net.uk.onetransport.android.county.bucks.provider.BucksProviderModule;
import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsProviderModule;
import net.uk.onetransport.android.modules.clearviewsilverstone.provider.CvsProviderModule;
import net.uk.onetransport.android.modules.common.provider.lastupdated.LastUpdatedProviderModule;

import java.util.UUID;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter,
        DougalCallback, CompoundButton.OnCheckedChangeListener, GoogleMap.OnCameraChangeListener,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapsActivity";
    private static final int VMS = 0;
    private static final int CAR_PARK = 1;
    private static final int TRAFFIC_FLOW = 2;
    private static final int ROAD_WORKS = 3;
    private static final int FASTPRK = 4;
    private static final int CLEARVIEW = 5;
    private static final int BITCARRIER_NODES = 6;
    private static final int BITCARRIER_ROADS = 7;


    public static float density;

    private Context context;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private String installationId;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CheckBox vmsCheckbox;
    private CheckBox carParkCheckbox;
    private CheckBox trafficFlowCheckBox;
    private CheckBox roadWorksCheckBox;
    private CheckBox clearviewCheckBox;
    private CheckBox bitcarrierCheckBox;
    private ItemObserver itemObserver;
    private BaseLayer[] layers = new BaseLayer[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        density = displayMetrics.density;
        context = getApplicationContext();
        setContentView(R.layout.activity_maps);
        initialiseToolbar();
        initialisePreferences();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        maybeCreateInstallationId();
        maybeCreateAe();
        initialiseDrawer();
        itemObserver = new ItemObserver(null, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        actionBarDrawerToggle.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.refresh_item:
                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                // TODO    Find a way to merge these sync adapter calls.
                BucksProviderModule.refresh(context, vmsCheckbox.isChecked(), carParkCheckbox.isChecked(),
                        trafficFlowCheckBox.isChecked(), roadWorksCheckBox.isChecked());
                CvsProviderModule.refresh(context, clearviewCheckBox.isChecked(),
                        clearviewCheckBox.isChecked());
                BcsProviderModule.refresh(context, bitcarrierCheckBox.isChecked(),
                        bitcarrierCheckBox.isChecked(), bitcarrierCheckBox.isChecked(),
                        bitcarrierCheckBox.isChecked());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setIndoorEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setInfoWindowAdapter(this);
        googleMap.setOnCameraChangeListener(this);
        googleMap.setOnMarkerClickListener(this);
        net.uk.onetransport.android.county.bucks.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        net.uk.onetransport.android.modules.clearviewsilverstone.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        net.uk.onetransport.android.modules.bitcarriersilverstone.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        layers[VMS] = new VariableMessageSign(context, googleMap);
        layers[CAR_PARK] = new CarPark(context, googleMap);
        layers[TRAFFIC_FLOW] = new TrafficFlow(context, googleMap);
        layers[ROAD_WORKS] = new RoadWorks(context, googleMap);
        layers[FASTPRK] = new Fastprk(context, googleMap);
        layers[CLEARVIEW] = new ClearviewSilverstone(context, googleMap);
        layers[BITCARRIER_NODES] = new BitCarrierSilverstoneNodes(context, googleMap);
        layers[BITCARRIER_ROADS] = new BitCarrierSilverstone(context, googleMap);
        loadMarkers(true);
    }

    public void loadMarkers(boolean moveMap) {
        new LoadMarkerTask(googleMap, (ProgressBar) findViewById(R.id.progress_bar),
                moveMap, layers).execute();
    }

    @Override
    public void getResponse(Resource resource, Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof DougalException) {
                int statusCode = ((DougalException) throwable).getCode();
                if (statusCode != Types.STATUS_CODE_CONFLICT) {
                    Log.e(TAG, "Error creating application entity, status code " + statusCode);
                }
            }
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        for (BaseLayer layer : layers) {
            View view = null;
            if (layer instanceof MarkerBaseLayer) {
                view = ((MarkerBaseLayer) layer).getInfoWindow(marker);
            } else if (layer instanceof ClusterBaseLayer) {
                view = ((ClusterBaseLayer) layer).getInfoWindow(marker);
            }
            if (view != null) {
                return view;
            }
        }
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        for (BaseLayer layer : layers) {
            View view = null;
            if (layer instanceof MarkerBaseLayer) {
                view = ((MarkerBaseLayer) layer).getInfoContents(marker);
            } else if (layer instanceof ClusterBaseLayer) {
                view = ((ClusterBaseLayer) layer).getInfoContents(marker);
            }
            if (view != null) {
                return view;
            }
        }
        return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        switch (compoundButton.getId()) {
            case R.id.vms_checkbox:
                layers[VMS].setVisible(checked);
                break;
            case R.id.car_park_checkbox:
                layers[CAR_PARK].setVisible(checked);
                break;
            case R.id.traffic_flow_checkbox:
                layers[TRAFFIC_FLOW].setVisible(checked);
                break;
            case R.id.road_works_checkbox:
                layers[ROAD_WORKS].setVisible(checked);
                break;
            case R.id.clearview_checkbox:
                layers[CLEARVIEW].setVisible(checked);
                break;
            case R.id.bitcarrier_checkbox:
                layers[BITCARRIER_NODES].setVisible(checked);
                layers[BITCARRIER_ROADS].setVisible(checked);
                break;
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        for (BaseLayer layer : layers) {
            layer.onCameraChange(cameraPosition);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (BaseLayer layer : layers) {
            if (layer instanceof MarkerBaseLayer) {
                if (((MarkerBaseLayer) layer).onMarkerClick(marker)) {
                    return true;
                }
            } else if (layer instanceof ClusterBaseLayer) {
                if (((ClusterBaseLayer) layer).onMarkerClick(marker)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (layers[FASTPRK] != null) {
            ((Fastprk) layers[FASTPRK]).startUpdateTimer();
        }
        getContentResolver().registerContentObserver(LastUpdatedProviderModule.LAST_UPDATED_URI,
                false, itemObserver);
    }

    @Override
    protected void onPause() {
        getContentResolver().unregisterContentObserver(itemObserver);
        if (layers[FASTPRK] != null) {
            ((Fastprk) layers[FASTPRK]).stopUpdateTimer();
        }
        super.onPause();
    }

    private void initialiseToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }

    private void maybeCreateInstallationId() {
        installationId = Storage.getInstallationId(context);
        if (installationId == null) {
            installationId = UUID.randomUUID().toString();
            Storage.putInstallationId(context, installationId);
        }
    }

    private void maybeCreateAe() {
        CseDetails.aeId = "C-" + getString(R.string.pref_default_user_name);
        CseDetails.appName = "SampleMapDataApp";
        String applicationId = "SampleMapDataApp-Id";
        ApplicationEntity applicationEntity = new ApplicationEntity(CseDetails.aeId,
                CseDetails.appName, applicationId, CseDetails.METHOD + CseDetails.hostName,
                CseDetails.cseName, false);
        applicationEntity.createAsync(CseDetails.userName, CseDetails.password, this);
    }

    private void initialisePreferences() {
        PreferenceManager.setDefaultValues(this, Storage.FILE_NAME, MODE_PRIVATE,
                R.xml.pref_server, false);
        CseDetails.initialiseFromPrefs(context);
    }

    private void initialiseDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        vmsCheckbox = (CheckBox) findViewById(R.id.vms_checkbox);
        carParkCheckbox = (CheckBox) findViewById(R.id.car_park_checkbox);
        trafficFlowCheckBox = (CheckBox) findViewById(R.id.traffic_flow_checkbox);
        roadWorksCheckBox = (CheckBox) findViewById(R.id.road_works_checkbox);
        clearviewCheckBox = (CheckBox) findViewById(R.id.clearview_checkbox);
        bitcarrierCheckBox = (CheckBox) findViewById(R.id.bitcarrier_checkbox);
        vmsCheckbox.setOnCheckedChangeListener(this);
        carParkCheckbox.setOnCheckedChangeListener(this);
        trafficFlowCheckBox.setOnCheckedChangeListener(this);
        roadWorksCheckBox.setOnCheckedChangeListener(this);
        clearviewCheckBox.setOnCheckedChangeListener(this);
        bitcarrierCheckBox.setOnCheckedChangeListener(this);
    }
}


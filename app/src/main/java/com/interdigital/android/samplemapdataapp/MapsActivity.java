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
import com.interdigital.android.samplemapdataapp.layer.ClearviewSilverstone;
import com.interdigital.android.samplemapdataapp.layer.ClusterBaseLayer;
import com.interdigital.android.samplemapdataapp.layer.MarkerBaseLayer;
import com.interdigital.android.samplemapdataapp.layer.PolylineBaseLayer;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksCarParks;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksEvents;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksRoadworks;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksTrafficFlows;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksTrafficQueues;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksTrafficScoots;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksTrafficSpeeds;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksTrafficTravelTimes;
import com.interdigital.android.samplemapdataapp.layer.bucks.BucksVariableMessageSigns;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsCarParks;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsEvents;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsRoadworks;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsTrafficFlows;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsTrafficScoots;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsTrafficSpeeds;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsTrafficTravelTimes;
import com.interdigital.android.samplemapdataapp.layer.herts.HertsVariableMessageSigns;
import com.interdigital.android.samplemapdataapp.layer.northants.NorthantsCarParks;
import com.interdigital.android.samplemapdataapp.layer.northants.NorthantsRoadworks;
import com.interdigital.android.samplemapdataapp.layer.northants.NorthantsTrafficFlows;
import com.interdigital.android.samplemapdataapp.layer.northants.NorthantsTrafficTravelTimes;
import com.interdigital.android.samplemapdataapp.layer.northants.NorthantsVariableMessageSigns;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonCarParks;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonEvents;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonRoadworks;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonTrafficFlows;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonTrafficQueues;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonTrafficScoots;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonTrafficSpeeds;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonTrafficTravelTimes;
import com.interdigital.android.samplemapdataapp.layer.oxon.OxonVariableMessageSigns;

import net.uk.onetransport.android.county.bucks.provider.BucksProviderModule;
import net.uk.onetransport.android.county.herts.provider.HertsProviderModule;
import net.uk.onetransport.android.county.northants.provider.NorthantsProviderModule;
import net.uk.onetransport.android.county.oxon.provider.OxonProviderModule;
import net.uk.onetransport.android.modules.bitcarriersilverstone.provider.BcsProviderModule;
import net.uk.onetransport.android.modules.clearviewsilverstone.provider.CvsProviderModule;
import net.uk.onetransport.android.modules.common.provider.lastupdated.LastUpdatedProviderModule;

import java.util.UUID;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter,
        DougalCallback, CompoundButton.OnCheckedChangeListener, GoogleMap.OnCameraChangeListener,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapsActivity";
    private static final int BUCKS_VMS = 0;
    private static final int BUCKS_CAR_PARK = 1;
    private static final int BUCKS_EVENT = 2;
    private static final int BUCKS_TRAFFIC_FLOW = 3;
    private static final int BUCKS_TRAFFIC_QUEUE = 4;
    private static final int BUCKS_TRAFFIC_SCOOT = 5;
    private static final int BUCKS_TRAFFIC_SPEED = 6;
    private static final int BUCKS_TRAFFIC_TRAVEL_TIME = 7;
    private static final int BUCKS_ROADWORKS = 8;
    private static final int HERTS_VMS = 9;
    private static final int HERTS_CAR_PARK = 10;
    private static final int HERTS_EVENT = 11;
    private static final int HERTS_TRAFFIC_FLOW = 12;
    private static final int HERTS_TRAFFIC_SCOOT = 13;
    private static final int HERTS_TRAFFIC_SPEED = 14;
    private static final int HERTS_TRAFFIC_TRAVEL_TIME = 15;
    private static final int HERTS_ROADWORKS = 16;
    private static final int NORTHANTS_VMS = 17;
    private static final int NORTHANTS_CAR_PARK = 18;
    private static final int NORTHANTS_TRAFFIC_FLOW = 19;
    private static final int NORTHANTS_TRAFFIC_TRAVEL_TIME = 20;
    private static final int NORTHANTS_ROADWORKS = 21;
    private static final int OXON_VMS = 22;
    private static final int OXON_CAR_PARK = 23;
    private static final int OXON_EVENT = 24;
    private static final int OXON_TRAFFIC_FLOW = 25;
    private static final int OXON_TRAFFIC_QUEUE = 26;
    private static final int OXON_TRAFFIC_SCOOT = 27;
    private static final int OXON_TRAFFIC_SPEED = 28;
    private static final int OXON_TRAFFIC_TRAVEL_TIME = 29;
    private static final int OXON_ROADWORKS = 30;
    private static final int FASTPRK = 31;
    private static final int CLEARVIEW = 32;
    private static final int BITCARRIER_NODES = 33;
    private static final int BITCARRIER_ROADS = 34;

    public static float density;

    private Context context;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private String installationId;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CheckBox bucksVmsCheckbox;
    private CheckBox bucksCarParkCheckbox;
    private CheckBox bucksEventCheckbox;
    private CheckBox bucksTrafficFlowCheckBox;
    private CheckBox bucksTrafficQueueCheckBox;
    private CheckBox bucksTrafficScootCheckBox;
    private CheckBox bucksTrafficSpeedCheckBox;
    private CheckBox bucksTrafficTravelTimeCheckBox;
    private CheckBox bucksRoadWorksCheckBox;
    private CheckBox hertsVmsCheckbox;
    private CheckBox hertsCarParkCheckbox;
    private CheckBox hertsEventCheckbox;
    private CheckBox hertsTrafficFlowCheckBox;
    private CheckBox hertsTrafficScootCheckBox;
    private CheckBox hertsTrafficSpeedCheckBox;
    private CheckBox hertsTrafficTravelTimeCheckBox;
    private CheckBox hertsRoadWorksCheckBox;
    private CheckBox northantsVmsCheckbox;
    private CheckBox northantsCarParkCheckbox;
    private CheckBox northantsTrafficFlowCheckBox;
    private CheckBox northantsTrafficTravelTimeCheckBox;
    private CheckBox northantsRoadWorksCheckBox;
    private CheckBox oxonVmsCheckbox;
    private CheckBox oxonCarParkCheckbox;
    private CheckBox oxonEventCheckbox;
    private CheckBox oxonTrafficFlowCheckBox;
    private CheckBox oxonTrafficQueueCheckBox;
    private CheckBox oxonTrafficScootCheckBox;
    private CheckBox oxonTrafficSpeedCheckBox;
    private CheckBox oxonTrafficTravelTimeCheckBox;
    private CheckBox oxonRoadWorksCheckBox;
    private CheckBox fastprkCheckBox;
    private CheckBox clearviewCheckBox;
    private CheckBox bitcarrierCheckBox;
    private ItemObserver itemObserver;
    private BaseLayer[] layers;

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
                // TODO    Same order as app.
                BucksProviderModule.refresh(context,
                        bucksCarParkCheckbox.isChecked(),
                        bucksEventCheckbox.isChecked(),
                        bucksRoadWorksCheckBox.isChecked(),
                        bucksTrafficFlowCheckBox.isChecked(),
                        bucksTrafficQueueCheckBox.isChecked(),
                        bucksTrafficScootCheckBox.isChecked(),
                        bucksTrafficSpeedCheckBox.isChecked(),
                        bucksTrafficTravelTimeCheckBox.isChecked(),
                        bucksVmsCheckbox.isChecked());
                HertsProviderModule.refresh(context,
                        hertsCarParkCheckbox.isChecked(),
                        hertsEventCheckbox.isChecked(),
                        hertsRoadWorksCheckBox.isChecked(),
                        hertsTrafficFlowCheckBox.isChecked(),
                        hertsTrafficScootCheckBox.isChecked(),
                        hertsTrafficSpeedCheckBox.isChecked(),
                        hertsTrafficTravelTimeCheckBox.isChecked(),
                        hertsVmsCheckbox.isChecked());
                NorthantsProviderModule.refresh(context,
                        northantsCarParkCheckbox.isChecked(),
                        northantsRoadWorksCheckBox.isChecked(),
                        northantsTrafficFlowCheckBox.isChecked(),
                        northantsTrafficTravelTimeCheckBox.isChecked(),
                        northantsVmsCheckbox.isChecked());
                OxonProviderModule.refresh(context,
                        oxonCarParkCheckbox.isChecked(),
                        oxonEventCheckbox.isChecked(),
                        oxonRoadWorksCheckBox.isChecked(),
                        oxonTrafficFlowCheckBox.isChecked(),
                        oxonTrafficQueueCheckBox.isChecked(),
                        oxonTrafficScootCheckBox.isChecked(),
                        oxonTrafficSpeedCheckBox.isChecked(),
                        oxonTrafficTravelTimeCheckBox.isChecked(),
                        oxonVmsCheckbox.isChecked());
                CvsProviderModule.refresh(context, clearviewCheckBox.isChecked(),
                        clearviewCheckBox.isChecked());
                BcsProviderModule.refresh(context, bitcarrierCheckBox.isChecked(),
                        bitcarrierCheckBox.isChecked(), bitcarrierCheckBox.isChecked(), true);
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
        net.uk.onetransport.android.county.herts.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        net.uk.onetransport.android.county.northants.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        net.uk.onetransport.android.county.oxon.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        net.uk.onetransport.android.modules.clearviewsilverstone.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        net.uk.onetransport.android.modules.bitcarriersilverstone.authentication.CredentialHelper
                .initialiseCredentials(context, getString(R.string.pref_default_user_name),
                        getString(R.string.pref_default_password), installationId);
        layers = new BaseLayer[]{
                new BucksVariableMessageSigns(context, googleMap),
                new BucksCarParks(context, googleMap),
                new BucksEvents(context, googleMap),
                new BucksTrafficFlows(context, googleMap),
                new BucksTrafficQueues(context, googleMap),
                new BucksTrafficScoots(context, googleMap),
                new BucksTrafficSpeeds(context, googleMap),
                new BucksTrafficTravelTimes(context, googleMap),
                new BucksRoadworks(context, googleMap),
                new HertsVariableMessageSigns(context, googleMap),
                new HertsCarParks(context, googleMap),
                new HertsEvents(context, googleMap),
                new HertsTrafficFlows(context, googleMap),
                new HertsTrafficScoots(context, googleMap),
                new HertsTrafficSpeeds(context, googleMap),
                new HertsTrafficTravelTimes(context, googleMap),
                new HertsRoadworks(context, googleMap),
                new NorthantsVariableMessageSigns(context,googleMap),
                new NorthantsCarParks(context,googleMap),
                new NorthantsTrafficFlows(context,googleMap),
                new NorthantsTrafficTravelTimes(context,googleMap),
                new NorthantsRoadworks(context,googleMap),
                new OxonVariableMessageSigns(context, googleMap),
                new OxonCarParks(context, googleMap),
                new OxonEvents(context, googleMap),
                new OxonTrafficFlows(context, googleMap),
                new OxonTrafficQueues(context, googleMap),
                new OxonTrafficScoots(context, googleMap),
                new OxonTrafficSpeeds(context, googleMap),
                new OxonTrafficTravelTimes(context, googleMap),
                new OxonRoadworks(context, googleMap),
//                new Fastprk(context, googleMap),
                new ClearviewSilverstone(context, googleMap),
                new BitCarrierSilverstoneNodes(context, googleMap),
                new BitCarrierSilverstone(context, googleMap)
        };
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
            } else if (layer instanceof PolylineBaseLayer) {
                view = ((PolylineBaseLayer) layer).getInfoWindow(marker);
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
            } else if (layer instanceof PolylineBaseLayer) {
                view = ((PolylineBaseLayer) layer).getInfoContents(marker);
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
            case R.id.bucks_vms_checkbox:
                layers[BUCKS_VMS].setVisible(checked);
                break;
            case R.id.bucks_car_park_checkbox:
                layers[BUCKS_CAR_PARK].setVisible(checked);
                break;
            case R.id.bucks_event_checkbox:
                layers[BUCKS_EVENT].setVisible(checked);
                break;
            case R.id.bucks_traffic_flow_checkbox:
                layers[BUCKS_TRAFFIC_FLOW].setVisible(checked);
                break;
            case R.id.bucks_traffic_queue_checkbox:
                layers[BUCKS_TRAFFIC_QUEUE].setVisible(checked);
                break;
            case R.id.bucks_traffic_scoot_checkbox:
                layers[BUCKS_TRAFFIC_SCOOT].setVisible(checked);
                break;
            case R.id.bucks_traffic_speed_checkbox:
                layers[BUCKS_TRAFFIC_SPEED].setVisible(checked);
                break;
            case R.id.bucks_traffic_travel_time_checkbox:
                layers[BUCKS_TRAFFIC_TRAVEL_TIME].setVisible(checked);
                break;
            case R.id.bucks_roadworks_checkbox:
                layers[BUCKS_ROADWORKS].setVisible(checked);
                break;
            case R.id.herts_vms_checkbox:
                layers[HERTS_VMS].setVisible(checked);
                break;
            case R.id.herts_car_park_checkbox:
                layers[HERTS_CAR_PARK].setVisible(checked);
                break;
            case R.id.herts_event_checkbox:
                layers[HERTS_EVENT].setVisible(checked);
                break;
            case R.id.herts_traffic_flow_checkbox:
                layers[HERTS_TRAFFIC_FLOW].setVisible(checked);
                break;
            case R.id.herts_traffic_scoot_checkbox:
                layers[HERTS_TRAFFIC_SCOOT].setVisible(checked);
                break;
            case R.id.herts_traffic_speed_checkbox:
                layers[HERTS_TRAFFIC_SPEED].setVisible(checked);
                break;
            case R.id.herts_traffic_travel_time_checkbox:
                layers[HERTS_TRAFFIC_TRAVEL_TIME].setVisible(checked);
                break;
            case R.id.herts_roadworks_checkbox:
                layers[HERTS_ROADWORKS].setVisible(checked);
                break;
            case R.id.northants_vms_checkbox:
                layers[NORTHANTS_VMS].setVisible(checked);
                break;
            case R.id.northants_car_park_checkbox:
                layers[NORTHANTS_CAR_PARK].setVisible(checked);
                break;
            case R.id.northants_traffic_flow_checkbox:
                layers[NORTHANTS_TRAFFIC_FLOW].setVisible(checked);
                break;
            case R.id.northants_traffic_travel_time_checkbox:
                layers[NORTHANTS_TRAFFIC_TRAVEL_TIME].setVisible(checked);
                break;
            case R.id.northants_roadworks_checkbox:
                layers[NORTHANTS_ROADWORKS].setVisible(checked);
                break;
            case R.id.oxon_vms_checkbox:
                layers[OXON_VMS].setVisible(checked);
                break;
            case R.id.oxon_car_park_checkbox:
                layers[OXON_CAR_PARK].setVisible(checked);
                break;
            case R.id.oxon_event_checkbox:
                layers[OXON_EVENT].setVisible(checked);
                break;
            case R.id.oxon_traffic_flow_checkbox:
                layers[OXON_TRAFFIC_FLOW].setVisible(checked);
                break;
            case R.id.oxon_traffic_queue_checkbox:
                layers[OXON_TRAFFIC_QUEUE].setVisible(checked);
                break;
            case R.id.oxon_traffic_scoot_checkbox:
                layers[OXON_TRAFFIC_SCOOT].setVisible(checked);
                break;
            case R.id.oxon_traffic_speed_checkbox:
                layers[OXON_TRAFFIC_SPEED].setVisible(checked);
                break;
            case R.id.oxon_traffic_travel_time_checkbox:
                layers[OXON_TRAFFIC_TRAVEL_TIME].setVisible(checked);
                break;
            case R.id.oxon_roadworks_checkbox:
                layers[OXON_ROADWORKS].setVisible(checked);
                break;
            case R.id.fastprk_checkbox:
                layers[FASTPRK].setVisible(checked);
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
            } else if (layer instanceof PolylineBaseLayer) {
                if (((PolylineBaseLayer) layer).onMarkerClick(marker)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (layers[FASTPRK] != null) {
//            ((Fastprk) layers[FASTPRK]).startUpdateTimer();
//        }
        getContentResolver().registerContentObserver(LastUpdatedProviderModule.LAST_UPDATED_URI,
                false, itemObserver);
    }

    @Override
    protected void onPause() {
        getContentResolver().unregisterContentObserver(itemObserver);
//        if (layers[FASTPRK] != null) {
//            ((Fastprk) layers[FASTPRK]).stopUpdateTimer();
//        }
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
        bucksVmsCheckbox = (CheckBox) findViewById(R.id.bucks_vms_checkbox);
        bucksCarParkCheckbox = (CheckBox) findViewById(R.id.bucks_car_park_checkbox);
        bucksEventCheckbox = (CheckBox) findViewById(R.id.bucks_event_checkbox);
        bucksTrafficFlowCheckBox = (CheckBox) findViewById(R.id.bucks_traffic_flow_checkbox);
        bucksTrafficQueueCheckBox = (CheckBox) findViewById(R.id.bucks_traffic_queue_checkbox);
        bucksTrafficScootCheckBox = (CheckBox) findViewById(R.id.bucks_traffic_scoot_checkbox);
        bucksTrafficSpeedCheckBox = (CheckBox) findViewById(R.id.bucks_traffic_speed_checkbox);
        bucksTrafficTravelTimeCheckBox = (CheckBox) findViewById(R.id.bucks_traffic_travel_time_checkbox);
        bucksRoadWorksCheckBox = (CheckBox) findViewById(R.id.bucks_roadworks_checkbox);
        hertsVmsCheckbox = (CheckBox) findViewById(R.id.herts_vms_checkbox);
        hertsCarParkCheckbox = (CheckBox) findViewById(R.id.herts_car_park_checkbox);
        hertsEventCheckbox = (CheckBox) findViewById(R.id.herts_event_checkbox);
        hertsTrafficFlowCheckBox = (CheckBox) findViewById(R.id.herts_traffic_flow_checkbox);
        hertsTrafficScootCheckBox = (CheckBox) findViewById(R.id.herts_traffic_scoot_checkbox);
        hertsTrafficSpeedCheckBox = (CheckBox) findViewById(R.id.herts_traffic_speed_checkbox);
        hertsTrafficTravelTimeCheckBox = (CheckBox) findViewById(R.id.herts_traffic_travel_time_checkbox);
        hertsRoadWorksCheckBox = (CheckBox) findViewById(R.id.herts_roadworks_checkbox);
        northantsVmsCheckbox = (CheckBox) findViewById(R.id.northants_vms_checkbox);
        northantsCarParkCheckbox = (CheckBox) findViewById(R.id.northants_car_park_checkbox);
        northantsTrafficFlowCheckBox = (CheckBox) findViewById(R.id.northants_traffic_flow_checkbox);
        northantsTrafficTravelTimeCheckBox = (CheckBox) findViewById(R.id.northants_traffic_travel_time_checkbox);
        northantsRoadWorksCheckBox = (CheckBox) findViewById(R.id.northants_roadworks_checkbox);
        oxonVmsCheckbox = (CheckBox) findViewById(R.id.oxon_vms_checkbox);
        oxonCarParkCheckbox = (CheckBox) findViewById(R.id.oxon_car_park_checkbox);
        oxonEventCheckbox = (CheckBox) findViewById(R.id.oxon_event_checkbox);
        oxonTrafficFlowCheckBox = (CheckBox) findViewById(R.id.oxon_traffic_flow_checkbox);
        oxonTrafficQueueCheckBox = (CheckBox) findViewById(R.id.oxon_traffic_queue_checkbox);
        oxonTrafficScootCheckBox = (CheckBox) findViewById(R.id.oxon_traffic_scoot_checkbox);
        oxonTrafficSpeedCheckBox = (CheckBox) findViewById(R.id.oxon_traffic_speed_checkbox);
        oxonTrafficTravelTimeCheckBox = (CheckBox) findViewById(R.id.oxon_traffic_travel_time_checkbox);
        oxonRoadWorksCheckBox = (CheckBox) findViewById(R.id.oxon_roadworks_checkbox);
        fastprkCheckBox = (CheckBox) findViewById(R.id.fastprk_checkbox);
        clearviewCheckBox = (CheckBox) findViewById(R.id.clearview_checkbox);
        bitcarrierCheckBox = (CheckBox) findViewById(R.id.bitcarrier_checkbox);
        bucksVmsCheckbox.setOnCheckedChangeListener(this);
        bucksCarParkCheckbox.setOnCheckedChangeListener(this);
        bucksEventCheckbox.setOnCheckedChangeListener(this);
        bucksTrafficFlowCheckBox.setOnCheckedChangeListener(this);
        bucksTrafficQueueCheckBox.setOnCheckedChangeListener(this);
        bucksTrafficScootCheckBox.setOnCheckedChangeListener(this);
        bucksTrafficSpeedCheckBox.setOnCheckedChangeListener(this);
        bucksTrafficTravelTimeCheckBox.setOnCheckedChangeListener(this);
        bucksRoadWorksCheckBox.setOnCheckedChangeListener(this);
        hertsVmsCheckbox.setOnCheckedChangeListener(this);
        hertsCarParkCheckbox.setOnCheckedChangeListener(this);
        hertsEventCheckbox.setOnCheckedChangeListener(this);
        hertsTrafficFlowCheckBox.setOnCheckedChangeListener(this);
        hertsTrafficScootCheckBox.setOnCheckedChangeListener(this);
        hertsTrafficSpeedCheckBox.setOnCheckedChangeListener(this);
        hertsTrafficTravelTimeCheckBox.setOnCheckedChangeListener(this);
        hertsRoadWorksCheckBox.setOnCheckedChangeListener(this);
        northantsVmsCheckbox.setOnCheckedChangeListener(this);
        northantsCarParkCheckbox.setOnCheckedChangeListener(this);
        northantsTrafficFlowCheckBox.setOnCheckedChangeListener(this);
        northantsTrafficTravelTimeCheckBox.setOnCheckedChangeListener(this);
        northantsRoadWorksCheckBox.setOnCheckedChangeListener(this);
        oxonVmsCheckbox.setOnCheckedChangeListener(this);
        oxonCarParkCheckbox.setOnCheckedChangeListener(this);
        oxonEventCheckbox.setOnCheckedChangeListener(this);
        oxonTrafficFlowCheckBox.setOnCheckedChangeListener(this);
        oxonTrafficQueueCheckBox.setOnCheckedChangeListener(this);
        oxonTrafficScootCheckBox.setOnCheckedChangeListener(this);
        oxonTrafficSpeedCheckBox.setOnCheckedChangeListener(this);
        oxonTrafficTravelTimeCheckBox.setOnCheckedChangeListener(this);
        oxonRoadWorksCheckBox.setOnCheckedChangeListener(this);
        fastprkCheckBox.setOnCheckedChangeListener(this);
        clearviewCheckBox.setOnCheckedChangeListener(this);
        bitcarrierCheckBox.setOnCheckedChangeListener(this);
    }
}


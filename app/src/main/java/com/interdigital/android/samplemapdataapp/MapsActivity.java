package com.interdigital.android.samplemapdataapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.interdigital.android.samplemapdataapp.cluster.CarParkClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.CarParkClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.RoadWorksClusterRenderer;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterManager;
import com.interdigital.android.samplemapdataapp.cluster.VmsClusterRenderer;
import com.interdigital.android.samplemapdataapp.json.items.Item;

import net.uk.onetransport.android.county.bucks.authentication.CredentialHelper;
import net.uk.onetransport.android.county.bucks.provider.BucksProvider;
import net.uk.onetransport.android.county.bucks.sync.BucksSyncAdapter;

import java.util.HashSet;
import java.util.UUID;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter, Handler.Callback,
        DougalCallback, CompoundButton.OnCheckedChangeListener, GoogleMap.OnCameraChangeListener,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapsActivity";
    private static final int MSG_SET_PLEASE_UPDATE = 1;

    private Context context;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    // Needed for quick look-up.
//    private HashMap<Marker, Item> markerMap = new HashMap<>();
    private Handler handler = new Handler(this);
    private String installationId;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CheckBox vmsCheckbox;
    private CheckBox carParkCheckbox;
    private CheckBox trafficFlowCheckBox;
    private CheckBox roadWorksCheckBox;
    private int numberUpdated;
    private ItemObserver itemObserver;
    private VmsClusterManager vmsClusterManager;
    private VmsClusterRenderer vmsClusterRenderer;
    private RoadWorksClusterManager roadWorksClusterManager;
    private RoadWorksClusterRenderer roadWorksClusterRenderer;
    private CarParkClusterManager carParkClusterManager;
    private CarParkClusterRenderer carParkClusterRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                BucksSyncAdapter.refresh(context, vmsCheckbox.isChecked(), carParkCheckbox.isChecked(),
                        trafficFlowCheckBox.isChecked(), roadWorksCheckBox.isChecked());
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
        CredentialHelper.initialiseCredentials(context, getString(R.string.pref_default_user_name),
                getString(R.string.pref_default_password), installationId);
        loadMarkers(true);
    }

    public void loadMarkers(boolean moveMap) {
        HashSet<Integer> visibleTypes = new HashSet<>();
        if (vmsCheckbox.isChecked()) {
            visibleTypes.add(Item.TYPE_VMS);
        }
        if (carParkCheckbox.isChecked()) {
            visibleTypes.add(Item.TYPE_CAR_PARK);
        }
        if (trafficFlowCheckBox.isChecked()) {
            visibleTypes.add(Item.TYPE_TRAFFIC_FLOW);
        }
        if (roadWorksCheckBox.isChecked()) {
            visibleTypes.add(Item.TYPE_ROAD_WORKS);
        }
        // The cluster manager doesn't seem to like being cleared and restarted.
        if (vmsClusterManager != null) {
            for (Marker marker : vmsClusterManager.getMarkerCollection().getMarkers()) {
                marker.remove();
            }
            for (Marker marker : vmsClusterManager.getClusterMarkerCollection().getMarkers()) {
                marker.remove();
            }
            vmsClusterManager.clearItems();
        }
        vmsClusterManager = new VmsClusterManager(context, googleMap);
        vmsClusterRenderer = new VmsClusterRenderer(context, googleMap, vmsClusterManager);

        if (roadWorksClusterManager != null) {
            for (Marker marker : roadWorksClusterManager.getMarkerCollection().getMarkers()) {
                marker.remove();
            }
            for (Marker marker : roadWorksClusterManager.getClusterMarkerCollection().getMarkers()) {
                marker.remove();
            }
            roadWorksClusterManager.clearItems();
        }
        roadWorksClusterManager = new RoadWorksClusterManager(context, googleMap);
        roadWorksClusterRenderer = new RoadWorksClusterRenderer(context, googleMap,
                roadWorksClusterManager);

        if (carParkClusterManager != null) {
            for (Marker marker : carParkClusterManager.getMarkerCollection().getMarkers()) {
                marker.remove();
            }
            for (Marker marker : carParkClusterManager.getClusterMarkerCollection().getMarkers()) {
                marker.remove();
            }
            carParkClusterManager.clearItems();
        }
        carParkClusterManager = new CarParkClusterManager(context, googleMap);
        carParkClusterRenderer = new CarParkClusterRenderer(context, googleMap,
                carParkClusterManager);
        // TODO
        new LoadMarkerTask(googleMap, (ProgressBar) findViewById(R.id.progress_bar),
                moveMap, this, visibleTypes, vmsClusterManager, vmsClusterRenderer,
                roadWorksClusterManager, roadWorksClusterRenderer,
                carParkClusterManager, carParkClusterRenderer).execute();
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_SET_PLEASE_UPDATE:
                updateAll();
                break;
        }
        return false;
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
        if (vmsClusterRenderer.getClusterItem(marker) != null) {
            return vmsClusterManager.getMarkerManager().getInfoWindow(marker);
        }
        if (roadWorksClusterRenderer.getClusterItem(marker) != null) {
            return roadWorksClusterManager.getMarkerManager().getInfoWindow(marker);
        }
        if (carParkClusterRenderer.getClusterItem(marker) != null) {
            return carParkClusterManager.getMarkerManager().getInfoWindow(marker);
        }
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (vmsClusterRenderer.getClusterItem(marker) != null) {
            return vmsClusterManager.getMarkerManager().getInfoContents(marker);
        }
        if (roadWorksClusterRenderer.getClusterItem(marker) != null) {
            return roadWorksClusterManager.getMarkerManager().getInfoContents(marker);
        }
        if (carParkClusterRenderer.getClusterItem(marker) != null) {
            return carParkClusterManager.getMarkerManager().getInfoContents(marker);
        }
        return null;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        switch (compoundButton.getId()) {
            case R.id.vms_checkbox:
                for (Marker marker : vmsClusterManager.getMarkerCollection().getMarkers()) {
                    marker.setVisible(checked);
                }
                break;
            case R.id.car_park_checkbox:
                if (checked) {
                    setItemVisible(Item.TYPE_CAR_PARK, true);
                } else {
                    setItemVisible(Item.TYPE_CAR_PARK, false);
                }
                break;
            case R.id.traffic_flow_checkbox:
                if (checked) {
                    setItemVisible(Item.TYPE_TRAFFIC_FLOW, true);
                } else {
                    setItemVisible(Item.TYPE_TRAFFIC_FLOW, false);
                }
                break;
            case R.id.road_works_checkbox:
                roadWorksClusterRenderer.setVisible(checked);
                break;
        }
    }

    public void updateCompleted() {
        numberUpdated++;
        if (numberUpdated == 6) {
            startUpdateTimer();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (vmsClusterManager != null) {
            vmsClusterManager.onCameraChange(cameraPosition);
        }
        if (roadWorksClusterManager != null) {
            roadWorksClusterManager.onCameraChange(cameraPosition);
        }
        if (carParkClusterManager != null) {
            carParkClusterManager.onCameraChange(cameraPosition);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (vmsClusterManager != null && (vmsClusterRenderer.getClusterItem(marker) != null
                || vmsClusterRenderer.getCluster(marker) != null)) {
            return vmsClusterManager.onMarkerClick(marker);
        }
        if (roadWorksClusterManager != null
                && (roadWorksClusterRenderer.getClusterItem(marker) != null
                || roadWorksClusterRenderer.getCluster(marker) != null)) {
            return roadWorksClusterManager.onMarkerClick(marker);
        }
        if (carParkClusterManager != null
                && (carParkClusterRenderer.getClusterItem(marker) != null
                || carParkClusterRenderer.getCluster(marker) != null)) {
            return carParkClusterManager.onMarkerClick(marker);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startUpdateTimer();
        getContentResolver().registerContentObserver(BucksProvider.LAST_UPDATED_URI, false,
                itemObserver);
    }

    @Override
    protected void onPause() {
        getContentResolver().unregisterContentObserver(itemObserver);
        stopUpdateTimer();
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
        vmsCheckbox.setOnCheckedChangeListener(this);
        carParkCheckbox.setOnCheckedChangeListener(this);
        trafficFlowCheckBox.setOnCheckedChangeListener(this);
        roadWorksCheckBox.setOnCheckedChangeListener(this);
    }

    private void updateAll() {
        numberUpdated = 0;
        // TODO    Get a new plan for Worldsensing updates.
//        for (Map.Entry<Marker, Item> entry : markerMap.entrySet()) {
//            Item item = entry.getValue();
//            if (item instanceof WorldsensingItem) {
//                ((WorldsensingItem) item).update();
//            }
//        }
    }

    private void setItemVisible(@Item.Type int type, boolean visible) {
//        for (Map.Entry<Marker, Item> entry : markerMap.entrySet()) {
//            if (entry.getValue().getType() == type) {
//                entry.getKey().setVisible(visible);
//            }
//        }
    }

    private void startUpdateTimer() {
        if (!handler.hasMessages(MSG_SET_PLEASE_UPDATE)) {
            handler.sendEmptyMessageDelayed(MSG_SET_PLEASE_UPDATE, 15000L);
        }
    }

    private void stopUpdateTimer() {
        handler.removeMessages(MSG_SET_PLEASE_UPDATE);
    }

}


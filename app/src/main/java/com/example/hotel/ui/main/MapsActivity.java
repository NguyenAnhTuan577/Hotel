package com.example.hotel.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.HotelsNearby;
import com.example.hotel.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    private static final String TAG = "MapsActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int REQUEST_CODE = 9999;
    private static final float DEFAULT_ZOOM = 15f;


    private EditText mSearchBox;
    private View mapView;


    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastLocation = new Location("default location");
    Marker searchMarker, myMarker;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mSearchBox = (EditText) findViewById(R.id.txtSearch);
        mLastLocation.setLatitude(10.762622);
        mLastLocation.setLongitude(106.660172);

        getLocationPermission();
        initSearchBox();
    }

    private void initSearchBox(){
        Log.d(TAG,"init: initializing");

        mSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        ||event.getAction() == KeyEvent.ACTION_DOWN
                        ||event.getAction() == KeyEvent.KEYCODE_ENTER){
                    geoLocate();

                }
                return false;
            }
        });
        hideKeyboard();
    }

    private void geoLocate(){
        Log.d(TAG,"geoLocate: geolocating");
        String str = mSearchBox.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(str,1);
        } catch (IOException e) {
            Log.d(TAG,"geoLocate: IOException: " + e.getMessage());
        }

        if(list.size()>0){
            Address address = list.get(0);
            Log.d(TAG,"geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }
    }

    private void initMap() {
        Log.d(TAG,"initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        mapView = mapFragment.getView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"onMapReady: map is ready");
        mMap = googleMap;
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(statusOfGPS){
            if (mLocationPermissionGranted) {
                getDeviceLocation();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(false);
                if (mapView != null) {

                    View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams)
                            locationButton.getLayoutParams();
                    layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                    layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                    layoutParams1.setMargins(0, 150, 30, 0);

                    View zoomControls = mapView.findViewById(0x1);
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) zoomControls.getLayoutParams();

                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    layoutParams2.setMargins(0, 0, 30, 100);
                }
            }
        }
        else {
            mLastLocation = null;
            Toast.makeText(MapsActivity.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG,"getDeviceLocation: get current device location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"onComplete: location was found");
                            mLastLocation = (Location)task.getResult();
                            moveCamera(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),DEFAULT_ZOOM,"My location");
                        }else {
                            Log.d(TAG,"onComplete: location is null");
                            Toast.makeText(MapsActivity.this,"Can not get current location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.d(TAG,"getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    public void findHotels(View v) {


        if ( mLastLocation != null) {
            StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            stringBuilder.append("location=").append(mLastLocation.getLatitude()).append(",").append(mLastLocation.getLongitude());
            stringBuilder.append("&radius=" + 5000);
            stringBuilder.append("&keyword=" + "hotel");
            stringBuilder.append("&key=").append(getResources().getString(R.string.map_key));

            String url = stringBuilder.toString();

            Object dataTransfer[] = new Object[2];
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            HotelsNearby hotelsNearby = new HotelsNearby(this);
            hotelsNearby.execute(dataTransfer);

        } else {
            Toast.makeText(MapsActivity.this, "Can not find current location", Toast.LENGTH_SHORT).show();
        }


    }

    private void moveCamera(LatLng latLng, float zoom,String title){
        Log.d(TAG,"moveCamera: moving camera to: " + latLng.latitude + "," + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        if(!title.equals("My location")){
            if(searchMarker != null){
                searchMarker.remove();
            }
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            searchMarker = mMap.addMarker(options);
        }
        else{
            if(myMarker != null){
                myMarker.remove();
            }
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            myMarker = mMap.addMarker(options);
        }
        hideKeyboard();
    }

    private void getLocationPermission(){
        Log.d(TAG,"getLocationPermission: getting location permission");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }
            else {
                ActivityCompat.requestPermissions(this,permission,REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,permission,REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"onRequestPermissionsResult: called");
        mLocationPermissionGranted = false;

        switch (requestCode){
            case REQUEST_CODE:{
                if (grantResults.length > 0){
                    for (int i = 0; i< grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG,"onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG,"onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted =true;
                    initMap();
                }
            }
        }
    }

    private void hideKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onLocationChanged(final Location location) {
        mLastLocation = location;
    }
}

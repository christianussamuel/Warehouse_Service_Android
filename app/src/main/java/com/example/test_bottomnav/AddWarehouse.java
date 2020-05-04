package com.example.test_bottomnav;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class AddWarehouse extends FragmentActivity implements OnMapReadyCallback{
    private Location mCurrentLocation;
    private LatLng latLngBiru = new LatLng(-6.8899, 107.6100);
    private LatLng latLng1 = new LatLng(-6.9899, 107.7100);
    private LatLng latLng2 = new LatLng(-6.5899, 107.6100);
    private LatLng latLng3 = new LatLng(-6.8399, 107.5100);
    MarkerOptions markerOptions = new MarkerOptions();
    FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mMapFragment;
    private ResultReceiver resultReceiver;
    private TextView warehouseName, warehouseAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        resultReceiver = new AddressResultReceiver(new Handler());

        warehouseName = findViewById(R.id.warehouseName);
        warehouseAddress = findViewById(R.id.warehouseAddress);

        getCurrentLocation();
    }

    private void getCurrentLocation() {
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    mCurrentLocation = location;
                    mMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
                    mMapFragment.getMapAsync(AddWarehouse.this);
                }
            }
        });

    }

    public void onMapReady(GoogleMap googleMap) {
        int height = 100;
        int width = 88;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.customlocation4);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallerMarker = Bitmap.createScaledBitmap(b, width, height, false);

        LatLng mLatLng = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
        Location location = new Location("providerNA");
        location.setLatitude(mCurrentLocation.getLatitude());
        location.setLongitude(mCurrentLocation.getLongitude());
        fetchAddressFromLatLong(location);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(mLatLng)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromBitmap(smallerMarker));
        googleMap.addMarker(markerOptions);

        googleMap.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Warehouse 1").snippet("Seafod"));

        googleMap.addMarker(new MarkerOptions()
                .position(latLng1)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Warehouse 2").snippet("Seafod"));

        googleMap.addMarker(new MarkerOptions()
                .position(latLng2)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Warehouse 3").snippet("Seafod"));

        googleMap.addMarker(new MarkerOptions()
                .position(latLng3)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Warehouse 4").snippet("Seafod"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 10));
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                double latitude = marker.getPosition().latitude;
                double longitude = marker.getPosition().longitude;

                Location location = new Location("providerNA");
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                fetchAddressFromLatLong(location);
            }
        });
    }

    private void fetchAddressFromLatLong(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode == Constants.SUCCESS_RESULT) {
                warehouseAddress.setText(resultData.getString(Constants.RESULT_DATA_KEY));
            } else {
                Toast.makeText(AddWarehouse.this, resultData.getString(Constants.RESULT_DATA_KEY),Toast.LENGTH_SHORT).show();
            }
        }
    }
}


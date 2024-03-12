package com.example.project1752.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project1752.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapActivity.class.getSimpleName();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtener la ubicación actual (asumiendo que ya la tienes)
        currentLocation = new LatLng(40.7128, -74.0060); // Latitud y longitud de Nueva York (ejemplo)

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_container);
        mapFragment.getMapAsync(this);

        // Verificar y solicitar permisos si es necesario
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getLastLocation() {
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                // Aquí puedes usar la ubicación actual (location)
                if (location != null) {
                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    updateMap();
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG, "getLastLocation: SecurityException: " + e.getMessage());
        }
    }

    private void updateMap() {
        if (mMap != null && currentLocation != null) {
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Mi ubicación"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                // Permiso denegado, maneja esta situación
            }
        }
    }
}

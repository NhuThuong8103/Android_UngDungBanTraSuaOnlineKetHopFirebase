package com.example.milkteaappandroid.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.milkteaappandroid.Controller.MapController;
import com.example.milkteaappandroid.Model.GPSTracker;
import com.example.milkteaappandroid.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    MapController mapController;
    double latitude = 0, longitude = 0;
    Location vitrihientai;
    String duongdan;
    LocationManager locationManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        mapController = new MapController();
        latitude = getIntent().getDoubleExtra("latitudequan", 0);
        longitude = getIntent().getDoubleExtra("longitudequan", 0);

        GPSTracker gps = new GPSTracker(MapActivity.this);
        vitrihientai = new Location("");
        vitrihientai.setLatitude(gps.getLatitude());
        vitrihientai.setLongitude(gps.getLongitude());
        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + vitrihientai.getLatitude() + "," + vitrihientai.getLongitude() + "&destination=" + latitude + "," + longitude + "&key=AIzaSyCSNQCX6UYnoiq-BSoaHRdQvmPovWRQeSY";
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
    }
    @Override
    protected void onStart() {
        super.onStart();

        mapFragment= (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        if(mapFragment!=null){
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap=googleMap;

        googleMap.clear();

        LatLng latLng= new LatLng(vitrihientai.getLatitude(),vitrihientai.getLongitude());

        MarkerOptions markerOptions=new MarkerOptions();

        markerOptions.position(latLng);

        googleMap.addMarker(markerOptions);

        LatLng vitriquan= new LatLng(latitude,longitude);

        MarkerOptions markerOptionsQuan=new MarkerOptions();

        markerOptionsQuan.position(vitriquan);

        googleMap.addMarker(markerOptionsQuan);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);

        googleMap.moveCamera(cameraUpdate);

        mapController.HienThiDuongDenQuan(googleMap,duongdan);

    }


    @Override
    public void onLocationChanged(Location location) {
        if(location.getLatitude()!=vitrihientai.getLatitude() && location.getLongitude()!=vitrihientai.getLongitude()) {

            vitrihientai.setLatitude(location.getLatitude());

            vitrihientai.setLongitude(location.getLongitude());

            duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + vitrihientai.getLatitude() + "," + vitrihientai.getLongitude() + "&destination=" + latitude + "," + longitude + "&key=AIzaSyCSNQCX6UYnoiq-BSoaHRdQvmPovWRQeSY";

            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

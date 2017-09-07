package com.example.op_bit.weatherplanner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<LatLng> markerPoints;

    private ProgressDialog progressDialog;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private String providerName;
    private Criteria defaultCriteria;

    private double lat;
    private double lon;

    private String startAddress;
    private String endAddress;

    Button saveRoute;
    Button clearRoute;
    Spinner selectTravelSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initScreenControl();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!checkLocationPermission()) {
            requestLocationPermission();
        } else {
            setupLocation();
        }
    } // End onCreate.

    public void initScreenControl() {

        startAddress = "";
        endAddress = "";

        selectTravelSpinner = (Spinner) findViewById(R.id.spinnerRouteMethod);
        String[] options = {"Walking Route", "Bicycling Route", "Driving Route"};
        ArrayAdapter<String> selectTravelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        selectTravelSpinner.setAdapter(selectTravelAdapter);
        selectTravelSpinner.setOnItemSelectedListener(new SpinnerSelectedOption());
        selectTravelSpinner.setPrompt("--- Select A Transportation Method ---");

        saveRoute = (Button) findViewById(R.id.btnSaveRoute);
        clearRoute = (Button) findViewById(R.id.btnClearRoute);

        saveRoute.setOnClickListener(new SaveRouteClickHandler());
        clearRoute.setOnClickListener(new ClearRouteClickHandler());

        markerPoints = new ArrayList<>();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        defaultCriteria = new Criteria();
        providerName = locationManager.getBestProvider(defaultCriteria, false);
    }// End initScreenControl

    public void downloadWeather() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading Weather Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();

        final int progressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < progressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progressDialog.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
    } // End downloadWeather

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapClickListener(new MapClickListener());

        boolean locationPermissionOk = checkLocationPermission();

        if (locationPermissionOk) {

            Location currentLocation = locationManager.getLastKnownLocation(providerName);

            if (currentLocation != null) {

                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
            }
        }
    } // End onMapReady

    private class CustomLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            lat = location.getLatitude();
            lon = location.getLongitude();

            LatLng latLng = new LatLng(lat, lon);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } // End onLocationChanged

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }// End CustomLocationListener


    private boolean checkLocationPermission() {

        int fineLocationOk = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationOk = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (fineLocationOk != PackageManager.PERMISSION_GRANTED || coarseLocationOk != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }// End checkLocationPermission

    public void requestLocationPermission() {

        String[] permissionIWant = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, permissionIWant, 0);
    } // End requestLocationPermission

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            setupLocation();
        } else {
            Toast.makeText(this, "Sorry you didn't give Permission to use your Location", Toast.LENGTH_LONG).show();
        }
    }// End onRequestPermissionResult

    public void setupLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria defaultCriteria = new Criteria();
        providerName = locationManager.getBestProvider(defaultCriteria, false);
        checkLocationPermission();
        if (checkLocationPermission()) {
            locationManager.requestLocationUpdates(providerName, 500, 1, new CustomLocationListener());
        }
    }// End setupLocation

    private class MapClickListener implements GoogleMap.OnMapClickListener {
        @Override
        public void onMapClick(LatLng latLng) {

            if (markerPoints.size() > 1) {

                markerPoints.clear();
                mMap.clear();
            }

            markerPoints.add(latLng);
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);

            if (markerPoints.size() == 1) {

                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            } else if (markerPoints.size() == 2) {

                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }

            mMap.addMarker(options);
            if (markerPoints.size() >= 2) {

                AsyncDownloadRouteJSON downloadRouteJSON = new AsyncDownloadRouteJSON();
                String travelMethod = selectTravelSpinner.getSelectedItem().toString().toLowerCase();
                downloadRouteJSON.execute(travelMethod);
            }
        }// End onMapClick
    }// End MapClickListener

    class AsyncDownloadRouteJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String JSONString = null;

            try {
                LatLng origin = markerPoints.get(0);
                LatLng dest = markerPoints.get(1);

                String urlString = "https://maps.googleapis.com/maps/api/directions/json?" +
                        "origin=" + origin.latitude + "," + origin.longitude +
                        "&destination=" + dest.latitude + "," + dest.longitude +
                        "&mode=" + params[0] +
                        "&key=AIzaSyDXBDkFt31azT5JGdtsy2TUzYjo4c9GR0U";

                URL URLObject = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) URLObject.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((responseString = bufferedReader.readLine()) != null) {

                        stringBuilder = stringBuilder.append(responseString);
                    }
                    JSONString = stringBuilder.toString();
                }
            } catch (Exception e) {
                Toast.makeText(MapsActivity.this, "Oops, there was an error creating the route", Toast.LENGTH_LONG).show();
            }
            return JSONString;
        }// End doInBackground

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject data = new JSONObject(s);
                JSONArray routesArray = data.getJSONArray("routes");
                JSONObject firstRoute = routesArray.getJSONObject(0);
                JSONObject overviewPolyline = firstRoute.getJSONObject("overview_polyline");
                JSONArray legsObject = firstRoute.getJSONArray("legs");
                JSONObject firstLeg = legsObject.getJSONObject(0);
                startAddress = firstLeg.getString("start_address");
                endAddress = firstLeg.getString("end_address");
                String polyline = overviewPolyline.getString("points");
                List<LatLng> polyLine = PolyUtil.decode(polyline);

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(polyLine);
                polylineOptions.color(Color.parseColor("Purple"));
                polylineOptions.geodesic(true);
                polylineOptions.width(12);
                mMap.addPolyline(polylineOptions);

            } catch (Exception e) {

            }
        }// End onPostExecute
    }// End AsyncDownloadRouteJSON

    private class SaveRouteClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (markerPoints.size() == 2) {

                Intent saveRouteIntent = new Intent(MapsActivity.this, DisplayRouteAndWeatherActivity.class);
                saveRouteIntent.putExtra("markerPos", markerPoints);
                saveRouteIntent.putExtra("startAddress", startAddress);
                saveRouteIntent.putExtra("endAddress", endAddress);
                startActivity(saveRouteIntent);
                finish();

            } else {
                Toast.makeText(MapsActivity.this, "You must make a route", Toast.LENGTH_LONG).show();
            }
        }// End onClick
    }// end SaveRouteClickHandler

    private class ClearRouteClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            mMap.clear();
        }
    }// End ClearRouteClickHandler

    private class SpinnerSelectedOption implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}

package com.example.op_bit.weatherplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DisplayRouteAndWeatherActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private ArrayList<Weather> weatherList;
    private ArrayList<LatLng> latLngs;
    private Spinner dateSpinner;
    private ArrayAdapter spinnerDayAdapter;
    private Button changeRoute;
    private TextView displayTemperature;
    private TextView displayHumidity;
    private TextView displayPressure;
    private TextView displayWindSpeed;
    private TextView displayStartingPoint;
    private TextView displayEndingPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_route_and_weather);

        // Setup all the user screen controls
        initScreenControl();


        Bundle bundleRoute = getIntent().getExtras();
        latLngs = bundleRoute.getParcelableArrayList("markerPos");
        String start = bundleRoute.getString("startAddress");
        String end = bundleRoute.getString("endAddress");

        displayStartingPoint.setText(start);
        displayEndingPoint.setText(end);

        nowWeatherForeCast();

    }

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

    public void initScreenControl() {

        dateSpinner = (Spinner) findViewById(R.id.spinnerShowDate);
        dateSpinner.setOnItemSelectedListener(new spinnerClickHandler());

        changeRoute = (Button) findViewById(R.id.btnChangeRoute);
        changeRoute.setOnClickListener(new changeRouteClickHandler());

        displayStartingPoint = (TextView) findViewById(R.id.tvOutputStartPoint);
        displayEndingPoint = (TextView) findViewById(R.id.tvOutputEndPoint);
        displayTemperature = (TextView) findViewById(R.id.tvOutputTemp);
        displayHumidity = (TextView) findViewById(R.id.tvOutputHumidity);
        displayPressure = (TextView) findViewById(R.id.tvOutputPressure);
        displayWindSpeed = (TextView) findViewById(R.id.tvOutputWindSpeed);
    } // End initScreenControl

    public void nowWeatherForeCast() {

        AsyncAPIGetWeatherForecastNow weatherForecast = new AsyncAPIGetWeatherForecastNow();
        weatherForecast.execute();
    }

    // Find the weather conditions for the next 5 days.
    public void fiveDayWeatherForeCast() {

        AsyncAPIGetWeatherForecast weatherForecast = new AsyncAPIGetWeatherForecast();
        weatherForecast.execute();
    }

    // Display the weather to the screen.
    public void displayWeatherForeCast(Weather weatherConditions) {

        // Get the weather data and output it in a textview.
        displayTemperature.setText(String.valueOf(weatherConditions.getTemperature()));
        displayHumidity.setText(String.valueOf(weatherConditions.getHumidity()));
        displayPressure.setText(String.valueOf(weatherConditions.getPressure()));
        displayWindSpeed.setText(String.valueOf(weatherConditions.getWindSpeed()));
    }


    private class spinnerClickHandler implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            displayWeatherForeCast(weatherList.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class changeRouteClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Change activity to the map class
            Intent changeActivity = new Intent(DisplayRouteAndWeatherActivity.this, MapsActivity.class);
            startActivity(changeActivity);
        }
    }

    public class AsyncAPIGetWeatherForecast extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String JSONString = null;
            try {

                // Get the URL
                String url = "http://api.openweathermap.org/data/2.5/forecast?" +
                        "units=metric" +
                        "&lat=" + latLngs.get(0).latitude +
                        "&lon=" + latLngs.get(0).longitude +
                        "&APPID=aaa48196d89737e29933ccabb2514574";

                URL URLObject = new URL(url);
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
                Toast.makeText(DisplayRouteAndWeatherActivity.this, "Oops.. there was an error trying to find the location!", Toast.LENGTH_LONG).show();
            }
            return JSONString;
        }

        @Override
        protected void onPostExecute(String grabString) {
            try {
                JSONObject data = new JSONObject(grabString);
                JSONArray weatherJsonArray = data.getJSONArray("list");

                //weatherList = new ArrayList<Weather>();

                int numWeatherItems = weatherJsonArray.length();
                for (int w = 0; w < numWeatherItems; w++) {
                    JSONObject weatherDataObject = weatherJsonArray.getJSONObject(w);
                    long dt = weatherDataObject.getLong("dt");
                    JSONObject weatherData = weatherDataObject.getJSONObject("main");
                    JSONObject windData = weatherDataObject.getJSONObject("wind");
                    Weather weather = new Weather();
                    weather.setHumidity(weatherData.getDouble("humidity"));
                    weather.setPressure(weatherData.getDouble("pressure"));
                    weather.setTemperature(weatherData.getDouble("temp"));
                    weather.setWindSpeed(windData.getDouble("speed"));

                    String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(dt * 1000));
                    weather.setDate(date);
                    weatherList.add(weather);
                }

                spinnerDayAdapter = new ArrayAdapter(DisplayRouteAndWeatherActivity.this, R.layout.support_simple_spinner_dropdown_item, weatherList);
                dateSpinner.setAdapter(spinnerDayAdapter);
                displayWeatherForeCast(weatherList.get(0));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } // End onPostExecute
    } // End AsyncAPIGetWeatherForecast

    public class AsyncAPIGetWeatherForecastNow extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String JSONString = null;
            try {

                // Get the URL
                String url = "http://api.openweathermap.org/data/2.5/weather?" +
                        "units=metric" +
                        "&lat=" + latLngs.get(0).latitude +
                        "&lon=" + latLngs.get(0).longitude +
                        "&APPID=aaa48196d89737e29933ccabb2514574";

                URL URLObject = new URL(url);
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
                Toast.makeText(DisplayRouteAndWeatherActivity.this, "Oops.. there was an error trying to find the location!", Toast.LENGTH_LONG).show();
            }
            return JSONString;
        }

        @Override
        protected void onPostExecute(String grabString) {
            try {
                JSONObject data = new JSONObject(grabString);

                weatherList = new ArrayList<Weather>();

                long dt = data.getLong("dt");
                JSONObject weatherData = data.getJSONObject("main");
                JSONObject windData = data.getJSONObject("wind");
                Weather weather = new Weather();
                weather.setHumidity(weatherData.getDouble("humidity"));
                weather.setPressure(weatherData.getDouble("pressure"));
                weather.setTemperature(weatherData.getDouble("temp"));
                weather.setWindSpeed(windData.getDouble("speed"));

                String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(dt * 1000));
                weather.setDate(date);
                weatherList.add(weather);

                spinnerDayAdapter = new ArrayAdapter(DisplayRouteAndWeatherActivity.this, R.layout.support_simple_spinner_dropdown_item, weatherList);
                dateSpinner.setAdapter(spinnerDayAdapter);
                displayWeatherForeCast(weatherList.get(0));
                fiveDayWeatherForeCast();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


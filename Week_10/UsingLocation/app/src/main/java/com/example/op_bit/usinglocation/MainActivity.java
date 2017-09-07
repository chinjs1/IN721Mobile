package com.example.op_bit.usinglocation;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int MAX_LATITUDE = 90;
    private final int MAX_LONGITUDE = 180;

    double latitude;
    double longitude;
    String showCurrentCity;
    Bitmap cityImage;
    Random rand;

    Button btnTeleport;
    TextView tvLat;
    TextView tvLong;
    TextView tvCityText;
    ImageView ivCityImg;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressDialog();

        // Init
        latitude = 0;
        longitude = 0;
        rand = new Random();

        // Create the screen components
        tvLat = (TextView) findViewById(R.id.tvLatVal);
        tvLong = (TextView) findViewById(R.id.tvLongVal);
        tvCityText = (TextView) findViewById(R.id.tvCityVal);
        ivCityImg = (ImageView) findViewById(R.id.ivCityImg);
        btnTeleport = (Button) findViewById(R.id.btnTeleport);

        // Create the button click handler
        btnTeleport.setOnClickListener(new ButtonTeleportHandler());
    }

    private class ButtonTeleportHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            CalculateLatitude();
            CalculateLongitude();
            OutputAllInfo();

            // Grab the closest city
            AsyncAPIGetClosestCity asyncAPI = new AsyncAPIGetClosestCity();
            asyncAPI.execute();
        }
    }

    public double CalculateLatitude() {

        int max = rand.nextInt(180);
        double decimal = rand.nextDouble();
        double randLat = max + decimal - 90;

        return randLat;
    }

    public double CalculateLongitude() {

        int max = rand.nextInt(360);
        double decimal = rand.nextDouble();
        double randLong = max + decimal - 180;

        return randLong;
    }

    public void OutputAllInfo() {

        DecimalFormat df = new DecimalFormat("0.000");
        tvLat.setText(df.format(latitude));
        tvLong.setText(df.format(longitude));
        tvCityText.setText(showCurrentCity);
    }

    public void ProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Searching for the closest city");
    }

    public class AsyncAPIGetClosestCity extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String JSONString = null;
            try {
                // Get the geoplugin URL
                String url = "http://www.geoplugin.net/extras/location.gp" +
                        "?lat=" + latitude +
                        "&long=" + longitude +
                        "&format=json";

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
                Toast.makeText(MainActivity.this, "Oops.. there was an error trying to find the location!", Toast.LENGTH_LONG).show();
            }
            return JSONString;
        }

        // Runs before async begins
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String grabString) {
            try {
                if (!grabString.equals("[[]]")) {
                    progressDialog.dismiss();
                    JSONObject data = new JSONObject(grabString);
                    String placeName = data.getString("geoplugin_place");
                    String countryCode = data.getString("geoplugin_countryCode");
                    CalculateLatitude();
                    CalculateLongitude();
                    OutputAllInfo();
                    tvCityText.setText(placeName + ", " + countryCode);
                    AsyncAPIFlikr flikrAsync = new AsyncAPIFlikr();
                    flikrAsync.execute();

                } else {
                    tvCityText.setText("Error, could not find a location!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class AsyncAPIFlikr extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String JSONString = null;
            try {
                String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=eda41a123d459be0f85276d37290651e&format=json&nojsoncallback=1&text=" + params[0];
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
                Toast.makeText(MainActivity.this, "Oops, there was error finding the data", Toast.LENGTH_LONG).show();
            }
            return JSONString;
        }
        @Override
        protected void onPostExecute(String flikrJSON) {

            String imgURL = "";

            try {
                JSONObject data = new JSONObject(flikrJSON);
                JSONObject photos = data.getJSONObject("photos");
                JSONArray photoArray = photos.getJSONArray("photo");

                // Check to see if there is a photo to grab
                if (photoArray.length() != 0) {
                    JSONObject image = photoArray.getJSONObject(0);

                    String farmID = image.getString("farm");
                    String serverID = image.getString("server");
                    String imageID = image.getString("id");
                    String secretID = image.getString("secret");

                    imgURL = "https://farm" + farmID + ".staticflickr.com/" + serverID + "/" + imageID + "_" + secretID + ".jpg";
                    AsyncGetBitmap flikrImage = new AsyncGetBitmap();
                    flikrImage.execute(imgURL);

                }
            } catch (Exception e) {

                ivCityImg.setImageResource(R.drawable.noimagefound);
            }
        }
    }

    class AsyncGetBitmap extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap img = null;

            try {
                String imgUrl = params[0];
                URL URLObjectBitmap = new URL(imgUrl);
                HttpURLConnection connectionBitmap = (HttpURLConnection) URLObjectBitmap.openConnection();
                connectionBitmap.connect();
                int responseCode = connectionBitmap.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStreamBitmap = connectionBitmap.getInputStream();
                    img = BitmapFactory.decodeStream(inputStreamBitmap);
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Oops, there was an error loading the image!", Toast.LENGTH_LONG).show();
            }
            return img;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivCityImg.setImageBitmap(bitmap);
        }
    }
}

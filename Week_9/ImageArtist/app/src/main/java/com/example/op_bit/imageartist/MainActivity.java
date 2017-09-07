package com.example.op_bit.imageartist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnClickToShowArtist;
    ImageView artistImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the button and imageview.
        btnClickToShowArtist = (Button) findViewById(R.id.btnShowArtistImage);
        artistImage = (ImageView) findViewById(R.id.ivDisplayArtist);

        // Make a button handler.
        btnClickToShowArtist.setOnClickListener(new ShowArtistClickHandler());
    }

    class AsyncImageDownload extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... args) {

            Bitmap artistImage = null;

            try {
                String urlString = args[0];
                URL URLObject = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) URLObject.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {

                    // Setup the input stream
                    InputStream inputStream = connection.getInputStream();
                    artistImage = BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {

                Toast.makeText(MainActivity.this, "Oops, an error happened trying to load artist image", Toast.LENGTH_LONG).show();
            }
            return artistImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            artistImage.setImageBitmap(bitmap);
        }
    }

    class AsycAPIShowRawJSON extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String JSONString = null;
            try {
                String urlString = "http://ws.audioscrobbler.com/2.0/?method=chart.getTopArtists&api_key=58384a2141a4b9737eacb9d0989b8a8c&limit=1&format=json";
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
                Toast.makeText(MainActivity.this, "An Error accoured while trying to fetch the chat data", Toast.LENGTH_LONG).show();
            }
            return JSONString;
        }

        @Override
        protected void onPostExecute(String s) {
            String imageUrl = null;
            try {

                JSONObject object = new JSONObject(s);
                JSONObject artists = object.getJSONObject("artists");
                JSONArray artistArray = artists.getJSONArray("artist");
                JSONObject currentArtist = artistArray.getJSONObject(0);
                JSONArray imagesArray = currentArtist.getJSONArray("image");
                JSONObject image = imagesArray.getJSONObject(2);

                imageUrl = image.getString("#text");

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error processing the JSON", Toast.LENGTH_LONG).show();
            }
            AsyncImageDownload asyncTask = new AsyncImageDownload();
            asyncTask.execute(imageUrl);
        }
    }

    private class ShowArtistClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            AsycAPIShowRawJSON asyncTask = new AsycAPIShowRawJSON();
            asyncTask.execute();
        }
    }
}

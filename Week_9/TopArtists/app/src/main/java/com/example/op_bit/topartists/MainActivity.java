package com.example.op_bit.topartists;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnLoadTopArtists;
    ListView lvListArtists;
    ArrayList<String> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the button handler, listview and arraylist on load.
        btnLoadTopArtists = (Button) findViewById(R.id.btnLoadArtistList);
        btnLoadTopArtists.setOnClickListener(new buttonLoadArtistHandler());
        lvListArtists = (ListView) findViewById(R.id.lvShowTopArtists);
        artistList = new ArrayList<>();
    }

    class AsyncAPIShowRawJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String JSONstring = null;

            try {
                String urlString = "http://ws.audioscrobbler.com/2.0/?method=chart.getTopArtists&api_key=58384a2141a4b9737eacb9d0989b8a8c&limit=20&format=json";
                URL URLObject = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) URLObject.openConnection();
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {

                    Toast.makeText(getApplicationContext(), "Oops! error:" + responseCode, Toast.LENGTH_LONG).show();
                }

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String responseString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((responseString = bufferedReader.readLine()) != null) {

                    stringBuilder = stringBuilder.append(responseString);
                }
                JSONstring = stringBuilder.toString();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error getting JSON string from URL.", Toast.LENGTH_LONG).show();
            }
            return JSONstring;
        }

        protected void onPostExecute(String jsonData) {

            JSONArray artistObjectArray = null;
            try {

                JSONObject object = new JSONObject(jsonData);
                JSONObject artistsObject = object.getJSONObject("artists");
                artistObjectArray = object.getJSONArray("artist");

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "JSON failed to load data", Toast.LENGTH_LONG).show();
            }

            int nCount = artistObjectArray.length();
            for (int i = 0; i < nCount; i++) {

                try {

                    JSONObject currentArtist = artistObjectArray.getJSONObject(i);
                    String artistDescription = currentArtist.getString("name") + " : " + currentArtist.getInt("listeners");
                    artistList.add(artistDescription);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error finding the artist", Toast.LENGTH_LONG).show();
                }
            }

            lvListArtists.setAdapter(new ArtistArrayAdapter(MainActivity.this, R.layout.custom, artistList));
        }
    }

    private class buttonLoadArtistHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            AsyncAPIShowRawJSON asyncTask = new AsyncAPIShowRawJSON();
            asyncTask.execute();
        }
    }

    private class ArtistArrayAdapter extends ArrayAdapter<String> {
        public ArtistArrayAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Inflate the view
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View customView = inflater.inflate(R.layout.custom, parent, false);

            TextView tvArtist = (TextView) customView.findViewById(R.id.artistName);
            TextView tvCount = (TextView) customView.findViewById(R.id.artistCount);

            // Get the current artist from the list
            String currentArtist = getItem(position);

            // Pull the artist and the count from the string
            String artist = currentArtist.substring(0, currentArtist.indexOf(":"));
            String artistCount = currentArtist.substring(currentArtist.indexOf(":") + 1, currentArtist.length());

            tvArtist.setText(artist);
            tvCount.setText(artistCount);

            return customView;
        }
    }
}

package com.example.op_bit.supportusersearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Globals
    EditText searchBar;
    ListView displayArtists;
    Button btnSearch;
    ArrayList<String> similarArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the search, list, button and arraylist.
        searchBar = (EditText) findViewById(R.id.etSearchArtist);
        displayArtists = (ListView) findViewById(R.id.lvListArtists);
        btnSearch = (Button) findViewById(R.id.btnSearchArtist);
        similarArtist = new ArrayList<String>();

        //  Create the button click handler.
        btnSearch.setOnClickListener(new ButtonSearchClickHandler());
    }

    // This class is for handling downloads from web and setting the listview
    public class AsyncAPIShowRawJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... searchedArtist) {

            String JSONString = null;
            try {
                // URL from last.fm to get top 10 artists in a json.
                String urlString = "http://ws.audioscrobbler.com/2.0/?method=artist.getSimilar&api_key=58384a2141a4b9737eacb9d0989b8a8c&limit=10&format=json&artist=" + searchedArtist[0];

                // Convert the url into an object
                URL URLObject = new URL(urlString);

                // Create an http object
                HttpURLConnection connection = (HttpURLConnection)URLObject.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    // Setup the input stream and the buffered reader.
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    // Read the input into a string builder from the buffered reader.
                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((responseString = bufferedReader.readLine()) != null) {
                        stringBuilder = stringBuilder.append(responseString);
                    }
                    // Grab the string from the string builder
                    JSONString = stringBuilder.toString();
                }
            }
            catch (Exception e) {
                Toast.makeText(MainActivity.this, "Oops, you have encountered an error - trouble finding the artist list", Toast.LENGTH_LONG).show();
            }

            // Returns the json string.
            return JSONString;
        } // End doInBackground

        @Override
        protected void onPostExecute(String s) {
            similarArtist.clear();
            try {
                // Make the json object.
                JSONObject object = new JSONObject(s);

                // If the json matches similar artists,
                if (object.has("similarartists")) {

                    // Store the object and make an array.
                    JSONObject artists = object.getJSONObject("similarartists");
                    JSONArray artistArray = artists.getJSONArray("artist");

                    // Loop through the json array
                    int nbrResults = artistArray.length();
                    for (int i = 0; i < nbrResults; i++) {

                        JSONObject currentArtist = artistArray.getJSONObject(i);
                        String name = currentArtist.getString("name");
                        similarArtist.add(name);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "No Results matching that Artist", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error processing the JSON", Toast.LENGTH_LONG).show();
            }
           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, similarArtist);
           // displayArtists.setAdapter(adapter);

            // Set the listview to the adapter.
            displayArtists.setAdapter(new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, similarArtist));
        }
    } // End AsyncAPIShowRawJSON

    // The button handler that creates the AsyncTask and executes on click.
    private class ButtonSearchClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String searchQuery = searchBar.getText().toString();
            AsyncAPIShowRawJSON asyncTask = new AsyncAPIShowRawJSON();
            asyncTask.execute(searchQuery);
        }
    } // End ButtonClick
} // End MainActivity

package com.example.op_bit.jsonapp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Global variables
    String JSONInput;
    String description;
    ArrayList<String> eventsList;
    Button btnPopulateList;
    ListView lvEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the arraylist
        eventsList = new ArrayList<>();

        // Create the button handler
        btnPopulateList = (Button) findViewById(R.id.btnFillList);
        btnPopulateList.setOnClickListener(new fillListView());

        // Create the listview handler
        lvEvents = (ListView) findViewById(R.id.lvEvents);
        lvEvents.setOnItemClickListener(new selectListViewHandler());

    }

    private class fillListView implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // On button click, load the JSON and read in the file, load up the arraylist
            // and populate the listview.
            readJSON();
            loadArrayList();
            updateListView();
        }
    }

    private class selectListViewHandler implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String title = parent.getItemAtPosition(position).toString();
            getDescription(title);
            Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
        }
    }

    // Loop through the array and match the event description.
    public void getDescription(String title) {

        try {
            JSONObject data = new JSONObject(JSONInput);
            JSONObject events = data.getJSONObject("events");
            JSONArray eventArray = events.getJSONArray("event");

            int nObj = eventArray.length();

            for (int i = 0; i < nObj; i++) {

                JSONObject event = eventArray.getJSONObject(i);
                String objectTitle = event.getString("title");

                if  (objectTitle.equals(title)) {
                    description = event.getString("description");
                    break;
                }
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error, unable match description", Toast.LENGTH_LONG).show();
        }

    }

    public void readJSON() {

        String jsonFileName = "dunedin_events_2017.txt";
        try {

            AssetManager am = getAssets();
            InputStream inputStream = am.open(jsonFileName);

            int fileSizeInBytes = inputStream.available();
            byte[] JSONBuffer = new byte[fileSizeInBytes];

            inputStream.read(JSONBuffer);
            inputStream.close();

            JSONInput = new String(JSONBuffer);

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error, unable to read JSON", Toast.LENGTH_LONG).show();
        }
    } // End readJSON

    public void loadArrayList() {
        try {

            JSONObject data = new JSONObject(JSONInput);
            JSONObject events = data.getJSONObject("events");
            JSONArray eventArray = events.getJSONArray("event");

            int nObj = eventArray.length();

            for (int i = 0; i < nObj; i++) {

                JSONObject event = eventArray.getJSONObject(i);
                String title = event.getString("title");
                eventsList.add(title);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error, unable to load array list", Toast.LENGTH_LONG).show();
        }
    }

    public void updateListView() {

        ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventsList);
        lvEvents.setAdapter(titleAdapter);
    }


}

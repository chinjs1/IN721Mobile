package com.example.op_bit.sqlapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase cityDatabase;
    Spinner spinnerCountry;
    Button btnSearchDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityDatabase = openOrCreateDatabase("cityDatabase", MODE_PRIVATE, null);


        btnSearchDb = (Button) findViewById(R.id.btnSubmit);
        btnSearchDb.setOnClickListener(new displayListResults());
    }

    private class displayListResults implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            populateList();
        }
    }

    public void createTables() {

        // Create the db
        cityDatabase = openOrCreateDatabase("cityDatabase", MODE_PRIVATE, null);

        // Drop the table
        String dropQuery = "DROP TABLE tblCity";
        cityDatabase.execSQL(dropQuery);

        // Create the table
        String createQuery = "CREATE TABLE IF NOT EXISTS tblCity(" +
                             "cityID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                             "cityName TEXT NOT NULL, " +
                             "countryName TEXT NOT NULL);";
        cityDatabase.execSQL(createQuery);

    }

    public void insertData() {

        // Populate the db
        String record1 = "INSERT INTO tblCity VALUES(null,'Cleveland', 'United States')";
        String record2 = "INSERT INTO tblCity VALUES(null,'San Antonio', 'United States')";
        String record3 = "INSERT INTO tblCity VALUES(null,'Golden State', 'United States')";
        String record4 = "INSERT INTO tblCity VALUES(null,'Auckland', 'New Zealand')";
        String record5 = "INSERT INTO tblCity VALUES(null,'Wellington', 'New Zealand')";
        String record6 = "INSERT INTO tblCity VALUES(null,'Dunedin', 'New Zealand')";

        cityDatabase.execSQL(record1);
        cityDatabase.execSQL(record2);
        cityDatabase.execSQL(record3);
        cityDatabase.execSQL(record4);
        cityDatabase.execSQL(record5);
        cityDatabase.execSQL(record6);
    }

    public String[] returnCity(String country) {

        String selectQuery = "SELECT DISTINCT cityName FROM tblCity WHERE countryName = " + "\"" + country + "\"";
        Cursor recordSet = cityDatabase.rawQuery(selectQuery, null);

        int recordCount = recordSet.getCount();
        String[] displayStringArray = new String[recordCount];

        int cityNameIndex = recordSet.getColumnIndex("cityName");
        int countryNameIndex = recordSet.getColumnIndex("countryName");

        recordSet.moveToFirst();

        for(int r = 0; r < recordCount; r++)
        {
            String cityName = recordSet.getString(cityNameIndex);
            String countryName = recordSet.getString(countryNameIndex);
            displayStringArray[r] = cityName + ", " + countryName;

            recordSet.moveToNext();
        }
        return displayStringArray;
    }

    // Return the cities from the db
    public ArrayList<String> returnCountry() {

        String selectQuery = "SELECT DISTINCT countryName FROM tblCity";
        Cursor recordSet = cityDatabase.rawQuery(selectQuery, null);

        int recordCount = recordSet.getCount();
        ArrayList<String>  displayStringArray = new ArrayList<String>();

        int cityNameIndex = recordSet.getColumnIndex("cityName");
        int countryNameIndex = recordSet.getColumnIndex("countryName");

        recordSet.moveToFirst();

        for(int r = 0; r < recordCount; r++)
        {
            String cityName = recordSet.getString(cityNameIndex);
            String countryName = recordSet.getString(countryNameIndex);
            displayStringArray.add(countryName);

            recordSet.moveToNext();
        }
        return displayStringArray;
    }

    public void populateList() {

        Spinner selectedCountry = (Spinner) findViewById(R.id.spinnerCountry);
        ListView listView = (ListView) findViewById(R.id.lvCountries);
        String[] citiesArray = returnCity(selectedCountry.getSelectedItem().toString());
        ArrayAdapter<String> citiesArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, citiesArray);
        listView.setAdapter(citiesArrayAdapter);
    }

    public void populateSpinner() {

        ArrayList<String> countriesList = returnCountry();
        //Add the placeholder to show all cities
        countriesList.add(0, null);
        ArrayAdapter<String> countrySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countriesList);
        spinnerCountry.setAdapter(countrySpinnerAdapter);

    }


}

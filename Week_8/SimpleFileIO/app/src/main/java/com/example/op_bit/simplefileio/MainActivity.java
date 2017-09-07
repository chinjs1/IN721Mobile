package com.example.op_bit.simplefileio;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> players = LoadStringArray("players.txt");
        ListView lvPlayers = (ListView) findViewById(R.id.lvPlayers);

        ArrayAdapter playerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, players);
        lvPlayers.setAdapter(playerAdapter);
    }

    public ArrayList<String> LoadStringArray(String assetFileName) {

        ArrayList<String> stringHolder = new ArrayList<String>();
        AssetManager am = getAssets();

        try {
            InputStream is = am.open(assetFileName);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);

            String currentString;
            while ((currentString = br.readLine()) != null) {
                stringHolder.add(currentString);
            }
            br.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return stringHolder;
    }
}

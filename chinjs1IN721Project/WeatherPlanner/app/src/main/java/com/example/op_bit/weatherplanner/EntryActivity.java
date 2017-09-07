package com.example.op_bit.weatherplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

// This is the entry point for my app.
public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        // Create a layout view listener.
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.activity_entry);
        rLayout.setOnClickListener(new OnClickListener());
    }

    // Start the app on click..
    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Change activity to the map class
            Intent changeActivity = new Intent(EntryActivity.this, MapsActivity.class);
            startActivity(changeActivity);
            finish();
        }
    }
}
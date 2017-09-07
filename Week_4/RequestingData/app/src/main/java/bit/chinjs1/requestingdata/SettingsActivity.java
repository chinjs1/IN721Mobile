package bit.chinjs1.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView tvChange = (TextView) findViewById(R.id.tvPurpleText);

        // Get the color of the text current in this TextView and store it in a int var called color
        int color = tvChange.getCurrentTextColor();

        // Create the intent. No args req, this pops the Activity stack
        Intent returnIntent = new Intent();

        // Load up the return data
        returnIntent.putExtra("color",color);

        // Set the return code.
        setResult(Activity.RESULT_OK, returnIntent);

        // Remove yourself from the activity stack
        finish();

    }
} // End SettingsActivity

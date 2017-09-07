package bit.chinjs1.datapassing;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make the button clickable to go to the next activity
        Button goToSettings = (Button) findViewById(R.id.btnSettings);
        goToSettings.setOnClickListener(new GoToSettingsActivity());

        // Get the username from intent and set it to textview
        // Fetch the intent
        Intent getUsername = getIntent();

        // Retrieve the data via it's key
        String username = getUsername.getStringExtra("username");

        // Use the data
        TextView showUserName = (TextView) findViewById(R.id.tvUsername);
        showUserName.setText(username);

    }// End onCreate

    private class GoToSettingsActivity implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Make an intent to go from the main to the secondary activity
            Intent changeActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(changeActivity);
        } // End onClick
    } // End GoToSettingsActivity
} // End MainActivity

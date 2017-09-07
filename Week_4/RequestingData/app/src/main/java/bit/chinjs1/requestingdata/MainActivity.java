package bit.chinjs1.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView tvContentToChangeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the button obj
        Button btnChangeTextColor = (Button) findViewById(R.id.btnChangeColor);
        btnChangeTextColor.setOnClickListener(new GoToSettingsActivity());

        // Get a reference to TextView
       tvContentToChangeColor = (TextView) findViewById(R.id.tvChangeTextColor);

    } // End onCreate

    private class GoToSettingsActivity implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent changeActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);

            // The second arg is a code that allows the received to cope with multiple requests
            startActivityForResult(changeActivityIntent, 0);

        } // End onClick
    } // End GoToSetingsActivity

    //Retrieving the requested data
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == 0) && (resultCode == Activity.RESULT_OK)) {

            // Set colour of text
            int color = data.getIntExtra("color", 0);
            tvContentToChangeColor.setTextColor(color);

        } // End if
    } // End onActivityResult
} // End MainActivity

package bit.chinjs1.datapassing;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Create a clickable button that can go back to the main activity
        Button addUserButton = (Button) findViewById(R.id.btnGoToMain);
        addUserButton.setOnClickListener(new SettingsHandler());

    } // End onCreate

    private class SettingsHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Make the editText obj
            EditText etUserName = (EditText) findViewById(R.id.etUsername);

            // Store whatever the user types into username and convert it to a string
            String userName = etUserName.getText().toString();

            // If input is less than 3 characters long..
            if(userName.length() < 3) {
                // Output a toast
                Toast invalidInput = Toast.makeText(SettingsActivity.this,"Please enter a username longer than 3 characters" ,Toast.LENGTH_LONG);
                invalidInput.show();

            }else {

                // Change to the secondary activity
                Intent changeActivity = new Intent(SettingsActivity.this, MainActivity.class);

                // Change activity passing in the key and the value
                changeActivity.putExtra("username", userName);

                // Change activity
                startActivity(changeActivity);

            } // End else
        } // End onClick
    } // End SettingsHandler
} // End SettingsActivity

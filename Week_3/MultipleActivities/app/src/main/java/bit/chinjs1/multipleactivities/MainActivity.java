package bit.chinjs1.multipleactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the button object from A to B
        Button btnChangeAvtivity = (Button) findViewById(R.id.btnA);
        btnChangeAvtivity.setOnClickListener(new changeActivityButtonListener());
    }

    // Inner class for the mainActivity
    private class changeActivityButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Use intent to change to the next activity
            Intent changeActivityIntent = new Intent(MainActivity.this, ActivityB.class);
            startActivity(changeActivityIntent);
        }
    }

}

package bit.chinjs1.task1_eventhandlers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the button object
        Button buttonHandler = (Button) findViewById(R.id.buttonClick);

        // Button event handlers -
        // short click handler
        buttonHandler.setOnClickListener(new quickButtonClick());

        // long click handler
        buttonHandler.setOnLongClickListener(new longButtonClick());
}

    // Inner class for a quick button click
    private class quickButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Toast.makeText(MainActivity.this,"Short Click", Toast.LENGTH_LONG).show();
        }
    }

    // Inner class for a long button click
    private class longButtonClick implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {

            Toast.makeText(MainActivity.this,"Long Click", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}

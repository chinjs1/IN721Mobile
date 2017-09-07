package bit.chinjs1.task2_eventhandlers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the edit text obj
        EditText editTextHandler = (EditText) findViewById(R.id.editText);

         keyPressListener listener = new keyPressListener();
         editTextHandler.setOnKeyListener(listener);

        // editTextHandler.setOnKeyListener(new keyPressListener());
    }

    private class keyPressListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(keyCode == KeyEvent.KEYCODE_AT){
                Toast.makeText(MainActivity.this, "Please no @ signs :)", Toast.LENGTH_LONG).show();
            }
            return false;
        }
    }
}

package bit.chinjs1.task3_eventhandlers;

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

        EditText editTextUserName = (EditText) findViewById(R.id.etUserName);
        keyTypeHandler handler = new keyTypeHandler();
        editTextUserName.setOnKeyListener(handler);
    }

    private class keyTypeHandler implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                EditText userName = (EditText) findViewById(v.getId());

                if(userName.getText().toString().length() == 8) {
                    Toast.makeText(MainActivity.this, "Valid username " + userName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Please make username 8 characters long", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        }
    }
}

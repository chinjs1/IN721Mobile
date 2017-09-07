package bit.chinjs1.musicschoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the button ID
        Button submit = (Button) findViewById(R.id.btnSubmit);

        // Set the onClickLister to 'submit'
        submit.setOnClickListener(new buttonListener());
    }

    // Generate the button listener class
    private class buttonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Create the radio group ID
            RadioGroup rgInstrument = (RadioGroup) findViewById(R.id.rgInstruments);

            // Get the checked radio button
            int checkedId = rgInstrument.getCheckedRadioButtonId();

            // Declaring a radio button with the checkedId
            RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);

            //Displaying what they have enrolled in
            TextView tvDisplay = (TextView) findViewById(R.id.tvOutput);
            tvDisplay.setText("You have enrolled for " + checkedRadioButton.getText() + " lessons.");
        }
    }
}

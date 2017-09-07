package bit.chinjs1.spinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define an array of strings
        String[] months = {

                "January",
                "Febuary",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "November",
                "December"
        }; // Closing the array

        // Declare a spinner
        Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);

        // Layout for spinner
        int layoutID = android.R.layout.simple_spinner_item;

        // Create an adapter with the layout and strings array
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, layoutID, months);

        // Set adapter to the spinner
        monthSpinner.setAdapter(monthAdapter);

        // Setup the button with a listener
        Button buttonEnrol = (Button) findViewById(R.id.btnEnrol);
        buttonEnrol.setOnClickListener(new buttonListener());

    }

    // Button listener class
    private class buttonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Make the RadioGroup ID
            RadioGroup rgInstruments = (RadioGroup) findViewById(R.id.rgInstruments);

            // Find the button that has been clicked in the rgInstruments
            int checkedID = rgInstruments.getCheckedRadioButtonId();

            // Declare a RadioButton with the checkedID
            RadioButton checkedRadioButton = (RadioButton) findViewById(checkedID);

            Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);

            String outputMessage = "You have enrolled for " + checkedRadioButton.getText() + " lessons in " + monthSpinner.getSelectedItem();

            // Show user the output of their choices..
            TextView tvChosenInstrument = (TextView) findViewById(R.id.tvHeader2);
            tvChosenInstrument.setText(outputMessage);
        }
    }
}

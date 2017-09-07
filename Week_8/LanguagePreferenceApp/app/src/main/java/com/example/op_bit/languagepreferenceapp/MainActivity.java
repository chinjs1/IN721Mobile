package com.example.op_bit.languagepreferenceapp;

import android.content.SharedPreferences;
import android.graphics.Color;
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

    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    Spinner colorSpinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a button handler.
        Button btnLanguageSelected = (Button) findViewById(R.id.btnSetLanguage);
        btnLanguageSelected.setOnClickListener(new languageSelectedClickHandler());

        String[] colors = {
                "Cyan",
                "Red",
                "Yellow"
        };

        colorSpinner = (Spinner) findViewById(R.id.spinnerColor);
        int layoutID = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this, layoutID, colors);
        colorSpinner.setAdapter(colorAdapter);

        prefs = getSharedPreferences("demoPrefs", MODE_PRIVATE);
        prefsEditor = prefs.edit();

        String languagePreference = prefs.getString("language", null);
        if (languagePreference != null) {
            getGreeting(languagePreference);
        }
    }

    private class languageSelectedClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            RadioGroup rg = (RadioGroup) findViewById(R.id.rgLanguageChoice);
            int checkedID = rg.getCheckedRadioButtonId();
            RadioButton checkedButton = (RadioButton) findViewById(checkedID);

            String checkedLanguage = checkedButton.getText().toString();
            String checkedColor = colorSpinner.getSelectedItem().toString();

            prefsEditor.putString("language", checkedLanguage);
            prefsEditor.putString("color", checkedColor);
            prefsEditor.commit();

            getGreeting(checkedLanguage);
            changeColor(checkedColor);

        }
    } // End languageSelectedClickHandler

    private void getGreeting(String language) {

        TextView setChosenLanguage = (TextView) findViewById(R.id.tvLanguageChanger);
        String greeting = "";

        switch (language) {

            case "Chinese":
                greeting = "Ni Hao Shijie";
                break;

            case "Maori":
                greeting = "Kia Ora Te Ao";
                break;

            case "German":
                greeting = "Hallo Welt";
                break;
        }
        setChosenLanguage.setText(greeting);
    } // End getGreeting.


    private void changeColor(String color) {
        TextView setChosenLanguage = (TextView) findViewById(R.id.tvLanguageChanger);
        int colorCode = -1;

        switch (color) {

            case "Cyan":
                colorCode = Color.CYAN;
                break;

            case "Red":
                colorCode = Color.RED;
                break;

            case "Yellow":
                colorCode = Color.YELLOW;
                break;
        }
        setChosenLanguage.setTextColor(colorCode);
    } // End changeColor.


}

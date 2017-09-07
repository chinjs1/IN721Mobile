package com.example.joshua.qrcodereader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    enum Month {
        JAN,
        FEB,
        MAR,
        APR,
        MAY,
        JUN,
        JUL,
        AUG,
        SEP,
        OCT,
        NOV,
        DEC
    };

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static final int TOTAL_MONTH_COUNT = 12;
    int[] totalMonths;
    TextView tvResult;
    Button btnQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup screen controls
        tvResult = (TextView) findViewById(R.id.tvIntro);
        btnQR = (Button) findViewById(R.id.btnScanQR);

        // Create the click listener
        btnQR.setOnClickListener(new QRButtonClickHandler());
    }

    private class QRButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // On click change intent to the camera
            Intent CameraIntent = new Intent(MainActivity.this, CameraView.class);
            startActivityForResult(CameraIntent, 1);

            // Store the total month count(12) into a var
            totalMonths = new int[TOTAL_MONTH_COUNT];
            for (int months = 0; months < TOTAL_MONTH_COUNT; months++) {
                totalMonths[months] = 0;
            }

            String monthVal = OverallCountOfMonth();
            tvResult.setText("QR code scanner!\n" + monthVal);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == BARCODE_READER_REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            String contents = data.getStringExtra("QR_CONTENTS");

            if (CheckMonthContents(contents)) {

                Month month = Month.valueOf(contents.toUpperCase());
                // Returns the position in the enum
                int currentMonthVal = totalMonths[month.ordinal()];
                currentMonthVal++;
                totalMonths[month.ordinal()] = currentMonthVal;
            } else {
                if (contents.contains("http")) {

                    Uri uri = Uri.parse(contents.trim());
                    Intent changeIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(changeIntent);
                }
            }

            String monthValues = OverallCountOfMonth();
            tvResult.setText("The Value of the scanned QR is: \n" + contents + "\n" + monthValues);
        }
    }

    private boolean CheckMonthContents(String qrCodeValue) {
        try {

            Month month = Month.valueOf(qrCodeValue.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {

            return false;
        }

    }

    private String OverallCountOfMonth() {

        String output = "";
        for (int months = 0; months < TOTAL_MONTH_COUNT; months++) {
            Month currentMonth = Month.values()[months];
            String currentMonthName = currentMonth.toString();
            int currentMonthCount = totalMonths[currentMonth.ordinal()];

            output = output + currentMonthName + ": " + currentMonthCount + "\n";

        }
        return output;
    }
}

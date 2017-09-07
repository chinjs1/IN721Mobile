package com.example.joshua.qrcodereader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CameraView extends AppCompatActivity {

    SurfaceView cameraPreview;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        barcodeDetector.setProcessor(new BarcodeDetectorProcessor());

        cameraPreview = (SurfaceView) findViewById(R.id.surfaceView);
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640,640)
                .build();

        SurfaceView sv = (SurfaceView) findViewById(R.id.activity_camera_view);
        SurfaceHolder sh = sv.getHolder();
        sh.addCallback(new SurfaceHolderSetUp());
    }

    private class BarcodeDetectorProcessor implements com.google.android.gms.vision.Detector.Processor<Barcode> {
        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {

            // Detector.getDetectedItems returns a SparseArray of all the things it has been sent from the camera
            // SparseArray is just a different underlying data structure for arrays, which is more efficient for fetching
            SparseArray<Barcode> barcodes = detections.getDetectedItems();

            // Check that something was detected, just in case....
            if (barcodes.size() != 0)
            {
                // This is how to get the QR Code text out of the "detected thing" class instance
                String qrCodeMessage = barcodes.valueAt(0).displayValue;

                // Do whatever you want to do with that string here.....
                // For this practical, return it to the Activity that launched you, via intent
                // using putExtra and finish(), as you have done before.
                Intent returnToMainActivity = new Intent();
                returnToMainActivity.putExtra("QR_CONTENTS",qrCodeMessage);

                setResult(Activity.RESULT_OK, returnToMainActivity);
                finish();

            }
        }
    }

    private class SurfaceHolderSetUp implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try
            {
                cameraSource.start(holder);
            }
            catch (IOException ie)
            {
                Log.e("CAMERA SOURCE", ie.getMessage());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            cameraSource.stop();
        }
    }
}

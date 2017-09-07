package com.example.joshua.exploresensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    final int SPEED = 3;
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    AccelerometerHandler accelHandler;
    ImageView ivTrophy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // Sensor service
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // Reference to the accelerometer
        accelHandler = new AccelerometerHandler(); // Inner cass
        ivTrophy = (ImageView) findViewById(R.id.ivTrophy); // Reference to image
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(accelHandler, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(accelHandler);
    }

    public void Orientation(float X, float Y) {

        float posX = ivTrophy.getX();
        float posY = ivTrophy.getY();

        if (X < -1 || X > 1) {
            // If X is gt 0, go left
            if (X > 0) {
                posX = posX - SPEED;
            }
            // Else, lt 0, go right
            else {
                posX = posX + SPEED;
            }
            ivTrophy.setX(posX);
        }

        if (Y < -1 || Y > 1) {
            // If Y is gt 0, go up
            if (Y > 0) {
                posY = posY + SPEED;
            }
            // If Y is lt 0, go down
            else {
                posY = posY - SPEED;
            }
            ivTrophy.setY(posY);
        }
    }

    // Call the sensor handler when movement is detected
    private class AccelerometerHandler implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float X = sensorEvent.values[0];
            float Y = sensorEvent.values[1];

            // Give Orientation X and Y once values have changed
            Orientation(X, Y);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

}

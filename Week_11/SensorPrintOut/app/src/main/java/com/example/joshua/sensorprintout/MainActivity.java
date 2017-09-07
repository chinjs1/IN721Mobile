package bit.chinjs1.sensorprintout;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_PROXIMITY;

public class MainActivity extends AppCompatActivity {
    SensorManager  mSensorManager;
    ListView lvSensors;
    TextView tvProximity;
    TextView tvX;
    TextView tvY;
    TextView tvZ;
    Sensor mProximity;
    Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSensors = (ListView)findViewById(R.id.lvSensors);
        tvProximity = (TextView)findViewById(R.id.tvProximity);
        tvX = (TextView)findViewById(R.id.tvX);
        tvY = (TextView)findViewById(R.id.tvY);
        tvZ = (TextView)findViewById(R.id.tvZ);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        bindSensorAdapter(getPhoneSensors());
        ProximityEventListener proxListener = new ProximityEventListener();
        AccelerationEventListener accelListener = new AccelerationEventListener();

        if(checkSensorAvailability(Sensor.TYPE_PROXIMITY))
        {
            mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorManager.registerListener(proxListener, mProximity, mSensorManager.SENSOR_DELAY_NORMAL);
        }

        if(checkSensorAvailability(Sensor.TYPE_ACCELEROMETER))
        {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(accelListener, mAccelerometer, mSensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private ArrayList<String> getPhoneSensors()
    {
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        ArrayList<String> sensorList = new ArrayList<String>();
        for (Sensor sensor: deviceSensors)
            sensorList.add(sensor.getName());
        return sensorList;
    }

    private void bindSensorAdapter(ArrayList<String> sensorList)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,sensorList);
        lvSensors.setAdapter(adapter);
    }

    private boolean checkSensorAvailability(int sensor)
    {
        switch(sensor)
        {
            case TYPE_PROXIMITY:
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) ;

            case TYPE_ACCELEROMETER:
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) ;

            default:
                return false;
        }
    }

    public class ProximityEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            double distance = sensorEvent.values[0];
            String prox = "";
            if(distance < 8)
                prox = "Near";
            else
                prox = "Far";
            tvProximity.setText(prox);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    public class AccelerationEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            DecimalFormat df = new DecimalFormat(".#");
            tvX.setText("X: " + df.format(x));
            tvY.setText("Y: " + df.format(y));
            tvZ.setText("Z: " + df.format(z));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
}
package com.example.w22shake;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        TextView bottomText = findViewById(R.id.bottomTextView);
        ImageView topImage = findViewById(R.id.imageView);

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        bottomText.setText("Accelerometer info: \nX: " + x + "\nY: " + y + "\nZ: " + z);

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());

        if (getForce(x, y, z) > 1.3f){
            topImage.startAnimation(rotate);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private double getForce (double x, double y, double z) {
        double gX = x / sensorManager.GRAVITY_EARTH;
        double gY = y / sensorManager.GRAVITY_EARTH;
        double gZ = z / sensorManager.GRAVITY_EARTH;
        return Math.sqrt((double) (gX * gX + gY * gY + gZ * gZ));
    }
}
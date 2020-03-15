package com.joblesscoders.activitymeter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    private SensorManager sensorManager;
    private List<Float> gyroxList= new ArrayList<>(),gyroyList= new ArrayList<>(),gyrozList= new ArrayList<>(),acceleroxList= new ArrayList<>(),acceleroyList= new ArrayList<>(),accelerozList = new ArrayList<>(),magnetoxList = new ArrayList<>(),magnetoyList = new ArrayList<>(),magnetozList = new ArrayList<>();
    private View stop;
    private Float gyrox = 0.0f,gyroz= 0.0f,gyroy= 0.0f,accelerox= 0.0f,acceleroy= 0.0f,acceleroz= 0.0f,magnetox= 0.0f,magnetoy= 0.0f,magnetoz= 0.0f;
    private String username,activityname;
    private long delay;
    private long start_time,end_time;
    private  Handler handler;
    private Runnable runnable;
    private  GraphView graphGyro,graphAccelero,graphMagneto;
    private LineGraphSeries<DataPoint> seriesGyroX = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesGyroY = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesGyroZ = new LineGraphSeries<>();

    private LineGraphSeries<DataPoint> seriesAcceleroX = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesAcceleroY = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesAcceleroZ = new LineGraphSeries<>();

    private LineGraphSeries<DataPoint> seriesMagnetoX = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesMagnetoY = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesMagnetoZ = new LineGraphSeries<>();

    private List<DataPoint>  seriesGyroXList = new ArrayList<>(), seriesGyroYList = new ArrayList<>(), seriesGyroZList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        graphGyro = (GraphView) findViewById(R.id.graphgyro);
        graphAccelero = (GraphView) findViewById(R.id.graphaccelero);
        graphMagneto = (GraphView) findViewById(R.id.graphmagneto);

        graphGyro.setTitle("Gyroscope");
        graphAccelero.setTitle("Accelerometer");
        graphGyro.setTitle("Magnetometer");

        seriesGyroX.setTitle("X");
        seriesGyroX.setColor(Color.RED);
        seriesGyroX.setDrawDataPoints(true);
        seriesGyroX.setDataPointsRadius(10);
        seriesGyroX.setThickness(8);

        seriesAcceleroX.setTitle("X");
        seriesAcceleroX.setColor(Color.RED);
        seriesAcceleroX.setDrawDataPoints(true);
        seriesAcceleroX.setDataPointsRadius(10);
        seriesAcceleroX.setThickness(8);

        seriesMagnetoX.setTitle("X");
        seriesMagnetoX.setColor(Color.RED);
        seriesMagnetoX.setDrawDataPoints(true);
        seriesMagnetoX.setDataPointsRadius(10);
        seriesMagnetoX.setThickness(8);
        //seriesGyroX.setCustomPaint(paint);

        seriesGyroY.setTitle("Y");
        seriesGyroY.setColor(Color.BLUE);
        seriesGyroY.setDrawDataPoints(true);
        seriesGyroY.setDataPointsRadius(10);
        seriesGyroY.setThickness(8);

        seriesAcceleroY.setTitle("Y");
        seriesAcceleroY.setColor(Color.BLUE);
        seriesAcceleroY.setDrawDataPoints(true);
        seriesAcceleroY.setDataPointsRadius(10);
        seriesAcceleroY.setThickness(8);

        seriesMagnetoY.setTitle("Y");
        seriesMagnetoY.setColor(Color.BLUE);
        seriesMagnetoY.setDrawDataPoints(true);
        seriesMagnetoY.setDataPointsRadius(10);
        seriesMagnetoY.setThickness(8);

        seriesGyroZ.setTitle("Z");
        seriesGyroZ.setColor(Color.GREEN);
        seriesGyroZ.setDrawDataPoints(true);
        seriesGyroZ.setDataPointsRadius(10);
        seriesGyroZ.setThickness(8);

        seriesAcceleroZ.setTitle("Z");
        seriesAcceleroZ.setColor(Color.GREEN);
        seriesAcceleroZ.setDrawDataPoints(true);
        seriesAcceleroZ.setDataPointsRadius(10);
        seriesAcceleroZ.setThickness(8);

        seriesMagnetoZ.setTitle("Z");
        seriesMagnetoZ.setColor(Color.GREEN);
        seriesMagnetoZ.setDrawDataPoints(true);
        seriesMagnetoZ.setDataPointsRadius(10);
        seriesMagnetoZ.setThickness(8);

        graphGyro.addSeries(seriesGyroX);
        graphGyro.addSeries(seriesGyroY);
        graphGyro.addSeries(seriesGyroZ);

        start_time = new Date().getTime();
        username = getIntent().getExtras().getString("username");
        activityname = getIntent().getExtras().getString("activityname");
        delay = Long.parseLong((getIntent().getExtras().getString("delay")));
        stop = findViewById(R.id.stop);
        stop.setOnClickListener(this);
        startEvent();
    }

    private void startEvent() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                long time = new Date().getTime() - start_time;

                gyroxList.add(gyrox);
                gyroyList.add(gyroy);
                gyrozList.add(gyroz);

              //  seriesGyroX.resetData(seriesGyroYList.toArray());
                seriesGyroX.appendData(new DataPoint(time/1000,gyrox),true,100);
                graphGyro.addSeries(seriesGyroX);

                seriesGyroY.resetData(new DataPoint[]{});
                seriesGyroY.appendData(new DataPoint(time/1000,gyroy),true,100);
                graphGyro.addSeries(seriesGyroY);

                seriesGyroZ.resetData(new DataPoint[]{});
                seriesGyroZ.appendData(new DataPoint(time/1000,gyroz),true,100);
                graphGyro.addSeries(seriesGyroZ);

                acceleroxList.add(accelerox);
                acceleroyList.add(acceleroy);
                accelerozList.add(acceleroz);

                seriesAcceleroX.resetData(new DataPoint[]{});
                seriesAcceleroX.appendData(new DataPoint(time/1000,accelerox),true,100);
                graphAccelero.addSeries(seriesAcceleroX);

                seriesAcceleroY.resetData(new DataPoint[]{});
                seriesAcceleroY.appendData(new DataPoint(time/1000,acceleroy),true,100);
                graphAccelero.addSeries(seriesAcceleroY);

                seriesAcceleroZ.resetData(new DataPoint[]{});
                seriesAcceleroZ.appendData(new DataPoint(time/1000,acceleroz),true,100);
                graphAccelero.addSeries(seriesAcceleroZ);

                magnetoxList.add(magnetox);
                magnetoyList.add(magnetoy);
                magnetozList.add(magnetoz);

                seriesMagnetoX.resetData(new DataPoint[]{});
                seriesMagnetoX.appendData(new DataPoint(time/1000,magnetox),true,100);
                graphMagneto.addSeries(seriesMagnetoX);
                //seriesMagnetoY.
                seriesMagnetoY.resetData(new DataPoint[]{});
                seriesMagnetoY.appendData(new DataPoint(time/1000,magnetoy),true,100);
                graphMagneto.addSeries(seriesMagnetoY);

                seriesMagnetoZ.resetData(new DataPoint[]{});
                seriesMagnetoZ.appendData(new DataPoint(time/1000,magnetoz),true,100);
                graphMagneto.addSeries(seriesMagnetoZ);

                Log.d("hello","gyrox "+gyrox+" gyroy "+gyroy+" gyroz "+gyroz);
                Log.d("hello","magnetox "+magnetox+" magnetoy "+magnetoy+" magnetoz "+magnetoz);

                Log.d("hello","accelerox "+accelerox+" acceleryx "+acceleroy+" acceleroz "+acceleroz);
            handler.postDelayed(runnable,delay);
            }
        };
        handler.postDelayed(runnable,delay);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION || sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE || sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            getData(sensorEvent);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void getData(SensorEvent event) {

        float[] values = event.values;

        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        //long actualTime = event.timestamp;




        //if (actualTime - lastUpdate > 200) {

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyrox = x;
            gyroy = y;
            gyroz = z;
            //  lastUpdate = actualTime;
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            magnetox = x;
            magnetoy = y;
            magnetoz = z;
            //  lastUpdate = actualTime;
        }
        else if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
        {
            accelerox = x;
            acceleroy = y;
            acceleroz = z;
        }
        //   }

    }
    @Override
    protected void onResume() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_NORMAL);
       boolean is = sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);
        //Toast.makeText(this, is+"", Toast.LENGTH_SHORT).show();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        end_time = new Date().getTime();
        ActivityPojo activityPojo = new ActivityPojo(magnetoxList,magnetoyList,activityname,accelerozList,gyroxList,gyroyList,gyrozList,acceleroxList,acceleroyList,start_time,end_time,magnetozList,username);
        RetrofitClient.getAPIService().postActivity(activityPojo).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               // Toast.makeText(EventActivity.this, "kll", Toast.LENGTH_SHORT).show();
                if(response.code() == 200)
                {
                    Toast.makeText(EventActivity.this, "Congrats nigga we have your data now, thanks for being our business model :3", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(EventActivity.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EventActivity.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();
            }
        });
        handler.removeCallbacks(runnable);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Activity is already running!", Toast.LENGTH_SHORT).show();
       // super.onBackPressed();
    }
}

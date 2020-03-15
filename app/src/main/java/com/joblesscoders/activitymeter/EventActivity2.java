package com.joblesscoders.activitymeter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity2 extends AppCompatActivity implements SensorEventListener, View.OnClickListener{
    private LineChart chartGyro,chartAccelero,chartMagneto;
    private SensorManager sensorManager;
    private List<Float> gyroxList= new ArrayList<>(),gyroyList= new ArrayList<>(),gyrozList= new ArrayList<>(),acceleroxList= new ArrayList<>(),acceleroyList= new ArrayList<>(),accelerozList = new ArrayList<>(),magnetoxList = new ArrayList<>(),magnetoyList = new ArrayList<>(),magnetozList = new ArrayList<>();
    private View stop;
    private Float gyrox = 0.0f,gyroz= 0.0f,gyroy= 0.0f,accelerox= 0.0f,acceleroy= 0.0f,acceleroz= 0.0f,magnetox= 0.0f,magnetoy= 0.0f,magnetoz= 0.0f;
    private String username,activityname;
    private long delay;
    private long start_time,end_time;
    private Handler handler;
    private Runnable runnable;
    private LineData dataGyro,dataAccelero,dataMagneto;

    ArrayList<ILineDataSet> dataSetsGyro = new ArrayList<>(),dataSetsAccelero = new ArrayList<>(),dataSetsMagneto = new ArrayList<>();

    private LineDataSet dataSetGyroZ ,dataSetGyroY,dataSetGyroX,dataSetAcceleroX,dataSetAcceleroY,dataSetAcceleroZ,dataSetMagnetoX,dataSetMagnetoY,dataSetMagnetoZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event2);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        chartGyro = (LineChart) findViewById(R.id.graphgyro);
        chartAccelero = (LineChart) findViewById(R.id.graphaccelero);
        chartMagneto = (LineChart) findViewById(R.id.graphmagneto);

        start_time = new Date().getTime();
        username = getIntent().getExtras().getString("username");
        activityname = getIntent().getExtras().getString("activityname");
        delay = Long.parseLong((getIntent().getExtras().getString("delay")));
        stop = findViewById(R.id.stop);
        stop.setOnClickListener(this);

        dataSetGyroX = new LineDataSet(new ArrayList<Entry>(), "Gyro X");
        dataSetGyroY = new LineDataSet(new ArrayList<Entry>(), "Gyro Y");
        dataSetGyroZ = new LineDataSet(new ArrayList<Entry>(), "Gyro Z");

        dataSetGyroX.setColor(Color.GREEN);
        dataSetsGyro.add(dataSetGyroX);

        dataSetGyroY.setColor(Color.RED);
        dataSetsGyro.add(dataSetGyroY);

        dataSetGyroZ.setColor(Color.BLUE);
        dataSetsGyro.add(dataSetGyroZ);

        dataGyro = new LineData(dataSetsGyro);
        chartGyro.setData(dataGyro);
        chartGyro.invalidate();

        dataSetAcceleroX = new LineDataSet(new ArrayList<Entry>(), "Accelero X");
        dataSetAcceleroY = new LineDataSet(new ArrayList<Entry>(), "Accelero Y");
        dataSetAcceleroZ = new LineDataSet(new ArrayList<Entry>(), "Accelero Z");

        dataSetAcceleroX.setColor(Color.MAGENTA);
        dataSetsAccelero.add(dataSetAcceleroX);

        dataSetAcceleroY.setColor(Color.RED);
        dataSetsAccelero.add(dataSetAcceleroY);

        dataSetAcceleroZ.setColor(Color.BLUE);
        dataSetsAccelero.add(dataSetAcceleroZ);

        dataAccelero = new LineData(dataSetsAccelero);
        chartAccelero.setData(dataAccelero);
        chartAccelero.invalidate();

        dataSetMagnetoX = new LineDataSet(new ArrayList<Entry>(), "Magneto X");
        dataSetMagnetoY = new LineDataSet(new ArrayList<Entry>(), "Magneto Y");
        dataSetMagnetoZ = new LineDataSet(new ArrayList<Entry>(), "Magneto Z");

        dataSetMagnetoX.setColor(Color.MAGENTA);
        dataSetsMagneto.add(dataSetMagnetoX);

        dataSetMagnetoY.setColor(Color.RED);
        dataSetsMagneto.add(dataSetMagnetoY);

        dataSetMagnetoZ.setColor(Color.BLUE);
        dataSetsMagneto.add(dataSetMagnetoZ);

        dataMagneto = new LineData(dataSetsMagneto);
        chartMagneto.setData(dataMagneto);
        chartMagneto.invalidate();

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


                dataGyro.addEntry(new Entry(time/1000,gyrox),0);
                dataGyro.addEntry(new Entry(time/1000,gyroy),1);
                dataGyro.addEntry(new Entry(time/1000,gyroz),2);
                chartGyro.notifyDataSetChanged();
                chartGyro.invalidate();



                acceleroxList.add(accelerox);
                acceleroyList.add(acceleroy);
                accelerozList.add(acceleroz);

                dataAccelero.addEntry(new Entry(time/1000,accelerox),0);
                dataAccelero.addEntry(new Entry(time/1000,acceleroy),1);
                dataAccelero.addEntry(new Entry(time/1000,acceleroz),2);
                chartAccelero.notifyDataSetChanged();
                chartAccelero.invalidate();

                magnetoxList.add(magnetox);
                magnetoyList.add(magnetoy);
                magnetozList.add(magnetoz);

                dataMagneto.addEntry(new Entry(time/1000,magnetox),0);
                dataMagneto.addEntry(new Entry(time/1000,magnetoy),1);
                dataMagneto.addEntry(new Entry(time/1000,magnetoz),2);
                chartMagneto.notifyDataSetChanged();
                chartMagneto.invalidate();


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
                    Toast.makeText(EventActivity2.this, "Congrats nigga we have your data now, thanks for being our business model :3", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(EventActivity2.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EventActivity2.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();
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

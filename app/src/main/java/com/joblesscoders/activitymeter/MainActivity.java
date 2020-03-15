package com.joblesscoders.activitymeter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int PERMISSIONS_REQUEST_WRITE_STORAGE = 5;
    //private long lastUpdate;
    //private List<ActivityPojo> activityPojoList = new ArrayList<>();
    private List<Float> gyroxList = new ArrayList<>(), gyroyList = new ArrayList<>(), gyrozList = new ArrayList<>(), acceleroxList = new ArrayList<>(), acceleroyList = new ArrayList<>(), accelerozList = new ArrayList<>();
    private View start, showdata;
    private EditText username, activityname, delay;
    private  List<ActivityPojo> activityPojoList;
    private Float gyrox = 0.0f, gyroz = 0.0f, gyroy = 0.0f, accelerox = 0.0f, acceleroy = 0.0f, acceleroz = 0.0f, magnetox = 0.0f, magnetoy = 0.0f, magnetoz = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        showdata = findViewById(R.id.getdata);
        showdata.setOnClickListener(this);
        username = findViewById(R.id.username);
        activityname = findViewById(R.id.activity);
        delay = findViewById(R.id.delay);

        start.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.getdata:
                getAllData();
               // startActivity(new Intent(this,ShowDataActivity.class));
                break;
            case R.id.start:
        // Toast.makeText(this, "kk", Toast.LENGTH_SHORT).show();
        if (username.getText().toString().trim().length() > 0 && activityname.getText().toString().trim().length() > 0 && delay.getText().toString().trim().length() > 0) {
            if (Long.parseLong(delay.getText().toString()) <= 0) {
                Toast.makeText(this, "Hey you l'il bitch, time should be greater than zero.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, EventActivity2.class);
            intent.putExtra("username", username.getText().toString());
            intent.putExtra("activityname", activityname.getText().toString());
            intent.putExtra("delay", delay.getText().toString());
            startActivity(intent);
        } else
            Toast.makeText(this, "There's a reason there are three boxes, fill'em up bitch!", Toast.LENGTH_SHORT).show();

        break;
        }
}

    private void getAllData() {
        RetrofitClient.getAPIService().getAllActivity().enqueue(new Callback<List<ActivityPojo>>() {
            @Override
            public void onResponse(Call<List<ActivityPojo>> call, Response<List<ActivityPojo>> response) {
                if(response.code() == 200)
                {
                    activityPojoList =  response.body();
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PERMISSIONS_REQUEST_WRITE_STORAGE);

                    }
                    else
                    {
                        generateCSV(activityPojoList);
                    }

                   // Toast.makeText(MainActivity.this, activityPojoList.size()+"", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<ActivityPojo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void generateCSV(List<ActivityPojo> activityPojoList) {

        for(final ActivityPojo activityPojo:activityPojoList) {
           RetrofitClient.getAPIService().downloadFileWithDynamicUrlSync(activityPojo.getFilePath()).enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   if(response.code() == 200)
                   {
                       boolean writtenToDisk = writeResponseBodyToDisk(response.body(),activityPojo.get_id());
                       if(writtenToDisk)
                           Toast.makeText(MainActivity.this, activityPojo.get_id().get$oid()+" is saved", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {

               }
           });
        }
    }


        private boolean writeResponseBodyToDisk(ResponseBody body, _id id) {
            try {
                File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"ActivityMeter");
                if(!directory.exists())
                    directory.mkdirs();
                // todo change the file location/name according to your needs
                File futureStudioIconFile = new File(directory.getAbsolutePath() + File.separator + id.get$oid()+".csv");

                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[4096];

                    long fileSize = body.contentLength();
                    long fileSizeDownloaded = 0;

                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(futureStudioIconFile);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }
                        outputStream.write(fileReader, 0, read);

                        fileSizeDownloaded += read;

                       // Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }

                    outputStream.flush();

                    return true;
                } catch (IOException e) {
                    return false;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    generateCSV(activityPojoList);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}

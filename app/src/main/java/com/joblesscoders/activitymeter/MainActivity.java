package com.joblesscoders.activitymeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //private long lastUpdate;
    private List<Float> gyroxList = new ArrayList<>(), gyroyList = new ArrayList<>(), gyrozList = new ArrayList<>(), acceleroxList = new ArrayList<>(), acceleroyList = new ArrayList<>(), accelerozList = new ArrayList<>();
    private View start, showdata;
    private EditText username, activityname, delay;
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
}

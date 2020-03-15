package com.joblesscoders.activitymeter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDataActivity extends AppCompatActivity {
    private List<ActivityPojo> activityPojoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        RetrofitClient.getAPIService().getAllActivity().enqueue(new Callback<List<ActivityPojo>>() {
            @Override
            public void onResponse(Call<List<ActivityPojo>> call, Response<List<ActivityPojo>> response) {
                if(response.code() == 200)
                {
                   activityPojoList =  response.body();

                    Toast.makeText(ShowDataActivity.this, activityPojoList.size()+"", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ShowDataActivity.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<ActivityPojo>> call, Throwable t) {
                Toast.makeText(ShowDataActivity.this, "Not my problem, server fucked up!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

package com.joblesscoders.activitymeter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("postActivity")
    Call<String> postActivity(@Body ActivityPojo body);

    @POST("listFiles")
    Call<List<ActivityPojo>> getAllActivity();
}

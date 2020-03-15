package com.joblesscoders.activitymeter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIService {

    @POST("postActivity")
    Call<String> postActivity(@Body ActivityPojo body);

    @GET("listFiles")
    Call<List<ActivityPojo>> getAllActivity();

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}

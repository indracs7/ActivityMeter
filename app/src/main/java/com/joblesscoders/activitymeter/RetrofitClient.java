package com.joblesscoders.activitymeter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://vor76f0raj.execute-api.ap-south-1.amazonaws.com/dev/";
        //"http://projectwatch.serverless.social/";
    //https://vor76f0raj.execute-api.ap-south-1.amazonaws.com/dev/";

    private static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory
                        .create(gson))
                .build();
    }

    public static APIService getAPIService() {
        return getRetrofitInstance().create(APIService.class);
    }
}

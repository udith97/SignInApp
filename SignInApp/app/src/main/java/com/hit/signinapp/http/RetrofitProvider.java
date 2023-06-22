package com.hit.signinapp.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static final String BASE_URL = "http://ec2-13-59-60-131.us-east-2.compute.amazonaws.com:8000/admin/";
    private static Retrofit retrofit = null;

    //Create Retrofit Client
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

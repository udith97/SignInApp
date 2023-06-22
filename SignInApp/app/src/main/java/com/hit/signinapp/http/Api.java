package com.hit.signinapp.http;

import com.hit.signinapp.model.LoginResponseModel;
import com.hit.signinapp.model.RegisterResponseModel;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    //Login
    @POST("/user/login")
    Call<LoginResponseModel> login(@Body RequestBody body);

    //Register
    @FormUrlEncoded
    @Multipart
    @POST("register")
    Call<RegisterResponseModel> register(
            @Field("admin_name") String admin_name,
            @Field("email") String email,
            @Part MultipartBody.Part imageFile,
            @Field("password") String password
    );
}

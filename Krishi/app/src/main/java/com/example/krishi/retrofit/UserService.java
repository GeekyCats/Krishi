package com.example.krishi.retrofit;

import com.example.krishi.data.LoginRequest;
import com.example.krishi.data.News;
import com.example.krishi.data.UserResponse;
import com.example.krishi.data.SignUpRequest;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("account/signin")
    retrofit2.Call<UserResponse> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("account/signup")
    retrofit2.Call<UserResponse> register(@Body SignUpRequest signUpRequest);

    @POST("/news")
    retrofit2.Call<List<News>> getNews();


}

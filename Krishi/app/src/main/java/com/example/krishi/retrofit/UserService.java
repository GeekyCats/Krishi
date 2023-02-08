package com.example.krishi.retrofit;

import com.example.krishi.data.models.User;
import com.example.krishi.data.requests.LoginRequest;
import com.example.krishi.data.models.News;
import com.example.krishi.data.responses.LoginResponse;
import com.example.krishi.data.requests.SignUpRequest;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("account/signin")
    retrofit2.Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("account/signup")
    retrofit2.Call<User> register(@Body SignUpRequest signUpRequest);

    @POST("/news")
    retrofit2.Call<List<News>> getNews();


}

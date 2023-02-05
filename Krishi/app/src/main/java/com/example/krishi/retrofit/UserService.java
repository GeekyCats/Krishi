package com.example.krishi.retrofit;

import com.example.krishi.data.LoginRequest;
import com.example.krishi.data.News;
import com.example.krishi.data.UserResponse;
import com.example.krishi.data.SignUpRequest;
import com.example.krishi.data.WeatherResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("account/signin")
    retrofit2.Call<UserResponse> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("account/signup")
    retrofit2.Call<UserResponse> register(@Body SignUpRequest signUpRequest);

    @POST("/news")
    retrofit2.Call<List<News>> getNews();

    @Headers("Content-Type: application/json")
    @POST("/weather/current")
    retrofit2.Call<WeatherResponse> getWeather(@Query("location") String city);

}

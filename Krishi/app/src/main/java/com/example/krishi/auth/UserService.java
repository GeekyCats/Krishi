package com.example.krishi.auth;

import com.example.krishi.SignUp;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("account/signin")
    retrofit2.Call<SignUpResponse> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("account/signup")
    retrofit2.Call<SignUpResponse> register(@Body User user);


}

package com.example.krishi.data.responses;

import com.example.krishi.data.models.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status_code")
    private int statusCode;

    public LoginResponse(int statusCode, String authToken, User user) {
        this.statusCode = statusCode;
        this.authToken = authToken;
        this.user = user;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SerializedName("token")
    private String authToken;

    @SerializedName("user")
    private User user;
}

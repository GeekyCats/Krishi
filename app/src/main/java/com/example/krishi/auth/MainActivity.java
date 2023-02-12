package com.example.krishi.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.krishi.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        context = this.context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPreferences.contains("authToken")){
            finish();
        }
    }

    public static Context getContext(){
        return context;
    }
}
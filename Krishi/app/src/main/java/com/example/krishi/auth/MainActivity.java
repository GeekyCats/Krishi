package com.example.krishi.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.krishi.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPreferences.contains("authToken")){
            finish();
        }
    }
}
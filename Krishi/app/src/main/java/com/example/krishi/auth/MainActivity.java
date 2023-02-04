package com.example.krishi.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.krishi.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
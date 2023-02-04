package com.example.krishi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
        Home home = new Home();
        Crop crop = new Crop();
        Weather weather = new Weather();
        Person person = new Person();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, home).commit();
                    return true;
                case R.id.crop:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, crop).commit();
                    return true;
                case R.id.weather:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, weather).commit();
                    return true;
                case R.id.person:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, person).commit();
                    return true;
            }
            return false;
        }
    }
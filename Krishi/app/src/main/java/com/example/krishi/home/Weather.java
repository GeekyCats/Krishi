package com.example.krishi.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.krishi.R;
import com.example.krishi.data.WeatherResponse;
import com.example.krishi.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Response;


public class Weather extends Fragment {

    TextView city,temp,humidity,rainfall;
    EditText city_name;
    ImageView icon;
    Button search;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        city = view.findViewById(R.id.city);
        temp = view.findViewById(R.id.temperature);
        humidity = view.findViewById(R.id.humidity);
//        rainfall = view.findViewById(R.id.rainfall);
//        city_name = view.findViewById(R.id.cityName);
//        icon = view.findViewById(R.id.imageView8);
//        search = view.findViewById(R.id.btn_enter);
        this.context = getContext();

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = city_name.getText().toString();
//                city.setText(name);
//                getWeatherData(name);
//            }});

        return view;
    }

    public void getWeatherData(String ok){
        Call<WeatherResponse> call = ApiClient.getAPIService().getWeather(ok);

        call.enqueue(new retrofit2.Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, retrofit2.Response<WeatherResponse> response) {
                if(response.isSuccessful()){
                    WeatherResponse weatherResponse = response.body();
                    Toast.makeText(getContext(), "oKAY !!", Toast.LENGTH_SHORT).show();
//                    setText(weatherResponse);
                }
                else{
                    Toast.makeText(getContext(), "Request Fucked up !!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Request Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setText(WeatherResponse weatherResponse){
        city.setText(city_name.getText().toString());
        temp.setText((int) weatherResponse.getTemprature());
        humidity.setText((int) weatherResponse.getHumidity());
        rainfall.setText((int) weatherResponse.getPrecipitation());
        String url = weatherResponse.getIcon_url();
        Glide.with(context).load(url).into(icon);
    }
}
package com.example.krishi.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.krishi.R;
import com.example.krishi.data.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class Weather extends Fragment {

    TextView city,temp,humidity,rainfall,feels_like,condition;
    ImageView icon;

    Context context;

    RequestQueue queue;

    String api_url = "http://api.weatherapi.com/v1/current.json?key=bb0c2c73eebf4e3b914173924230702&q=";

    double lat,lon;
    FusedLocationProviderClient fusedLocationProviderClient;

    private final int REQUEST_LOCATION_PERMISSION = 1;
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
        rainfall= view.findViewById(R.id.rainfall);
        feels_like = view.findViewById(R.id.feels_like);
        condition = view.findViewById(R.id.condition);
        icon = view.findViewById(R.id.weather_resource);
        context = requireContext();

        queue = Volley.newRequestQueue(requireContext());

        assert context != null;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        requestLocationPermission();

        Toast.makeText(context, "ok1", Toast.LENGTH_SHORT).show();
        obtainLocation();


        return view;
    }

    @SuppressLint("MissingPermission")
    private void obtainLocation() {
        Toast.makeText(context, "ok2", Toast.LENGTH_SHORT).show();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                Toast.makeText(context, "ok3", Toast.LENGTH_SHORT).show();

                getTemp();
            }
        });
    }

    private void getTemp() {

        Toast.makeText(context, "ok4", Toast.LENGTH_SHORT).show();
        api_url = api_url + lat + "," + lon;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, api_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject location = response.getJSONObject("location");
                    JSONObject current =  response.getJSONObject("current");

                    String cityName = location.getString("name");

                    double temp1 = current.getDouble("temp_c");
                    int temp2 = (int) Math.round(temp1);
                    String temperature = String.valueOf(temp2);

                    double feels_like1 =current.getDouble("feelslike_c");
                    int feels_like2 = (int) Math.round(feels_like1);
                    String feelsLike = String.valueOf(feels_like2);

                    int humidity1 = current.getInt("humidity");
                    String humidity2 = String.valueOf(humidity1);

                    JSONObject condition = current.getJSONObject("condition");
                    String condition1 = condition.getString("text");

                    double rain1 = current.getDouble("precip_mm");
                    int rain2 = (int) Math.round(rain1);
                    String rainfall1 = String.valueOf(rain2);

                    int id = condition.getInt("code");

                    WeatherResponse weatherResponse = new WeatherResponse(cityName,temperature,humidity2,
                            feelsLike,condition1,rainfall1,id);

                    setParameters(weatherResponse);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void setParameters(WeatherResponse weatherResponse) {
        Toast.makeText(context, "ok7", Toast.LENGTH_SHORT).show();
        city.setText(weatherResponse.getCity()+"°C");
        temp.setText(weatherResponse.getTemp()+"°C");
        humidity.setText(weatherResponse.getHumidity());
        rainfall.setText(weatherResponse.getRainfall());
        feels_like.setText(weatherResponse.getFeels_like());
        condition.setText(weatherResponse.getCondition());

        int id = weatherResponse.getId();
        if(id==1273 || id==1276 || id==1279 || id==1282 || id==1087){
            icon.setImageResource(R.drawable.thunderstorm);
        }
        else if(id==1063 || id==1072 || id==1153 || id==1168 || id==1180 || id==1183 || id==1186 || id==1189
                || id==1204 || id==1207 || id==1240 || id==1243 || id==1246 || id==1261 || id==1264){
            icon.setImageResource(R.drawable.drizzle);
        }
        else if(id==1192 || id==1195 || id==1201 || id==1198 || id==1249 || id==1252 || id==1255 || id==1258){
            icon.setImageResource(R.drawable.rain);
        }
        else if(id==1066 || id==114 || id == 1210 || id==1213 || id==1216 || id==1219 || id==1222 || id==1225){
            icon.setImageResource(R.drawable.snow);
        }
        else if(id==1030||id==1135||id==1147){
            icon.setImageResource(R.drawable.fog);
        }
        else if(id==1000){
            icon.setImageResource(R.drawable.sunny);
        }
        else if(id==1003 || id==1006 || id==1009){
            icon.setImageResource(R.drawable.clouds);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(requireContext(), perms)) {
            Toast.makeText(requireContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


}
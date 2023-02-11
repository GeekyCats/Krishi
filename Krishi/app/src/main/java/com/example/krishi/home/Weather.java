package com.example.krishi.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.krishi.R;
import com.example.krishi.data.responses.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class Weather extends Fragment {

    TextView city, temp, humidity, rainfall, feels_like, condition;
    ImageView icon;

    Context context;

    RequestQueue queue;

    String api_url = "https://api.weatherapi.com/v1/current.json?key=bb0c2c73eebf4e3b914173924230702&q=";

    double lat, lon;

    Location currentLocation;
    LocationManager locationManager;

    Location locationByGPS, locationByNetwork;


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
        temp = view.findViewById(R.id.temp_condition);
        humidity = view.findViewById(R.id.humidity_value);
        rainfall = view.findViewById(R.id.rainfall);
        feels_like = view.findViewById(R.id.feels_like);
        condition = view.findViewById(R.id.condition);
        icon = view.findViewById(R.id.weather_resource);
        context = requireContext();

        queue = Volley.newRequestQueue(requireContext());

        assert context != null;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        getTemp();

        return view;
    }

    private void getTemp() {

        getLastLocation();
        api_url = api_url + lat + "," + lon;
        System.out.println(api_url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, api_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("recieved response");
                    JSONObject location = response.getJSONObject("location");
                    JSONObject current = response.getJSONObject("current");

                    String cityName = location.getString("name");

                    double temp1 = current.getDouble("temp_c");
                    int temp2 = (int) Math.round(temp1);
                    String temperature = String.valueOf(temp2);

                    double feels_like1 = current.getDouble("feelslike_c");
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

                    WeatherResponse weatherResponse = new WeatherResponse(cityName, temperature, humidity2,
                            feelsLike, condition1, rainfall1, id);

                    setParameters(weatherResponse);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void setParameters(WeatherResponse weatherResponse) {

        city.setText(weatherResponse.getCity());
        temp.setText(weatherResponse.getTemp() + "°C");
        humidity.setText(weatherResponse.getHumidity());
        rainfall.setText(weatherResponse.getRainfall());
        feels_like.setText(weatherResponse.getFeels_like());
        condition.setText(weatherResponse.getCondition());

        int id = weatherResponse.getId();
        if (id == 1273 || id == 1276 || id == 1279 || id == 1282 || id == 1087) {
            icon.setImageResource(R.drawable.thunderstorm);
        } else if (id == 1063 || id == 1072 || id == 1153 || id == 1168 || id == 1180 || id == 1183 || id == 1186 || id == 1189
                || id == 1204 || id == 1207 || id == 1240 || id == 1243 || id == 1246 || id == 1261 || id == 1264) {
            icon.setImageResource(R.drawable.drizzle);
        } else if (id == 1192 || id == 1195 || id == 1201 || id == 1198 || id == 1249 || id == 1252 || id == 1255 || id == 1258) {
            icon.setImageResource(R.drawable.rain);
        } else if (id == 1066 || id == 114 || id == 1210 || id == 1213 || id == 1216 || id == 1219 || id == 1222 || id == 1225) {
            icon.setImageResource(R.drawable.snow);
        } else if (id == 1030 || id == 1135 || id == 1147) {
            icon.setImageResource(R.drawable.fog);
        } else if (id == 1000) {
            icon.setImageResource(R.drawable.sunny);
        } else if (id == 1003 || id == 1006 || id == 1009) {
            icon.setImageResource(R.drawable.clouds);
        }
    }

    public void getLastLocation() {

        boolean hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        LocationListener gpsLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                locationByGPS = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        LocationListener networkLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationByNetwork = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (hasGPS) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, gpsLocationListener);
        }
        if (hasNetwork) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, networkLocationListener);
        }

        Location lastKnownLocationByGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastKnownLocationByGPS != null){
            locationByGPS = lastKnownLocationByGPS;
        }

        Location lastKnownLocationByNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(lastKnownLocationByNetwork != null){
            locationByNetwork = lastKnownLocationByNetwork;
        }
        if(locationByGPS!=null && locationByNetwork!=null){
            if(locationByGPS.getAccuracy()>locationByNetwork.getAccuracy()){
                currentLocation = locationByNetwork;
        }else{
                currentLocation = locationByGPS;
            }
            lat = currentLocation.getLatitude();
            lon = currentLocation.getLongitude();
    }
}
}
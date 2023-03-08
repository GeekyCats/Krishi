package com.example.krishi.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.krishi.R;

import java.util.HashMap;
import java.util.Map;


public class Crop extends Fragment {

    String temp,humidity,rainfall;

    EditText N,P,K,Ph;

    Button submit;

    TextView result;

    RequestQueue requestQueue;

    String api_url = "https://krishiml.azurewebsites.net//predict";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop, container, false);

        N = view.findViewById(R.id.nValue);
        P = view.findViewById(R.id.pValue);
        K = view.findViewById(R.id.kValue);
        Ph = view.findViewById(R.id.phValue);
        submit = view.findViewById(R.id.button);
        result = view.findViewById(R.id.crop);

        temp = Weather.getTemperatureData();
        humidity = Weather.getHumidityData();
        rainfall = Weather.getRainfallData();

        if(temp==null || humidity == null || rainfall == null){
            String s = "Open weather page";
            result.setText(s);
            Toast.makeText(requireContext(), "Pls open weather page", Toast.LENGTH_SHORT).show();
        }

        requestQueue = Volley.newRequestQueue(requireContext());


        submit.setOnClickListener(v -> {
            if(N.getText().toString().isEmpty() || P.getText().toString().isEmpty() ||
                    K.getText().toString().isEmpty() || Ph.getText().toString().isEmpty()){
                Toast.makeText(requireContext(), "Please enter all the values", Toast.LENGTH_SHORT).show();
            }
            else{
                predictCrop(temp,humidity,rainfall, N.getText().toString(),P.getText().toString(),
                        K.getText().toString(),Ph.getText().toString());
            }
        });
        return  view;
    }

    private void predictCrop(
            String temp, String humidity, String rainfall, String n, String p, String k, String ph
    ) {
        ProgressDialog pDialog = new ProgressDialog(requireContext());
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, api_url,
                response -> {
                    pDialog.dismiss();
                    result.setText(response.substring(8,response.length()-2));
                },
                error -> {
                    pDialog.dismiss();
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("N", n);
                params.put("P", p);
                params.put("K", k);
                params.put("temperature", temp);
                params.put("humidity", humidity);
                params.put("ph", ph);
                params.put("rainfall", rainfall);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
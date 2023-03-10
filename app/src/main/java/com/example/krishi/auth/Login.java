package com.example.krishi.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishi.R;
import com.example.krishi.data.responses.LoginResponse;
import com.example.krishi.retrofit.ApiClient;
import com.example.krishi.data.requests.LoginRequest;
import com.example.krishi.home.HomeActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class Login extends Fragment {
    NavController navController;
    ProgressBar progressBar;
    TextView email,password;
    Button button;

    SharedPreferences sharedPreferences;

    LoginResponse loginResponse;

    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String AUTH_TOKEN = "authToken";

    String token;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        ImageView image = view.findViewById(R.id.imageView);
        navController = Navigation.findNavController(requireActivity(), this.getId());
        image.setImageResource(R.drawable.logo_asset);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        button = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.progressBar);

        requireContext();
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(AUTH_TOKEN, "");

        if(!token.isEmpty()){
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                if(emailText.isEmpty() || passwordText.isEmpty()){
                    email.setError("Email is required");
                    password.setError("Password is required");
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(createLoginRequest());
                }
            }
        });
        TextView text = view.findViewById(R.id.signUp);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_login_to_signup);
            }
        });

        return  view;
    }
    public LoginRequest createLoginRequest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        return loginRequest;
    }

    public void loginUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getAPIService().login(loginRequest);
        loginResponseCall.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    loginResponse = response.body();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    assert loginResponse != null;
                    String temp = loginResponse.getAuthToken();
                    editor.putString(AUTH_TOKEN, temp);
                    editor.apply();

                    progressBar.setVisibility(View.GONE);

                    assert response.body() != null;
                    Toast.makeText(getContext(), "Login Successful !!", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(requireActivity(), HomeActivity.class);
                        startActivity(intent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Login Failed !!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Login Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
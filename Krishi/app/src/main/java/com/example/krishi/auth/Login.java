package com.example.krishi.auth;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.krishi.retrofit.ApiClient;
import com.example.krishi.data.LoginRequest;
import com.example.krishi.data.UserResponse;
import com.example.krishi.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Response;

public class Login extends Fragment {
    NavController navController;
    ProgressBar progressBar;
    TextView email,password;
    Button button;

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
        Call<UserResponse> loginResponseCall = ApiClient.getAPIService().login(loginRequest);
        loginResponseCall.enqueue(new retrofit2.Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Login Successful !!", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(requireActivity(), HomeActivity.class);
                        startActivity(intent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Login Failed !!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Login Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
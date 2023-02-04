package com.example.krishi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishi.auth.ApiClient;
import com.example.krishi.auth.SignUpResponse;
import com.example.krishi.auth.User;

import retrofit2.Call;


public class SignUp extends Fragment {

    NavController navController;
    EditText name,email,password;
    Button button;
    ProgressBar progressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        TextView text = view.findViewById(R.id.logIn);

        navController = Navigation.findNavController(requireActivity(), this.getId());

        name = view.findViewById(R.id.PersonName);
        email = view.findViewById(R.id.editTextTextPersonName2);
        password = view.findViewById(R.id.editTextTextPassword);
        button = view.findViewById(R.id.btn_signup);
        progressBar = view.findViewById(R.id.progressBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().isEmpty()){
                    name.setError("Name is required");
                    return;
                }
                else if(email.getText().toString().isEmpty()){
                    email.setError("Email is required");
                    return;
                }
                else if(password.getText().toString().isEmpty()){
                    password.setError("Password is required");
                    return;
                }
                else{
                progressBar.setVisibility(View.VISIBLE);
                saveUser(createUserRequest());
            }}
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signup_to_login);
            }
        });
        return view;
    }


    public User createUserRequest(){
        User user = new User();
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        return user;
    }
    public void saveUser(User user){
        Call<SignUpResponse> call = ApiClient.getAPIService().register(user);
        call.enqueue(new retrofit2.Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "User Registered Successfully !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Request Failed !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
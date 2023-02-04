package com.example.krishi;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class login extends Fragment {
    NavController navController;

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
        TextView text = view.findViewById(R.id.signUp);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_login_to_signup);
            }
        });
        return  view;
    }
}
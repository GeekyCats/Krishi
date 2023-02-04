package com.example.krishi.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishi.R;
import com.example.krishi.data.News;
import com.example.krishi.recyclerView.RecyclerAdapter;
import com.example.krishi.retrofit.ApiClient;
import com.example.krishi.retrofit.UserService;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class Home extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<News> newsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        newsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerAdapter = new RecyclerAdapter(getContext(), newsList);
        recyclerView.setAdapter(recyclerAdapter);
        UserService userService = ApiClient.getRetrofit().create(UserService.class);
        Call<List<News>> call = userService.getNews();


        call.enqueue(new retrofit2.Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, retrofit2.Response<List<News>> response) {
                if (response.isSuccessful()) {
                    newsList = response.body();
                    recyclerAdapter.setNewsList(newsList);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });

        return view;
    }
}
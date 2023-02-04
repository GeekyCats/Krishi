package com.example.krishi.recyclerView;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishi.data.News;
import com.example.krishi.R;

import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    Context context;
    List<News> newsList;

    public RecyclerAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news, parent, false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        String url = news.getUrlToImage();
        try{
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.urlToImage);}
        catch (Exception e){
            Log.d("Error", "onBindViewHolder: "+e);
        }

        holder.urlToImage.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            String website = news.getUrl();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            context.startActivity(browserIntent);
        }
        });
    }


    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        String url;
        ImageView urlToImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            urlToImage = itemView.findViewById(R.id.image);
        }
    }

}

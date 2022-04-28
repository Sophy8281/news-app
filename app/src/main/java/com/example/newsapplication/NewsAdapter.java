package com.example.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final ArrayList<Articles> articlesArrayList;
    private final Context context;

    // Constructor
    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    // Generated from implement methods of this class
    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new ViewHolder(view);
    }

    // Generated from implement methods of this class
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        // Instance of Articles class
        Articles articles = articlesArrayList.get(position);
        holder.titleTV.setText(articles.getTitle());
        holder.subTitleTV.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        // OnClickListener on the ViewHolder itemView
        holder.itemView.setOnClickListener(v -> {
            // New intent
            Intent intent = new Intent(context,NewsDetailActivity.class);
            // Passing data to NewsDetailActivity
            intent.putExtra("title",articles.getTitle());
            intent.putExtra("desc", articles.getDescription());
            intent.putExtra("image", articles.getUrlToImage());
            intent.putExtra("content",articles.getContent());
            intent.putExtra("url",articles.getUrl());
            context.startActivity(intent);
        });
    }

    // Return articlesArrayList size
    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Variable declaration
        private final TextView titleTV;
        private final TextView subTitleTV;
        private final ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Variable initialization
            titleTV = itemView.findViewById(R.id.idTVNewsHeading);
            subTitleTV = itemView.findViewById(R.id.idTVSubTitle);
            newsIV = itemView.findViewById(R.id.idIVNews);
        }
    }
}

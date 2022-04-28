package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface{

    //109358310bf14ce5b0ec3cf60aa41a61
    // Variable declaration
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModal> categoryModalArrayList;
    private NewsAdapter newsAdapter;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variable declaration and initialization
        RecyclerView newsRV = findViewById(R.id.idRVNews);
        RecyclerView categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);

        // Instances of ArrayList<Articles>, private ArrayList<CategoryModal>, NewsAdapter and CategoryAdapter
        articlesArrayList = new ArrayList<>();
        categoryModalArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(articlesArrayList,this);
        categoryAdapter = new CategoryAdapter(categoryModalArrayList,this,this::onCategoryClick);
        // Setting the layout manager of news RecyclerView to LinearLayoutManager
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        // Setting the adapter of news RecyclerView to newsAdapter
        newsRV.setAdapter(newsAdapter);
        // Setting the adapter of category RecyclerView to categoryAdapter
        categoryRV.setAdapter(categoryAdapter);

        //Get all categories
        getCategories();
        // Get all news
        getNews("All");
        newsAdapter.notifyDataSetChanged();
    }

    private void getCategories() {
        categoryModalArrayList.add(new CategoryModal("All","https://images.unsplash.com/photo-1643555604542-edc38fd85fd2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=352&q=80"));
        categoryModalArrayList.add(new CategoryModal("Technology","https://images.unsplash.com/photo-1621609764095-b32bbe35cf3a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80"));
        categoryModalArrayList.add(new CategoryModal("Science","https://images.unsplash.com/photo-1639813978991-909326392f31?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryModalArrayList.add(new CategoryModal("Sports","https://images.unsplash.com/photo-1647933656589-8f97c2d193b3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoryModalArrayList.add(new CategoryModal("General","https://images.unsplash.com/photo-1625409493189-ab2a2c432bcd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryModalArrayList.add(new CategoryModal("Business","https://images.unsplash.com/photo-1648070024543-371068b9b5e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryModalArrayList.add(new CategoryModal("Entertainment","https://images.unsplash.com/photo-1647743840162-507d66ac06ce?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80"));
        categoryModalArrayList.add(new CategoryModal("Health","https://images.unsplash.com/photo-1647201036996-e5f4ebb42212?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryAdapter.notifyDataSetChanged();
    }

    private void getNews(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=109358310bf14ce5b0ec3cf60aa41a61";
        String url = "https://newsapi.org/v2/top-headlines?country=us&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=109358310bf14ce5b0ec3cf60aa41a61";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;

        if (category.equals("All")) {
            call = retrofitAPI.getAllNews(url);
        } else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed to get News",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // onClickCategoryClick interface to load news by category
    @Override
    public void onCategoryClick(int position) {
        String category = categoryModalArrayList.get(position).getCategory();
        getNews(category);
    }
}
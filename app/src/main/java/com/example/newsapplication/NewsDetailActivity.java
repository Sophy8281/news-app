package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    // Variable declaration
    String title,desc,imageUrl,content,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // Get data passed from the previous activity
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        imageUrl = getIntent().getStringExtra("image");
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");

        //Variable declaration and initialization
        TextView titleTV = findViewById(R.id.idTVTitle);
        TextView subtitleTV = findViewById(R.id.idTVSubTitle);
        TextView contentTV = findViewById(R.id.idTVContent);
        ImageView newsIV = findViewById(R.id.idIVNews);
        Button readNewsBtn = findViewById(R.id.idBtnReadNews);

        // Setting text of the TextViews to the data passed from the previous activity
        titleTV.setText(title);
        subtitleTV.setText(desc);
        contentTV.setText(content);
        // Loading image of the news to the news ImageView
        Picasso.get().load(imageUrl).into(newsIV);

        // Setting OnClickListener on the readNewsBtn
        readNewsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }
}
package com.example.elitebook_anas.activitepartietrois;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class ArticleActivity extends AppCompatActivity {

    public static final String ARTICLE_TITLE = "title";
    public static final String ARTICLE_LINK = "link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_article);

        String title = getIntent().getStringExtra(ARTICLE_TITLE);
        String link = getIntent().getStringExtra(ARTICLE_LINK);

        setTitle(title);

        WebView webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl(link);

    }
}

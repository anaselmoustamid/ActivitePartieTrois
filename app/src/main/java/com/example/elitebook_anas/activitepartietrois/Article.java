package com.example.elitebook_anas.activitepartietrois;

import android.support.annotation.NonNull;

import java.util.Date;

public class Article implements Comparable<Article> {

    public final String title;
    public final String link;
    public final Date date;

    public Article(String title, String link, Date date) {
        this.title = title;
        this.link = link;
        this.date = date;
    }

    @Override
    public int compareTo(Article article) {
        return -(date.compareTo(article.date));
    }
}

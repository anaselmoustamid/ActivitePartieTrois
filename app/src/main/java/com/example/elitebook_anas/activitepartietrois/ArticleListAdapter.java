package com.example.elitebook_anas.activitepartietrois;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private final List<Article> _list = new ArrayList<>();

    @Override
    public int getItemCount() {
        return _list.size();
    }

    // This method must be called in UI thread !
    public void addArticles(List<Article> articles) {
        _list.addAll(articles);
        Collections.sort(_list);
        notifyDataSetChanged();
    }

    // This method must be called in UI thread !
    public void clear() {
        _list.clear();
        notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bind(_list.get(position));
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView _title;
        private Article _article;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            _title = ((TextView) itemView.findViewById(R.id.title));

            itemView.setOnClickListener(this);
        }

        public void bind(Article article) {
            _article = article;
            _title.setText(article.title);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ArticleActivity.class);
            intent.putExtra(ArticleActivity.ARTICLE_TITLE, _article.title);
            intent.putExtra(ArticleActivity.ARTICLE_LINK, _article.link);
            view.getContext().startActivity(intent);
        }
    }
}

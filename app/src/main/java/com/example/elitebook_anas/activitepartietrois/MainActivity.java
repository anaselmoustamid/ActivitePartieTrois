package com.example.elitebook_anas.activitepartietrois;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<DownloadTask> _tasks = new ArrayList<>();
    private ArticleListAdapter _adapter;

    private final List<String> _urls = Arrays.asList(
            "http://lesclesdedemain.lemonde.fr/screens/RSS/sw_getFeed.php?feedType=rss2&idTheme=88", // Culture
            "http://lesclesdedemain.lemonde.fr/screens/RSS/sw_getFeed.php?feedType=rss2&idTheme=90",  // Sciences
            "http://lesclesdedemain.lemonde.fr/screens/RSS/sw_getFeed.php?feedType=rss2&idTheme=92"  // Technologies
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listnews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _adapter = new ArticleListAdapter();
        recyclerView.setAdapter(_adapter);

        final ProgressBar progress = (ProgressBar) findViewById(R.id.progress);

        _adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (_adapter.getItemCount() > 0)
                    progress.setVisibility(View.GONE);
                else
                    progress.setVisibility(View.VISIBLE);
            }
        });

        _startDownloadTasks();
    }

    private void _startDownloadTasks() {
        for (DownloadTask task : _tasks)
            task.cancel(true);

        for (String url: _urls) {
            DownloadTask task = new DownloadTask(_adapter);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            _tasks.add(task);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refrech) {
            _adapter.clear();
            _startDownloadTasks();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (DownloadTask task : _tasks)
            task.cancel(true);
    }
}

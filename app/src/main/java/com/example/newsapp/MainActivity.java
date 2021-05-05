package com.example.newsapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<List<News>>, NewsAdapter.OnItemClickListener {

    private SwipeRefreshLayout swipe;
    private SwipeRefreshLayout swipe2;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<News> list = null;
    private TextView tvEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe2 = (SwipeRefreshLayout) findViewById(R.id.swiperefresh2);
        tvEmptyView = (TextView) findViewById(R.id.tv_emptyView);
        swipe.setOnRefreshListener(this);
        swipe2.setOnRefreshListener(this);

        if (haveNetworkConnection()){
            swipe2.setVisibility(View.GONE);
            swipe.setVisibility(View.VISIBLE);
        }else{
            swipe.setVisibility(View.GONE);
            swipe2.setVisibility(View.VISIBLE);
            tvEmptyView.setText(R.string.no_internet_connected);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsAdapter = new NewsAdapter(this);
        recyclerView.setAdapter(newsAdapter);

        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(0, null, this);
        if (haveNetworkConnection()){
            swipe2.setVisibility(View.GONE);
            swipe.setVisibility(View.VISIBLE);
        }else{
            swipe.setVisibility(View.GONE);
            swipe2.setVisibility(View.VISIBLE);
            tvEmptyView.setText(R.string.no_internet_connected);

        }
        swipe.setRefreshing(false);
        swipe2.setRefreshing(false);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        swipe.setRefreshing(false);
        if (data != null) {
            list = data;
            newsAdapter.addList(data);
            newsAdapter.notifyDataSetChanged();
        }else{
            swipe.setVisibility(View.GONE);
            swipe2.setVisibility(View.VISIBLE);
            tvEmptyView.setText(R.string.no_data_found);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {

    }

    @Override
    public void onItemClick(int position) {
        News news = list.get(position);
        String url = news.url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
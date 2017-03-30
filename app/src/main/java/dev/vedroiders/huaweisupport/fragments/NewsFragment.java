package dev.vedroiders.huaweisupport.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.vedroiders.huaweisupport.DataLoader;
import dev.vedroiders.huaweisupport.Main2Activity;
import dev.vedroiders.huaweisupport.NewsItem;
import dev.vedroiders.huaweisupport.NewsListView;

import java.util.ArrayList;

/**
 * Created by mike on 29.03.17.
 */

public class NewsFragment extends Fragment {

    NewsListView newsListView;
    Main2Activity main2Activity;


    public NewsFragment() {

    }

    @SuppressLint("ValidFragment")
    public NewsFragment(Main2Activity main2Activity) {
        this.main2Activity = main2Activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newsListView = new NewsListView(container.getContext());

        return newsListView;
    }

    @Override
    public void onStart() {
        loadNews();
        super.onStart();
    }

    void loadNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                final ArrayList<NewsItem> newsList = DataLoader
                        .loadNews();

                main2Activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsListView.setNews(newsList);
                    }
                });

            }
        }).start();
    }

}

package dev.vedroiders.huaweisupport;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mike on 25.03.17.
 */
public class NewsListView extends ScrollView {

    ArrayList<View> unSizedViews = new ArrayList<>();
    private LinearLayout attachedLayout;
    private String TAG = "ItemsContainer";
    private NewsViewer newsViewer;

    public NewsListView(Context context) {
        this(context, null);
    }

    public NewsListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NewsListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        attachedLayout = new LinearLayout(getContext());
        attachedLayout.setOrientation(LinearLayout.VERTICAL);
        attachedLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(attachedLayout);
        newsViewer = new NewsViewer(getContext());
    }

    public void setNews(ArrayList<NewsItem> items) {
        int i = 0;
        for (NewsItem item : items) {
            attachedLayout.addView(getView(item));
            Log.d(TAG, "setNews: " + i++);
        }
    }


    private LinearLayout getView(final NewsItem newsItem) {


        LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.news_item,
                        null);

        ((TextView) layout.findViewById(R.id.title))
                .setText((newsItem.title));
        ((TextView) layout.findViewById(R.id.description))
                .setText(Html.fromHtml(newsItem.description));

        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setView(newsViewer.load(newsItem.url))
                        .create()
                        .show();
            }
        });


//        unSizedViews.add(layout);
//        sizePostCorrection(unSizedViews.size() - 1);

        return layout;
    }


    class NewsViewer extends WebView {

        public NewsViewer(Context context) {
            super(context);
            getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        private void unbindFromParent() {
            if (getParent() != null) {
                ((ViewGroup) getParent()).removeView(this);
            }
        }

        public NewsViewer load(String url) {
            loadUrl("about:blank");
            unbindFromParent();
            loadUrl(url);
            return this;
        }
    }


}

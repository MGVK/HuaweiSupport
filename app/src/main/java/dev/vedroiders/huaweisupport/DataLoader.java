package dev.vedroiders.huaweisupport;


import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mike on 25.03.17.
 */
public class DataLoader {


    private static String TAG = "DataLoader";

    public static ArrayList<NewsItem> loadNews() {

        ArrayList<NewsItem> news = new ArrayList<>();

        try {
//            String rss = getResponse("http://www.huawei.com/ru/rss-feeds/huawei-updates/rss","");

            HttpURLConnection connection = (HttpURLConnection)
                    new URL("http://www.huawei.com/ru/rss-feeds/huawei-updates/rss")
                            .openConnection();
//            connection.setDoOutput(true);
            connection.setRequestMethod("GET");

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();

            parser.setInput(new InputStreamReader(connection.getInputStream()));

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {

                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("item")) {

//                    Log.d(TAG, "loadNews: new item ("+i+")");

                    NewsItem newsItem = new NewsItem();

                    parser.next(); // skip empty text
                    parser.next(); // <title>
                    parser.next(); // text
                    newsItem.title = parser.getText();
//                    Log.d(TAG, "loadNews: title:"+newsItem.title);
                    parser.next(); // </title>
                    parser.next(); // skip empty text
                    parser.next(); // <pubDate>
                    parser.next(); // text
                    newsItem.date = parser.getText();
//                    Log.d(TAG, "loadNews: date:"+newsItem.date);
                    parser.next(); // </pubDate>
                    parser.next(); // skip empty text
                    parser.next(); // <link>
                    parser.next(); // text
                    newsItem.url = parser.getText();
//                    Log.d(TAG, "loadNews: url:"+newsItem.url);
                    parser.next(); // </link>
                    parser.next(); // skip empty text
                    parser.next(); // <description>
                    parser.next(); // text
                    newsItem.description = parser.getText();
//                    Log.d(TAG, "loadNews: description:"+newsItem.description);
                    news.add(newsItem);

//                    Log.d(TAG, "loadNews: finish item ("+(i++)+")");
                }
                parser.next();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    void dataTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (NewsItem newsItem : DataLoader.loadNews()) {
                    Log.d(TAG, newsItem.toString());
                }
//                DataLoader.test();
            }
        }).start();
    }

}

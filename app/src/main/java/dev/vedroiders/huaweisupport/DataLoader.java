package dev.vedroiders.huaweisupport;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import dev.vedroiders.huaweisupport.kupihleba.Client;
import dev.vedroiders.huaweisupport.kupihleba.Interaction;

/**
 * Created by mike on 25.03.17.
 */
class DataLoader {


    static String FILE_PATH;
    private static String TAG = "DataLoader";
    private static String FILE_NAME = "consumer";
    private static HashMap<String, Typeface> typefaces = new HashMap<>();


    static ArrayList<NewsItem> loadNews() {

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

    static void removeProfile() {
        File f = new File(FILE_PATH + FILE_NAME);
        if (f.exists()) {
            f.delete();
        }
    }

    static void dataTest() {
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

    static Typeface getTypeface(Context context, String name) {
        Typeface typeface = typefaces.get(name);

        if (typeface == null) {
            typefaces.put(name,
                    typeface = (Typeface.createFromAsset(context.getAssets(), name + ""
                            + ".ttf"))
            );
        }

        return typeface;
    }


    static Consumer loadProfile() {

        Consumer consumer = null;

        try {

            FileInputStream fis = new FileInputStream(FILE_PATH + FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            consumer = (Consumer) ois.readObject();
            Log.d("Consumer", "Loaded " + consumer.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return consumer;
    }


    /**
     * попытка залогиниться
     *
     * @param login
     * @param pass
     * @return профиль пользователя, если успех, иначе - null,
     */
    static void tryLogin(String login, String pass) {
        Interaction interaction = new Interaction();
        interaction.type = Interaction.Type.LOGIN;
        interaction.login = login;
        interaction.password = pass;
        new Client() {
            @Override
            public void gotResponse(boolean isOk, Interaction response) {
                // YOUR CODE HERE!!!!!!!
            }
        }.sendAsync(interaction);
    }

    static void saveProfile(Consumer consumer) {
        try {
            File f = new File(FILE_PATH + FILE_NAME);
            if (!f.getParentFile().exists()
                    && f.getParentFile().mkdirs()) {
                f.createNewFile();
            }
            else if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(FILE_PATH + FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(consumer);
            oos.close();
            fos.flush();
            fos.close();
            Log.d("Consumer", "Saved " + consumer.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * Проверка существования акка на серваке
     *
     * @return
     */
    static void checkMail(String login) {

        Interaction interaction = new Interaction();
        interaction.type = Interaction.Type.DATA;
        new Client() {
            @Override
            public void gotResponse(boolean hasLogin, Interaction response) {
                // YOUR CODE HERE!!!!!!!
            }
        }.sendAsync(interaction);

    }

    /**
     * запрос на регистрацию
     * @param consumer профиль для регистрации
     * @return true, если успешно, false, нет нет
     */

    public static boolean registerProfile(Consumer consumer) {
        Interaction interaction = new Interaction();
        interaction.type = Interaction.Type.REGISTER;
        interaction.login = consumer.getEmail();
        interaction.password = consumer.getPass();
        interaction.email = consumer.getEmail();
        interaction.model = consumer.getModel();
        interaction.number = consumer.getPhone();

        new Client() {
            @Override
            public void gotResponse(boolean isOk, Interaction response) {
                // YOUR CODE HERE!!!!!!!
            }
        }.sendAsync(interaction);

        return true;
    }
}

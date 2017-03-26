package dev.vedroiders.huaweisupport;

/**
 * Created by mike on 25.03.17.
 */
public class NewsItem {

    public String title = "";
    public String url = "";
    public String date = "";
    public String description = "";

    @Override
    public String toString() {

        return String.format("[%s of %s | %s]: %s ", title, date, url, description);

    }
}

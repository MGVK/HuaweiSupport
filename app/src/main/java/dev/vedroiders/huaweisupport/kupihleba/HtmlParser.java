package dev.vedroiders.huaweisupport.kupihleba;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * @author kupihleba
 *         Class for getting links to documentation
 */
public class HtmlParser {

    final URL url = new URL("http://consumer.huawei.com/ru/support/manuals/index.htm");
    final URL res = new URL("http://consumer.huawei.com/support/services/service/isearch/open/list");
    final URL service = new URL("http://consumer.huawei.com/support/services/service/file/list");
    final URL test = new URL("http://app.huawei.com/hwa-c/open/dc?top=istop&action=click&idsite=consumer-ru&hier=c%3A%7Bsupport%2Fmanuals%7Dg%3A%7Bru%2Fpageview%7D&r=402556&t=1490697573024&tz=3&dt=%E3%80%90%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D0%B8%D0%B8%20Huawei%E3%80%91%20-%20%D0%9F%D0%BE%D0%B4%D0%B4%D0%B5%D1%80%D0%B6%D0%BA%D0%B0%20Huawei%20-%20%D0%9E%D1%84%D0%B8%D1%86%D0%B8%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9%20%D1%81%D0%B0%D0%B9%D1%82%20Huawei&url=http%3A%2F%2Fconsumer.huawei.com%2Fru%2Fsupport%2Fmanuals%2Findex.htm&blp=0&_idenc=1&_id=bbcd000eb322d367&_sid=47dfd55123299a7f11a03335a68b02e24188d49d&_pid=f6767ffadec0455ad3b35aec4394de33daff6a99&_idts=1490625846&_idvc=2&_idn=0&_refts=1490696644&_viewts=1490640754&_ref=http%3A%2F%2Fvk.com%2Faway.php&offset=929201&data=%7B%22con_key3%22%3A%22manuals%22%2C%22con_key4%22%3A%22123%22%2C%22con_key5%22%3A%22supisearch%22%7D&cookie=1&res=1920x1080");
    private Pattern manualsPattern  = Pattern.compile("");

    public HtmlParser() throws MalformedURLException {
    }

    public String requestLink(String query) {
        try {
            execute(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String[] execute(String query) throws IOException {

        Log.d("kupihleba", "execute!");
        HttpURLConnection connection = (HttpURLConnection) res.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("jsonp", "jQuery171006250950902245012_1490700312508");
        connection.addRequestProperty("siteCode", "ru");
        connection.addRequestProperty("APP_NUM", "A40426686");
        connection.addRequestProperty("tagMatch", "same");
        connection.addRequestProperty("mcCurPage", "1");
        connection.addRequestProperty("pageSize", "5");
        connection.addRequestProperty("isTop", "1");
        connection.addRequestProperty("ssUserText", "p6");
        connection.addRequestProperty("_", "1490700314859");


        //connection.addRequestProperty("", "{\"con_key3\":\"manuals\",\"con_key4\":\"" + query + "\",\"con_key5\":\"supisearch\"}");

       //connection.setRequestProperty("Accept-Charset", "UTF-8");
//http://app.huawei.com/hwa-c/open/dc?top=istop&action=click&idsite=consumer-ru&hier=c%3A%7Bsupport%2Fmanuals%7Dg%3A%7Bru%2Fpageview%7D&r=402556&t=1490697573024&tz=3&dt=%E3%80%90%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D0%B8%D0%B8%20Huawei%E3%80%91%20-%20%D0%9F%D0%BE%D0%B4%D0%B4%D0%B5%D1%80%D0%B6%D0%BA%D0%B0%20Huawei%20-%20%D0%9E%D1%84%D0%B8%D1%86%D0%B8%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9%20%D1%81%D0%B0%D0%B9%D1%82%20Huawei&url=http%3A%2F%2Fconsumer.huawei.com%2Fru%2Fsupport%2Fmanuals%2Findex.htm&blp=0&_idenc=1&_id=bbcd000eb322d367&_sid=47dfd55123299a7f11a03335a68b02e24188d49d&_pid=f6767ffadec0455ad3b35aec4394de33daff6a99&_idts=1490625846&_idvc=2&_idn=0&_refts=1490696644&_viewts=1490640754&_ref=http%3A%2F%2Fvk.com%2Faway.php&offset=929201&data=%7B%22con_key3%22%3A%22manuals%22%2C%22con_key4%22%3A%22123%22%2C%22con_key5%22%3A%22supisearch%22%7D&cookie=1&res=1920x1080
        try {

            StringBuilder sb = new StringBuilder();
            byte [] data = new byte[1024];
            InputStream response = new BufferedInputStream(connection.getInputStream());
            while (response.read(data) != -1)
            {
                sb.append(new String(data));
            }
            Log.d("kupihleba", sb.toString());

        } finally {
            connection.disconnect();
        }
        return null;
    }

}

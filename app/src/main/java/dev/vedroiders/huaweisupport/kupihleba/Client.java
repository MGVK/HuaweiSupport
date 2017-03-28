package dev.vedroiders.huaweisupport.kupihleba;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author kupihleba
 *         CLient service class
 */
@TargetApi(3)
public class Client {
    private final int port = 54382;
    private final String host = "35.162.158.3";
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public void sendAsync(final Interaction data)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    socket = new Socket(InetAddress.getByName(host), port);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(prettify(data));
                    Interaction respond = prettify(in.readUTF());
                    Log.d("kupihleba", respond.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
    }.execute();
    }

    private String prettify (Interaction data)
    {
       return Base64.encodeToString(new Gson().toJson(data).getBytes(), Base64.DEFAULT);
    }
    private Interaction prettify (String data)
    {
        return new Gson().fromJson(data, Interaction.class);
    }

}

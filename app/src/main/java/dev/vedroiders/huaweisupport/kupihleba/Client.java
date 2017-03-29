package dev.vedroiders.huaweisupport.kupihleba;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

@TargetApi(24)
interface Queries {
    void gotResponse(boolean isOk, Interaction responseQuery);
}

/**
 * @author kupihleba
 *         CLient service class
 */
@TargetApi(3)
public abstract class Client implements Queries {
    private final int port = 54382;
    private final String host = "35.162.158.3";
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private void assume(Interaction response) {
        gotResponse(response.type != Interaction.Type.NONE, response);
    }

    public void sendAsync(final Interaction data)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Parser parser = new Parser();
                    socket = new Socket(InetAddress.getByName(host), port);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    Log.d("kupihleba", ">> sending query");

                    out.writeUTF(parser.outputParse(data));
                    Log.d("kupihleba", ">> query sent!");

                    Interaction respond = parser.inputParse(in.readUTF());
                    Log.d("kupihleba", respond.toString());
                    Log.d("kupihleba", ">> Perfect!");
                    assume(respond);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
    }.execute();
    }


}

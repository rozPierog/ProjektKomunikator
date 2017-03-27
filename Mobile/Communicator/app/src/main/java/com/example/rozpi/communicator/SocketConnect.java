package com.example.rozpi.communicator;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by rozpi on 27.03.2017.
 */

public class SocketConnect extends AsyncTask<Void, Void, Void> {

    private String serverIP = "10.0.2.2";
    private Integer serverPort = 3000;
    Socket client;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            client = new Socket(InetAddress.getByName(serverIP),serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SocketHandler.setSocket(client);
        return null;
    }
}

package com.example.rozpi.communicator;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by rozpi on 27.03.2017.
 */

class SocketConnect extends AsyncTask<Void, Void, Void> {

    private String serverIP = "52.57.45.43";
    private Integer serverPort = 6666;
    private Socket client;

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

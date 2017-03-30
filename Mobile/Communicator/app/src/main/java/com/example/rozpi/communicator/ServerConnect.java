package com.example.rozpi.communicator;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Marcin Omelan on 26.03.2017.
 */

class ServerConnect extends AsyncTask<String, Void, Void> {


    private Socket client;
    private String toServer;
    private Boolean message;

     ServerConnect(Boolean message, String toServer, Socket client) {
         this.message = message;
         this.toServer = toServer;
         this.client = client;
    }


    @Override
    protected Void doInBackground(String... params) {


        try {
            //client = new Socket(InetAddress.getByName(serverIP),serverPort);
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(client.getOutputStream())),
                    true);
            if(message)
                out.println(SocketHandler.getNick() +" "+ toServer);
            else
                out.println("LOGIN "+ toServer);
            Log.e("SOCKET", "Success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

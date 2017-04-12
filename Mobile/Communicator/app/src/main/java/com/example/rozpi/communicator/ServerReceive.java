package com.example.rozpi.communicator;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Marcin Omelan on 27.03.2017.
 * Receiving message from echos from server
 */

public class ServerReceive extends IntentService {

    public static final String BROADCAST_ACTION = "com.example.rozpie.serverrevice.displayevent";
    private final Handler handler = new Handler();
    private Intent intent;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServerReceive(String name) {
        super(name);
    }

    public ServerReceive() {
        super("ServerRecive");
    }




    private final Runnable sendUpdatesToUI = new Runnable() {
        @Override
        public void run() {
            checkForMessage();
        }
    };


    private void checkForMessage() {
        Socket socket = SocketHandler.getSocket();
        BufferedReader networkIn;
        try {
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(socket.isConnected()) {
                String message = networkIn.readLine();
                Log.i("Echo", "Connected");
                Log.i("Echo read", message);
                if (message != null){
                    intent.putExtra("message", message);
                    sendBroadcast(intent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        handler.removeCallbacks(sendUpdatesToUI);
        this.intent = new Intent(BROADCAST_ACTION);
        checkForMessage();
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }



}

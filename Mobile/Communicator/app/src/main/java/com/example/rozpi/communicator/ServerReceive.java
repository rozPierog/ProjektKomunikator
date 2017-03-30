package com.example.rozpi.communicator;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by rozpi on 27.03.2017.
 */

public class ServerReceive extends IntentService {

    private static final String TAG ="ServerRecive";
    public static final String BROADCAST_ACTION = "com.example.rozpie.serverrevice.displayevent";
    Socket socket;
    private final Handler handler = new Handler();
    String message;
    Intent intent;

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




    private Runnable sendUpdatesToUI = new Runnable() {
        @Override
        public void run() {
            checkForMessage();
        }
    };


    private void checkForMessage() {
        socket = SocketHandler.getSocket();
        PrintWriter out;
        BufferedReader networkIn;
        try {
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                message = networkIn.readLine();
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

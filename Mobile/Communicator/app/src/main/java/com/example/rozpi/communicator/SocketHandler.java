package com.example.rozpi.communicator;

import java.net.Socket;

/**
 * Created by rozpi on 27.03.2017.
 */

public class SocketHandler {
    private static String serverIP = "10.0.2.2";
    private static Integer serverPort = 3000;
    private static Socket socket;


    public static synchronized Socket getSocket(){
        return SocketHandler.socket;
    }

    public static synchronized void setSocket(Socket socket){
        SocketHandler.socket = socket;
    }
}
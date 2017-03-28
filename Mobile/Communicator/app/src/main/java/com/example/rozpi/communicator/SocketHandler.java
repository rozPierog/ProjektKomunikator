package com.example.rozpi.communicator;

import java.net.Socket;

/**
 * Created by Marcin Omelan on 27.03.2017.
 */

class SocketHandler {
    private static String nick = "10.0.2.2";
    private static Socket socket;

    static String getNick() {
        return nick;
    }

    static void setNick(String nick) {
        SocketHandler.nick = nick;
    }

    static synchronized Socket getSocket(){
        return SocketHandler.socket;
    }

    static synchronized void setSocket(Socket socket){
        SocketHandler.socket = socket;
    }
}
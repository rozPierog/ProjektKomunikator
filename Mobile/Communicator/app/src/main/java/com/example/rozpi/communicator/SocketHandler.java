package com.example.rozpi.communicator;

import java.net.Socket;

/**
 * Created by Marcin Omelan on 27.03.2017.
 * Class that allows me to get socket and user nick in any place in the app.
 */

class SocketHandler {
    private static String nick;
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
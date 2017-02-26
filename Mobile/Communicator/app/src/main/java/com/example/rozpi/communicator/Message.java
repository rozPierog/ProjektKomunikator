package com.example.rozpi.communicator;



public class Message {

    String sender;
    String message;
    String timestamp;


    public Message(String sender, String message, String timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
}

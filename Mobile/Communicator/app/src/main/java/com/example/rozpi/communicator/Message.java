package com.example.rozpi.communicator;



class Message {

    String sender = "No Nick";
    String message = "No Message";
    String timestamp = "No Time";


    String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    Message(String sender, String message, String timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
}

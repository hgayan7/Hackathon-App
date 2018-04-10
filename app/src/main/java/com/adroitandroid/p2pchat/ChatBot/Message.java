package com.adroitandroid.p2pchat.ChatBot;

public class Message {

    final static int BOT_MSG = 1;
    final static int USER_MSG = 0;
    String msg;

    public Message(String msg, int owner) {
        this.msg = msg;
        this.owner = owner;
    }

    int owner;

}

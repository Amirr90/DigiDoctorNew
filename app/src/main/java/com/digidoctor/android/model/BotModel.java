package com.digidoctor.android.model;

public class BotModel {
    String msg;
    String type;
    long timestamp;

    public BotModel() {
    }

    public BotModel(String msg, String type, long timestamp) {
        this.msg = msg;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }
}

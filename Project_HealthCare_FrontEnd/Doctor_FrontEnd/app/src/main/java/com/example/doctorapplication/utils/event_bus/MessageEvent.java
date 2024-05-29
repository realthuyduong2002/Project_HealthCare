package com.example.doctorapplication.utils.event_bus;

public class MessageEvent {
    public static final String UPDATE_DOCTOR_INFO = "UPDATE_DOCTOR_INFO";
    private String msg;

    public MessageEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
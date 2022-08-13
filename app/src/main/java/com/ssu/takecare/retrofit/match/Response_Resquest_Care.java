package com.ssu.takecare.retrofit.match;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_Resquest_Care {
    @SerializedName("data")
    private int data;

    @SerializedName("message")
    public String message;
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.ssu.takecare.Retrofit.Match;

import com.google.gson.annotations.SerializedName;

public class ResponseGetUser {

    @SerializedName("data")
    private DataResponseGetUser data;

    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }
    public DataResponseGetUser getData() {
        return data;
    }

    public void setData(DataResponseGetUser data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
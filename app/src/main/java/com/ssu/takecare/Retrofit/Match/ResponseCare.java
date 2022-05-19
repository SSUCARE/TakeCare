package com.ssu.takecare.Retrofit.Match;

import com.google.gson.annotations.SerializedName;

public class ResponseCare {
    @SerializedName("data")
    private DataResponseCare data;

    @SerializedName("message")
    public String message;

    public void setData(DataResponseCare data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataResponseCare getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
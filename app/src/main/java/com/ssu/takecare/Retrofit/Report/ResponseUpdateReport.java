package com.ssu.takecare.Retrofit.Report;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateReport {
    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

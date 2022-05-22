package com.ssu.takecare.Retrofit.GetReport;

import com.google.gson.annotations.SerializedName;

public class ResponseGetReport {
    @SerializedName("data")
    public DataGetReport data;

    @SerializedName("message")
    public String message;

    public void setData(DataGetReport data) { this.data = data; }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataGetReport getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}

package com.ssu.takecare.retrofit.report;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetReport {
    @SerializedName("data")
    public List<DataGetReport> data;

    @SerializedName("message")
    public String message;

    public void setData(List<DataGetReport> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataGetReport> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}

package com.ssu.takecare.retrofit.match;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseCare {
    @SerializedName("data")
    private List<DataResponseCare> data;

    @SerializedName("message")
    public String message;

    public void setData(List<DataResponseCare> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataResponseCare> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
package com.ssu.takecare.retrofit.report;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class ResponseReport {
    @SerializedName("data")
    public DataReport data;

    @SerializedName("message")
    public String message;

    public void setData(DataReport data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataReport getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return message + ", reportId : " + getData().getReportId();
    }
}

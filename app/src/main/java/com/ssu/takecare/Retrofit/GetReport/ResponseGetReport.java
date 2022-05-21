package com.ssu.takecare.Retrofit.GetReport;

import com.google.gson.annotations.SerializedName;

public class ResponseGetReport {
    @SerializedName("data")
    public DataGetReport dataGetReport;

    @SerializedName("message")
    public String message;

    public void setDataGetReport(DataGetReport dataGetReport) {
        this.dataGetReport = dataGetReport;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataGetReport getDataGetReport() {
        return dataGetReport;
    }

    public String getMessage() {
        return message;
    }
}

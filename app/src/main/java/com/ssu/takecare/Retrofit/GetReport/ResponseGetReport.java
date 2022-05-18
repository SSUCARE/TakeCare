package com.ssu.takecare.Retrofit.GetReport;

import com.google.gson.annotations.SerializedName;

public class ResponseGetReport {
    @SerializedName("data")
    public DataGetReport datagetreprot;

    public DataGetReport getDatagetreprot() {
        return datagetreprot;
    }

    public void setDatagetreprot(DataGetReport datagetreprot) {
        this.datagetreprot = datagetreprot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    public String message;
}

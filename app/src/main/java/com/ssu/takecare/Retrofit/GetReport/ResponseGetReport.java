package com.ssu.takecare.Retrofit.GetReport;

import com.google.gson.annotations.SerializedName;

public class ResponseGetReport {
    @SerializedName("data")
    public DataGetReport datagetreprot;

    @SerializedName("message")
    public String message;

    public void setDatagetreprot(DataGetReport datagetreprot) {
        this.datagetreprot = datagetreprot;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataGetReport getDatagetreprot() {
        return datagetreprot;
    }

    public String getMessage() {
        return message;
    }
}

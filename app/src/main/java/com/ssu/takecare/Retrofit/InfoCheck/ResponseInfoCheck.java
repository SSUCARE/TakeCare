package com.ssu.takecare.Retrofit.InfoCheck;

import com.google.gson.annotations.SerializedName;

public class ResponseInfoCheck {
    @SerializedName("data")
    public DataInfoCheck dataInfoCheck;

    @SerializedName("message")
    public String message;

    public DataInfoCheck getDataInfocheck() {
        return dataInfoCheck;
    }

    public void setDataInfoCheck(DataInfoCheck dataInfoCheck) { this.dataInfoCheck = dataInfoCheck; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}

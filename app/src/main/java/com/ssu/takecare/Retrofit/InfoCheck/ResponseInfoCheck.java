package com.ssu.takecare.Retrofit.InfoCheck;

import com.google.gson.annotations.SerializedName;

public class ResponseInfoCheck {
    @SerializedName("data")
    public DataInfoCheck datainfocheck;
    @SerializedName("message")
    public String message;

    public DataInfoCheck getDatainfocheck() {
        return datainfocheck;
    }

    public void setDatainfocheck(DataInfoCheck datainfocheck) {
        this.datainfocheck = datainfocheck;
    }

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

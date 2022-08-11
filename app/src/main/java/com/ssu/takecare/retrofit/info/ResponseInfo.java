package com.ssu.takecare.retrofit.info;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class ResponseInfo {
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public String data;

    @NonNull
    @Override
    public String toString() {
        return message + " data : " + data;
    }
}

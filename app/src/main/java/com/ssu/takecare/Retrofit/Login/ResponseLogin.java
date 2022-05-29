package com.ssu.takecare.Retrofit.Login;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public DataLogin data;

    public String data_accessToken;

    @NonNull
    @Override
    public String toString() {
        data_accessToken = data.toString();

        return message;
    }
}

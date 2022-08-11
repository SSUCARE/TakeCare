package com.ssu.takecare.retrofit.login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public DataLogin data;

    public String data_accessToken;

    public String getMessage() {
        data_accessToken = data.getAccessToken();
        return message;
    }
}

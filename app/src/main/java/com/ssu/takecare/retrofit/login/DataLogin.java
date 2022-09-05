package com.ssu.takecare.retrofit.login;

import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("accessToken")
    public String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}

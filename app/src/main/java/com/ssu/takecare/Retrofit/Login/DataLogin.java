package com.ssu.takecare.Retrofit.Login;

import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("accessToken")
    public String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}

package com.ssu.takecare.Retrofit.Login;

import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("email")
    public String email;

    @SerializedName("accessToken")
    public String accessToken;

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

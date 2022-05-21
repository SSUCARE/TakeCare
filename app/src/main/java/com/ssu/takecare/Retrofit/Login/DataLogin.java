package com.ssu.takecare.Retrofit.Login;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("accessToken")
    public String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @NonNull
    @Override
    public String toString() {
        return accessToken;
    }
}

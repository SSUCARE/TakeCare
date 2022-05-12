package com.ssu.takecare.Retrofit.Login;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("accessToken")
    public String accessToken;

    @NonNull
    @Override
    public String toString() {
        return accessToken;
    }
}

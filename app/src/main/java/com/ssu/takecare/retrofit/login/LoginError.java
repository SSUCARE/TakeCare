package com.ssu.takecare.retrofit.login;

import com.google.gson.annotations.SerializedName;

public class LoginError {
    @SerializedName("errorMessage")
    public String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
}

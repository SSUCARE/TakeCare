package com.ssu.takecare.retrofit.signup;

import com.google.gson.annotations.SerializedName;

public class SignupError {
    @SerializedName("errorMessage")
    public String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
}

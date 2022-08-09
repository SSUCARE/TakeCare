package com.ssu.takecare.Retrofit.Login;

import com.google.gson.annotations.SerializedName;
import com.ssu.takecare.Retrofit.Signup.DataSignup;

public class ResponseLogin {
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public DataLogin data;

    @SerializedName("errorMessage")
    public String errorMessage;

    @SerializedName("errors")
    public DataSignup errors;

    public String error_email;
    public String data_accessToken;

    public String getMessage() {
        data_accessToken = data.getAccessToken();
        return message;
    }

    public String getErrorMessage() {
        error_email = errors.getEmail();
        return errorMessage;
    }
}

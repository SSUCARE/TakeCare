package com.ssu.takecare.Retrofit.Signup;

import com.google.gson.annotations.SerializedName;

public class ResponseSignup {
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public DataSignup data;

    @SerializedName("errorMessage")
    public String errorMessage;

    @SerializedName("errors")
    public DataSignup errors;

    public String data_email;
    public String error_email;
    public String error_password;

    public String getMessage() {
        data_email = data.getEmail();

        return message;
    }

    public String getErrorMessage() {
        error_email = errors.getEmail();
        error_password = errors.getPassword();

        return errorMessage;
    }
}

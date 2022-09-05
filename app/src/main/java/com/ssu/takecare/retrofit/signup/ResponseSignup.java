package com.ssu.takecare.retrofit.signup;

import com.google.gson.annotations.SerializedName;

public class ResponseSignup {
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public DataSignup data;

    public String data_email;

    public String getMessage() {
        data_email = data.getEmail();
        return message;
    }
}

package com.ssu.takecare.retrofit.password;

import com.google.gson.annotations.SerializedName;

public class ResponseFindPassword {
    @SerializedName("message")
    public String message;

    @SerializedName("errorMessage")
    public String errorMessage;

    public String getMessage() {
        return message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

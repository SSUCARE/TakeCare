package com.ssu.takecare.Retrofit.Signup;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class ResponseSignup {
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public DataSignup data;

    public String data_email;

    @NonNull
    @Override
    public String toString() {
        data_email = data.toString();

        return message;
    }
}

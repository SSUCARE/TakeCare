package com.ssu.takecare.retrofit.signup;

import com.google.gson.annotations.SerializedName;

public class DataSignup {
    @SerializedName("email")
    public String email;

    public String getEmail() {
        return email;
    }
}
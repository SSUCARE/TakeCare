package com.ssu.takecare.Retrofit.Signup;

import com.google.gson.annotations.SerializedName;

public class DataSignup {
    @SerializedName("email")
    public String email;

    public String getEmail() {
        return email;
    }
}
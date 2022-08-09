package com.ssu.takecare.Retrofit.Signup;

import com.google.gson.annotations.SerializedName;

public class DataSignup {
    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}